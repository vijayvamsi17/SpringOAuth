package com.OAuth.demo.services;


import com.OAuth.demo.entities.User;
import com.OAuth.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service("userDetailsService")
public class UserServiceImpl implements UserDetailsService {

    @Autowired private UserRepository userRepository;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

    /* Test Users*/

/*    @PostConstruct
    public  void createNewUsers() {
        User user1 = new User();
        user1.setUsername("vijay");
        user1.setPassword(bCryptPasswordEncoder.encode("vamsi1709"));
        user1.setEnabled(true);
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("John");
        user2.setPassword(bCryptPasswordEncoder.encode("test123"));
        user2.setEnabled(true);
        userRepository.save(user2);
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid Username or Password");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Arrays.asList(
                        new SimpleGrantedAuthority("ROLE_ADMIN")
                ));
    }
}
