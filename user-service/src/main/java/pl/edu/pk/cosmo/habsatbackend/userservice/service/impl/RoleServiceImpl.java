package pl.edu.pk.cosmo.habsatbackend.userservice.service.impl;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pk.cosmo.habsatbackend.userservice.converter.RoleConverter;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.User;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.AddRolesRequest;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.ChangeRoleRequest;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.response.UserResponse;
import pl.edu.pk.cosmo.habsatbackend.userservice.exception.NoRoleException;
import pl.edu.pk.cosmo.habsatbackend.userservice.exception.NoUserException;
import pl.edu.pk.cosmo.habsatbackend.userservice.repository.UserRepository;
import pl.edu.pk.cosmo.habsatbackend.userservice.service.RoleService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleConverter roleConverter;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void addRole(ChangeRoleRequest changeRoleRequest) throws NoUserException {
        final String email = changeRoleRequest.getEmail();

        if(!userRepository.existsByEmail(email)) {
            throw new NoUserException(String.format("There is no user with %s email", email));
        }

        User user = userRepository.findByEmail(email).get();

        List<String> roles = new ArrayList<>(roleConverter.toList(user.getRoles()));

        roles.add(changeRoleRequest.getRole());
        user.setRoles(roleConverter.toString(roles));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void addAllRoles(AddRolesRequest addRolesRequest) throws NoUserException {
        final String email = addRolesRequest.getEmail();

        if(!userRepository.existsByEmail(email)) {
            throw new NoUserException(String.format("There is no user with %s email", email));
        }

        User user = userRepository.findByEmail(email).get();
        Set<String> roles = new LinkedHashSet<>(List.of(user.getRoles().split(" ")));

        roles.addAll(addRolesRequest.getRoles());

        user.setRoles(roleConverter.toString(roles));
        userRepository.save(user);
    }

    @Override
    public void deleteRole(ChangeRoleRequest changeRoleRequest) throws NoUserException, NoRoleException {
        final String email = changeRoleRequest.getEmail();
        final String requestedRole = changeRoleRequest.getRole();

        if(!userRepository.existsByEmail(email)) {
            throw new NoUserException(String.format("There is no user with %s email", email));
        }

        User user = userRepository.findByEmail(email).get();
        List<String> roles = roleConverter.toList(user.getRoles());

        if(!roles.contains(requestedRole)) {
            throw new NoRoleException(String.format("%s does not have role: %s", email, requestedRole));
        }

        roles.remove(changeRoleRequest.getRole());
        user.setRoles(roleConverter.toString(roles));
        userRepository.save(user);
    }

    @Override
    public List<String> getRolesByEmail(String email) throws NoUserException {
        return userRepository.findByEmail(email)
                .map(User::getRoles)
                .map(roleConverter::toList)
                .orElseThrow(()-> new NoUserException("There is no such user with " + email + " email"));
    }
}
