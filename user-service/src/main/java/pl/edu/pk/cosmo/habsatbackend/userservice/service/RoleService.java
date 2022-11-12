package pl.edu.pk.cosmo.habsatbackend.userservice.service;

import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.AddRolesRequest;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.ChangeRoleRequest;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.response.UserResponse;
import pl.edu.pk.cosmo.habsatbackend.userservice.exception.NoRoleException;
import pl.edu.pk.cosmo.habsatbackend.userservice.exception.NoUserException;

import java.util.List;

public interface RoleService {
    void addRole(ChangeRoleRequest changeRoleRequest) throws NoUserException;
    void deleteRole(ChangeRoleRequest changeRoleRequest) throws NoUserException, NoRoleException;
    List<String> getRolesByEmail(String email) throws NoUserException;
    void addAllRoles(AddRolesRequest addRolesRequest) throws NoUserException;
}
