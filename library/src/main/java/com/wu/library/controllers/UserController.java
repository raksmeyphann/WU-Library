package com.wu.library.controllers;

import com.wu.library.LibraryApplication;
import com.wu.library.models.User;
import com.wu.library.repositories.admin.UserRepository;
import com.wu.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("admin/user/")
public class UserController {

    UserRepository userRepository;
    UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("create")
    public String create(ModelMap model){
        model.addAttribute("user", new User());
        return "admin/User/create";
    }

    @GetMapping("/all")
    public String getAll(ModelMap modelMap)
    {
        List<User> userList = this.userService.getAll();
        modelMap.addAttribute("users",userList);
        return "admin/User/index";
    }

    @PostMapping("create/submit")
    public String createSubmit(@ModelAttribute @Valid User user, MultipartFile file, @RequestParam("role") String role, ModelMap modelMap){

        System.out.println("User"+user.getAddress());
        for (User admin: this.userRepository.getAllUser()) {
            if (admin.getUsername().equals(user.getUsername()) ){
                modelMap.addAttribute("error", true);
                modelMap.addAttribute("errorType","email");
                return "admin/user/create";
            }

        }

        if(file == null)
             return null;

         File path = new File("/wu");

         if(!path.exists())
             path.mkdir();

         String filename = file.getOriginalFilename();
         String extension = filename.substring(filename.lastIndexOf('.')+1);;

         filename = UUID.randomUUID() + "." + extension;

        try {
            Files.copy(file.getInputStream(), Paths.get("/wu",filename));
        } catch (IOException e) {
            e.printStackTrace();
        };

        user.setProfileImg("/image-wu/"+filename);
        String pass = LibraryApplication.getEncoder().encode(user.getPassword());
        user.setPassword(pass);
        this.userService.insert(user);
        this.userRepository.insertUserRole(role,user.getId());
        return "redirect:/admin/book/all";
    }

}
