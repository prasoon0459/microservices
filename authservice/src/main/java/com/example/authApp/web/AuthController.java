package com.example.authApp.web;

import static org.springframework.http.ResponseEntity.ok;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.authApp.domain.User;
import com.example.authApp.repo.UserRepository;
import com.example.authApp.security.JwtProperties;
import com.example.authApp.security.JwtTokenProvider;
	

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    JwtProperties jwtProperties;
    
    @Autowired
    UserRepository usersRepo;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @SuppressWarnings("rawtypes")
    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody AuthRequest data) {

        try {
            String username = data.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            User user=usersRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"));
            
            String token,refreshToken;
            if(user.isValid()){
            	token = jwtTokenProvider.createToken(user);
                refreshToken= jwtTokenProvider.createRefreshToken(user);
            }
            else {
				throw new BadCredentialsException("Account blocked by admin");
			}
            HttpHeaders headers=new HttpHeaders();
            
            Calendar ca=Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            Calendar cr=Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            ca.add(Calendar.SECOND, (int)(jwtProperties.getValidityInMs()/1000));
            cr.add(Calendar.SECOND, (int)(jwtProperties.getRefreshValidityInMs()/1000));
            
            Date accessExp=ca.getTime();
            Date refreshExp=cr.getTime();
            
            SimpleDateFormat df=new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
            
            
            headers.add(HttpHeaders.SET_COOKIE, "access_token="+token+"; Path=/; Expires="+df.format(accessExp)+" GMT; HttpOnly");
            headers.add(HttpHeaders.SET_COOKIE, "refresh_token="+refreshToken+"; Path=/auth/refresh; Expires="+df.format(refreshExp)+" GMT; HttpOnly");
            Map<Object, Object> model = new HashMap<>();
            model.put("timestamp",new Date());
            model.put("status",200);
            model.put("name", user.getName());
            model.put("username",user.getUsername());
            model.put("phone", user.getPhone());
            model.put("roles", user.getRoles());
            model.put("path", "auth/signin");
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(model);
            
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
    
    @SuppressWarnings("rawtypes")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest data) {
            String username = data.getUsername();
            String password = data.getPassword();
            String name=data.getName();
            long phone=data.getPhone();

            Map<Object, Object> model = new HashMap<>();
            if(usersRepo.findByUsername(username).isPresent()) {
            	model.put("timestamp",new Date());
                model.put("status",409);
                model.put("error", "Conflict");
                model.put("message", "Username already registered!");
                model.put("path","auth/register");
                return ok(model);
            }
            usersRepo.save(new User(username,passwordEncoder.encode(password),name, phone,Arrays.asList( "ROLE_USER")));
            
            model.put("timestamp",new Date());
            model.put("status",201);
            model.put("error", false);
            model.put("message", "Successfully Registerd!!");
            model.put("path","auth/register");
            return ok(model);
    }
    
    @SuppressWarnings("rawtypes")
    @PostMapping("/refresh")
    public ResponseEntity refresh(HttpServletRequest response) {
		Map<Object, Object> model = new HashMap<>();
		String newAccessToken;
		String refresh_token=response.getCookies()[0].getValue();

		Calendar ca=Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		//System.out.println("GMT TIME "+ ca.getTime());
		ca.add(Calendar.SECOND, (int)(jwtProperties.getValidityInMs()/1000));
//		System.out.println("GMT ADDED TIME "+ ca.getTime());
//		System.out.println("access expiry "+(int)(jwtProperties.getValidityInMs()/1000));
		Date accessExp=ca.getTime();
		//System.out.println("GMT EXPIRY TIME "+ accessExp);
		SimpleDateFormat df=new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
		
		HttpHeaders headers=new HttpHeaders();
		model.put("timestamp",new Date());
		try {
			User user=usersRepo.findByUsername(jwtTokenProvider.getUsernamefromRefresh(refresh_token)).get();
			if(user.isValid()) {
				if(jwtTokenProvider.validateRefreshToken(refresh_token)) {
		    		newAccessToken=jwtTokenProvider.createToken(user);
		    		//System.out.println("new access token "+ newAccessToken);
		            model.put("status",201);
		            model.put("error", false);
		            model.put("name", user.getName());
		            model.put("username",user.getUsername());
		            model.put("phone", user.getPhone());
		            model.put("roles", user.getRoles());
		            headers.add(HttpHeaders.SET_COOKIE, "access_token="+newAccessToken+"; Path=/; Expires="+df.format(accessExp)+"; HttpOnly");
		    	}
		    	else {
		    		model.put("status",403);
		            model.put("error", "Bad Request");
		            model.put("message", "Refresh token is invalid or expired");
		    	}
		    
		        model.put("path","auth/refresh");
			}
			else {
				throw new BadCredentialsException("Account blocked by admin");
			}
		} catch (Exception e) {
			
            model.put("error", "Bad Request");
            model.put("message", e.getMessage());
		}

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(model);
		
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/refresh/logout")
    public ResponseEntity logout(HttpServletRequest response) {
    	
    	Map<Object, Object> model = new HashMap<>();
		HttpHeaders headers=new HttpHeaders();
		Date date=Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime();
		SimpleDateFormat df=new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
        String d=df.format(date);
		
		Cookie [] cookies=response.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			cookies[i].setMaxAge(0);
			String path=(cookies[i].getName().equals("access_token"))?"/":"/auth/refresh";
			String c=cookies[i].getName()+"="+cookies[i].getValue()+"; Path="+path+"; Expires="+d+"; HttpOnly";
			//System.out.println("cookie "+i+"  "+c);
			headers.add(HttpHeaders.SET_COOKIE, c);
		}
		model.put("timestamp",new Date());
		
		return ResponseEntity.ok().headers(headers).build();
    	
    }
}


