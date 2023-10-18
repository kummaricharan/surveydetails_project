package com.examplejwtwiththymeleaf.demousercrudoperation.controller;


import com.examplejwtwiththymeleaf.demousercrudoperation.entity.Branch;
import com.examplejwtwiththymeleaf.demousercrudoperation.entity.User;
import com.examplejwtwiththymeleaf.demousercrudoperation.service.BranchesService;
import com.examplejwtwiththymeleaf.demousercrudoperation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private BranchesService branchesService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        User user = new User();
        List<Branch> branches = branchesService.findAll();

        model.addAttribute("user",user);

        model.addAttribute("branches",branches);

        return "survey/user-singup-form";
    }
    @GetMapping("update")
    public String update(){
        return "survey/user-update-form";
    }
    @PostMapping("/updatePassword")
    public String updateString(@RequestParam("email") String email,@RequestParam("password") String password,Model model){
        User users = userService.findByMail(email);
        if(users == null){
            model.addAttribute("error","user is not found");
            return "survey/user-update-form";
        }
        users.setPassword(passwordEncoder.encode(password));
        userService.save(users);
        model.addAttribute("success","user password has been updated");
        return "survey/login-form";
    }
    @GetMapping("/LoginPage")
    public String loginPage(){
        return "survey/login-form";
    }
    @PostMapping("/register")
    public String saveUserDetails(@ModelAttribute("user") User user,@RequestParam("email") String email,@RequestParam("username") String username,
                                  @RequestParam("role") String role,
                                  @RequestParam("password") String password,@RequestParam("branchId") int branchId,Model model){
        User users = userService.findByName(user.getUsername());

        List<Branch> branches = branchesService.findAll();

        model.addAttribute("branches",branches);
        if(users!=null){
            model.addAttribute("error","user name already present try with different one");
            return "survey/user-singup-form";
        }
        else{
            Branch branch = branchesService.findById(branchId);
            branch.getUsers().add(user);
            user.setBranch(branch);
            user.setEnabled(1);
            user.setRole(role);
            user.setPassword(passwordEncoder.encode(password) );
            userService.save(user);
            branchesService.save(branch);
            return "redirect:/survey/userdetails";
        }
    }
    @GetMapping("/editUser")
    public String editUser(@RequestParam("userId") int userId,Model model){
        List<Branch> branches = branchesService.findAll();
        model.addAttribute("branches",branches);
        User user = userService.findById(userId);
        model.addAttribute("user",user);
        return "survey/editUser";
    }
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") int userId,Model model){
        User user = userService.findById(userId);
        if(user==null){
            throw new RuntimeException("user not found exception");
        }
        userService.deleteById(userId);
        
        return "redirect:/survey/userdetails";
    }
    @PostMapping("/updateSave")
    public String updateSave(@ModelAttribute("user") User user,@RequestParam("userId") int userId,@RequestParam("email") String email,
                             @RequestParam("username") String username,@RequestParam("role") String role,@RequestParam("branchId") int branchId,
                             @RequestParam("password") String password){
        User userdata = userService.findById(userId);
        user.setId(userId);
        user.setEnabled(1);

        Branch branch = branchesService.findById(branchId);
        branch.getUsers().add(user);
        user.setBranch(branch);

        user.setRole(role);
        user.setPassword(passwordEncoder.encode(password) );
        userService.save(user);
        branchesService.save(branch);
        return  "redirect:/survey/userdetails";
    }
}

//        else if(username.equals("") || email.equals("") || password.equals("")){
//            if(username.equals("")){
//                model.addAttribute("userEmpty","user field is empty");
//                return "survey/user-singup-form";
//            }
//            else if(email.equals("")){
//                model.addAttribute("mailEmpty","mail field is empty");
//                return "survey/user-singup-form";
//            }
//            model.addAttribute("passwordEmpty","password field is empty");
//            return "survey/user-singup-form";
//        }