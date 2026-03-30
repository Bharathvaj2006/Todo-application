package com.secure.security.controller;

import com.secure.security.models.User;
import com.secure.security.repository.UserRepository;
import com.secure.security.service.UserService;
import com.secure.security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    public UserController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody Map<String,String> body){
        String email=body.get("email");
        String password=passwordEncoder.encode(body.get("password"));
        if(userRepository.findByEmail(email).isPresent()){
            return new ResponseEntity<>("User is already Exist", HttpStatus.CONFLICT);
        }
        userService.creatUser(new User().setEmail(email).setPassward(password));
        return new ResponseEntity<>("Successfully Registered",HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<?> userLodin(@RequestBody Map<String,String> body){
        String email=body.get("email");
        String password=body.get("password");
        var userOption=userRepository.findByEmail(email);
        if(userOption.isEmpty()){
            return new ResponseEntity<>("User is not Regitered",HttpStatus.UNAUTHORIZED);
        }

        User user=userOption.get();
        if(!passwordEncoder.matches(password,user.getPassward())){
            return new ResponseEntity<>("Invalid password",HttpStatus.UNAUTHORIZED);
        }

        String token=jwtUtil.generateToken(email);
        return ResponseEntity.ok(Map.of("token",token));

    }

}
