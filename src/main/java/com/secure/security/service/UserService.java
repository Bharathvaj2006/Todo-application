package com.secure.security.service;

import com.secure.security.models.User;
import com.secure.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User creatUser(User user) {
        var temp=userRepository.findByEmail(user.getEmail());
        if(temp.isPresent()) try {
            throw new Exception("User Already Exist");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userRepository.save(user);
    }

    public User getUser(Long id){
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("Not Fount"));
    }
}

