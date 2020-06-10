package com.example.authApp.web;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.authApp.domain.User;

@RestController()
public class UserInfoController {

    @SuppressWarnings("rawtypes")
	@GetMapping("/me")
    public ResponseEntity currentUser(@AuthenticationPrincipal User userDetails){
        Map<Object, Object> model = new HashMap<>();
        model.put("timestamp", new Date());
        model.put("status", 200);
        model.put("error", false);
        model.put("name",userDetails.getName());
        model.put("username", userDetails.getUsername());
        model.put("roles", userDetails.getAuthorities()
            .stream()
            .map(a -> ((GrantedAuthority) a).getAuthority())
            .collect(toList())
        );
        model.put("path","/me");
        return ok(model);
    }
}
