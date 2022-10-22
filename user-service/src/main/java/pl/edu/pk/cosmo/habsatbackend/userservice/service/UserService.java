package pl.edu.pk.cosmo.habsatbackend.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pk.cosmo.habsatbackend.userservice.converter.UserConverter;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.User;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.AddUserRequest;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.ChangePasswdRequest;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.ChangeRoleRequest;
import pl.edu.pk.cosmo.habsatbackend.userservice.exception.EmailTakenException;
import pl.edu.pk.cosmo.habsatbackend.userservice.exception.NoUserException;
import pl.edu.pk.cosmo.habsatbackend.userservice.exception.PasswordMismatchException;
import pl.edu.pk.cosmo.habsatbackend.userservice.repository.UserRepository;
import pl.edu.pk.cosmo.habsatbackend.userservice.utils.RandomPasswordGenerator;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void addUser(AddUserRequest userRequest) throws EmailTakenException {
        final String email = userRequest.getEmail();

        if(userRepository.existsByEmail(email)) {
            throw new EmailTakenException(String.format("User with email %s already exists", email));
        }

        final User user = userConverter.userOf(userRequest);
        userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void modifyPassword(ChangePasswdRequest changePasswdRequest) throws NoUserException, PasswordMismatchException {
        final String email = changePasswdRequest.getEmail();

        if(!userRepository.existsByEmail(email)) {
            throw new NoUserException(String.format("There is no user with %s email", email));
        }

        User user = userRepository.findByEmail(email).get();

        if(!bCryptPasswordEncoder.matches(changePasswdRequest.getOldPassword(), user.getPassword())) {
            throw new PasswordMismatchException("Given passwords do not match!");
        }

        user.setPassword(bCryptPasswordEncoder.encode(changePasswdRequest.getNewPassword()));
        user.setFirstLogin(false);
        userRepository.save(user);
    }

    @Transactional
    public void addRole(ChangeRoleRequest changeRoleRequest) throws NoUserException {
        final String email = changeRoleRequest.getEmail();

        if(!userRepository.existsByEmail(email)) {
            throw new NoUserException(String.format("There is no user with %s email", email));
        }

        User user = userRepository.findByEmail(email).get();

        List<String> roles = new ArrayList<>(List.of(user
                .getRoles()
                .split(" ")));

        roles.add(changeRoleRequest.getRole());
        user.setRoles(String.join(" ", roles).strip());
        userRepository.save(user);
    }

    public void deleteRole(ChangeRoleRequest changeRoleRequest) throws NoUserException {
        final String email = changeRoleRequest.getEmail();

        if(!userRepository.existsByEmail(email)) {
            throw new NoUserException(String.format("There is no user with %s email", email));
        }

        User user = userRepository.findByEmail(email).get();

        List<String> roles = new ArrayList<>(List.of(user
                .getRoles()
                .split(" ")));

        roles.remove(changeRoleRequest.getRole());
        user.setRoles(String.join(" ", roles).strip());
        userRepository.save(user);
    }
}
