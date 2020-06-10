package com.dev.admin.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class Router {
    
    @Autowired
    UserRepository userRepository;

    @PostMapping(value="/user")
    public String create(@RequestBody final User user){
        try{
            userRepository.save(user);
            return "Added";

        }
        catch(Exception e){
            return e.toString();
        }
    }
    
    @GetMapping(value="/user")
    public List<User> read(){
        return userRepository.findAll();
    }
    
    @PutMapping(value="/user/{uid}")
    public String update(@PathVariable("uid") final long uid){
        try{
        User usr = userRepository.getOne(uid);
        userRepository.delete(usr);
        return "Updated";
        }
        catch(Exception e){
            return e.toString();
        }
    }

    @DeleteMapping(value="/user/{uid}")
    public String delete(@PathVariable("uid") final long uid){
        try{
            User usr = userRepository.getOne(uid);
            userRepository.delete(usr);
            return "Deleted";
            }
            catch(Exception e){
                return e.toString();
            }
    }
   
}