package com.examplejwtwiththymeleaf.demousercrudoperation.service;

import com.examplejwtwiththymeleaf.demousercrudoperation.entity.Options;
import com.examplejwtwiththymeleaf.demousercrudoperation.entity.User;
import com.examplejwtwiththymeleaf.demousercrudoperation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public void save(User user) {
        userRepository.save(user);
    }
    public User findByName(String username) {
        return userRepository.findByName(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
    public User findUserByName(String username) {
        return userRepository.findByUsername(username);
    }
    public User findByMail(String mail){
        return userRepository.findByMail(mail);
    }
    public User findById(int id){
        Optional<User> result =userRepository.findById(id);
        User user = null;
        if(result.isPresent()){
            user = result.get();
        }
        else{
            throw new RuntimeException("user did not found - "+id);
        }
        return user;
    }
    public void deleteById(int userId){
        userRepository.deleteById(userId);
    }
}
