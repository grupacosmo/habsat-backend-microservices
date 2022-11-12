package pl.edu.pk.cosmo.habsatbackend.userservice.service;

import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.AddUserRequest;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.ChangePasswdRequest;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.ChangeRoleRequest;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.response.UserResponse;
import pl.edu.pk.cosmo.habsatbackend.userservice.exception.EmailTakenException;
import pl.edu.pk.cosmo.habsatbackend.userservice.exception.NoUserException;
import pl.edu.pk.cosmo.habsatbackend.userservice.exception.PasswordMismatchException;

import java.util.List;

public interface UserService {
    List<UserResponse> getUsers();
    UserResponse getUserByEmail(String email) throws NoUserException;
    void addUser(AddUserRequest userRequest) throws EmailTakenException;
    void deleteUser(String email) throws NoUserException;
    void modifyPassword(ChangePasswdRequest changePasswdRequest) throws NoUserException, PasswordMismatchException;
    List<UserResponse> getUsersByRole(String role);
}
