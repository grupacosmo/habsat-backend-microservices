package pl.edu.pk.cosmo.habsatbackend.userservice.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class RandomPasswordGenerator {
    private static final int RANDOM_PASSWD_SIZE = 10;
    private static final int RANDOM_PASSWD_LEFT_LIMIT = 97;
    private static final int RANDOM_PASSWD_RIGHT_LIMIT = 122;

    private final Random random;

    public String generateRandomPassword() {
        return random.ints(RANDOM_PASSWD_LEFT_LIMIT, RANDOM_PASSWD_RIGHT_LIMIT + 1)
                .limit(RANDOM_PASSWD_SIZE)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
