package com.example.authApp.web;

import static org.springframework.http.ResponseEntity.ok;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.authApp.web.AuthRequest;
	

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
            String token = jwtTokenProvider.createToken(user);
            String refreshToken= jwtTokenProvider.createRefreshToken(user);
            
            Map<Object, Object> model = new HashMap<>();
            model.put("timestamp",new Date());
            model.put("status",200);
            model.put("access_token", token);
            model.put("expiry",jwtProperties.getValidityInMs());
            model.put("refresh_token", refreshToken);
            model.put("path", "auth/signin");
            return ok(model);
            
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
            String phone=data.getPhone();
            
            usersRepo.save(new User(username,passwordEncoder.encode(password),name, phone,Arrays.asList( "ROLE_USER")));
            
            Map<Object, Object> model = new HashMap<>();
            model.put("timestamp",new Date());
            model.put("status",201);
            model.put("error", false);
            model.put("message", "Successfully Registerd!!");
            model.put("path","auth/register");
            return ok(model);
    }
    
    @SuppressWarnings("rawtypes")
    @PostMapping("/refresh")
    public ResponseEntity refresh(@RequestBody RefreshRequest data) {
		Map<Object, Object> model = new HashMap<>();
		String refreshToken=data.getRefresh_token();
		String newRefeshToken, newAccessToken;
		model.put("timestamp",new Date());
		User user=usersRepo.findByUsername(jwtTokenProvider.getUsernamefromRefresh(refreshToken)).get();
		if(jwtTokenProvider.validateRefreshToken(refreshToken)) {
    		newAccessToken=jwtTokenProvider.createToken(user);
    		newRefeshToken=jwtTokenProvider.createRefreshToken(user);
            model.put("status",201);
            model.put("error", false);
            model.put("access_token",newAccessToken);
            model.put("refresh_token",newRefeshToken);
            
    	}
    	else {
    		model.put("status",403);
            model.put("error", "Bad Request");
            model.put("message", "Refresh token is invalid or expired");
    	}
    
        model.put("path","auth/refresh");
        return ok(model);
    }
}


