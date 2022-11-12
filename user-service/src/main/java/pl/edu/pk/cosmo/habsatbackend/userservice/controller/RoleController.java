package pl.edu.pk.cosmo.habsatbackend.userservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.AddRolesRequest;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.ChangeRoleRequest;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.response.UserResponse;
import pl.edu.pk.cosmo.habsatbackend.userservice.exception.NoRoleException;
import pl.edu.pk.cosmo.habsatbackend.userservice.exception.NoUserException;
import pl.edu.pk.cosmo.habsatbackend.userservice.service.RoleService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;


    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getRolesByEmail(@PathVariable String email) {
        try {
            return roleService.getRolesByEmail(email);
        } catch(NoUserException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  e.getMessage());
        }
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addRole(@RequestBody ChangeRoleRequest changeRoleRequest) {
        try {
            roleService.addRole(changeRoleRequest);
        } catch (NoUserException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  e.getMessage());
        }
    }

    @PostMapping( "/addAll")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAllRoles(@RequestBody AddRolesRequest addRolesRequest) {
        try {
            roleService.addAllRoles(addRolesRequest);
        } catch (NoUserException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  e.getMessage());
        }
    }

    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@RequestBody ChangeRoleRequest changeRoleRequest) {
        try {
            roleService.deleteRole(changeRoleRequest);
        } catch (NoUserException | NoRoleException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  e.getMessage());
        }
    }
}
