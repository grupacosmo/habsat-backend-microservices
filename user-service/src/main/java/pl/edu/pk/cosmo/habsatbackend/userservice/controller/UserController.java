package pl.edu.pk.cosmo.habsatbackend.userservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.Mail;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.AddUserRequest;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.ChangePasswdRequest;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.Credentials;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.response.UserResponse;
import pl.edu.pk.cosmo.habsatbackend.userservice.exception.EmailTakenException;
import pl.edu.pk.cosmo.habsatbackend.userservice.exception.NoUserException;
import pl.edu.pk.cosmo.habsatbackend.userservice.service.EmailService;
import pl.edu.pk.cosmo.habsatbackend.userservice.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserResponse> findAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{email}")
    public UserResponse getUserByEmail(@PathVariable String email) {
        try {
            return userService.getUserByEmail(email);
        } catch (NoUserException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody @Valid AddUserRequest user) {
        try {
            userService.addUser(user);
        } catch (EmailTakenException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  e.getMessage());
        }
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String email) {
        try {
            userService.deleteUser(email);
        } catch(NoUserException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  e.getMessage());
        }
    }

    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyPassword(@RequestBody ChangePasswdRequest changePasswdRequest) {
        try {
            userService.modifyPassword(changePasswdRequest);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  e.getMessage());
        }
    }


    @GetMapping("/{role}/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getUsersByRole(@PathVariable String role) {
        return userService.getUsersByRole(role);
    }

    @PostMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    public void validateUserCredentials(@RequestBody Credentials credentials) {
        try {
            userService.validateCredentials(credentials);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,  e.getMessage());
        }
    }
}
