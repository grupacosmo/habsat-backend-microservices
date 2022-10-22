package pl.edu.pk.cosmo.habsatbackend.userservice.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.User;
import pl.edu.pk.cosmo.habsatbackend.userservice.entity.request.AddUserRequest;
import pl.edu.pk.cosmo.habsatbackend.userservice.utils.RandomPasswordGenerator;

@Component
@RequiredArgsConstructor
public class UserConverter {
    private final RandomPasswordGenerator randomPasswordGenerator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User userOf(AddUserRequest addUserRequest) {
        return new User()
                .setName(addUserRequest.getName())
                .setSurname(addUserRequest.getSurname())
                .setEmail(addUserRequest.getEmail())
                .setPassword(
                        bCryptPasswordEncoder.encode(
                                randomPasswordGenerator.generateRandomPassword()
                        )
                )
                .setFirstLogin(true)
                .setRoles("USER");
    }
}
