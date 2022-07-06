package com.code.pavan.expensetrackerapi.service;

import com.code.pavan.expensetrackerapi.entity.User;
import com.code.pavan.expensetrackerapi.exception.ItemAlreadyExistsException;
import com.code.pavan.expensetrackerapi.exception.ResourceNotFoundException;
import com.code.pavan.expensetrackerapi.model.UserModel;
import com.code.pavan.expensetrackerapi.repository.UserRepository;
import net.bytebuddy.pool.TypePool;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Override
    public User createUser(UserModel userModel) {

        if(userRepository.existsByEmail(userModel.getEmail())){
            throw new ItemAlreadyExistsException("User already registered with email: "+userModel.getEmail());
        }
        User user=new User();
        BeanUtils.copyProperties(userModel,user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User read() {
        Long userId = getLoggedInUser().getId();
        return userRepository.findById(userId).orElseThrow(()->new TypePool.Resolution.NoSuchTypeException("User not found for the id: "+userId));
    }

    @Override
    public User update(User user) throws ResourceNotFoundException {

        User oUser =read();
        oUser.setName((user.getName() !=null ? user.getName():oUser.getName()));
        oUser.setEmail((user.getEmail() !=null ? user.getEmail():oUser.getEmail()));
        oUser.setPassword((user.getPassword() !=null ? passwordEncoder.encode(user.getPassword()):oUser.getPassword()));
        oUser.setAge((user.getAge() !=null ? user.getAge():oUser.getAge()));

        return userRepository.save(oUser);
    }

    @Override
    public void delete() {
        User dUser=read();
        userRepository.delete(dUser);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found for the email"));
    }
}
