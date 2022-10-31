package pl.edu.pk.cosmo.habsatbackend.userservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.User;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.AddUserRequest;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.ChangePasswdRequest;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.ChangeRoleRequest;
import pl.edu.pk.cosmo.habsatbackend.userservice.exception.EmailTakenException;
import pl.edu.pk.cosmo.habsatbackend.userservice.exception.NoUserException;
import pl.edu.pk.cosmo.habsatbackend.userservice.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> findAllUsers() {
        return userService.getUsers();
    }

    @PostMapping("/user")
    public void addUser(@RequestBody AddUserRequest user) {
        try {
            userService.addUser(user);
        } catch (EmailTakenException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  e.getMessage());
        }
    }

    @PatchMapping("/user/password")
    public void modifyPassword(@RequestBody ChangePasswdRequest changePasswdRequest) {
        try {
            userService.modifyPassword(changePasswdRequest);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  e.getMessage());
        }
    }

    @PostMapping("/user/role/add")
    public void addRole(@RequestBody ChangeRoleRequest changeRoleRequest) {
        try {
            userService.addRole(changeRoleRequest);
        } catch (NoUserException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  e.getMessage());
        }
    }

    @PostMapping("/user/role/delete")
    public void deleteRole(@RequestBody ChangeRoleRequest changeRoleRequest) {
        try {
            userService.deleteRole(changeRoleRequest);
        } catch (NoUserException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  e.getMessage());
        }
    }
}
