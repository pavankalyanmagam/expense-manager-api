package com.code.pavan.expensetrackerapi.controller;

import com.code.pavan.expensetrackerapi.entity.User;
import com.code.pavan.expensetrackerapi.model.AuthModel;
import com.code.pavan.expensetrackerapi.model.JwtResponse;
import com.code.pavan.expensetrackerapi.model.UserModel;
import com.code.pavan.expensetrackerapi.security.CustomUserDetailsService;
import com.code.pavan.expensetrackerapi.service.UserService;
import com.code.pavan.expensetrackerapi.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthModel authModel) throws Exception{
        authenticate(authModel.getEmail(),authModel.getPassword());
        // we need to generate the jwt token
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authModel.getEmail());
        final String token =jwtTokenUtil.generateToken(userDetails);

        return new ResponseEntity<>(new JwtResponse(token),HttpStatus.OK);
    }

    private void authenticate(String email, String password) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        }
        catch (DisabledException e){
            throw new Exception("User Disabled");
        }
        catch (BadCredentialsException e){
            throw new Exception("Bad Credentials");
        }

    }

    @PostMapping("/register")
    public ResponseEntity<User> save(@Valid @RequestBody UserModel userModel){
        return new ResponseEntity<>(userService.createUser(userModel), HttpStatus.CREATED);
    }
}
