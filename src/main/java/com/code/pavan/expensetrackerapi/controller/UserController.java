package com.code.pavan.expensetrackerapi.controller;

import com.code.pavan.expensetrackerapi.entity.User;
import com.code.pavan.expensetrackerapi.exception.ResourceNotFoundException;
import com.code.pavan.expensetrackerapi.model.UserModel;
import com.code.pavan.expensetrackerapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> get(){
        return new ResponseEntity<>(userService.read(),HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<User> update(@RequestBody User user){
        User mUser =userService.update(user);
        return new ResponseEntity<>(mUser,HttpStatus.OK);
    }

    @DeleteMapping("/deactivate")
    public ResponseEntity<HttpStatus> delete() throws ResourceNotFoundException {
        userService.delete();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
