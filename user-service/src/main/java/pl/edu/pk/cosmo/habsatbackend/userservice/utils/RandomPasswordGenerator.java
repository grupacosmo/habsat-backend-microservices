package pl.edu.pk.cosmo.habsatbackend.userservice.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class RandomPasswordGenerator {
    private static final int RANDOM_PASSWD_SIZE = 10;
    private static final int RANDOM_PASSWD_LEFT_LIMIT = 97;
    private static final int RANDOM_PASSWD_RIGHT_LIMIT = 122;

    public String generateRandomPassword() {
        Random random = new Random();

        return random.ints(RANDOM_PASSWD_LEFT_LIMIT, RANDOM_PASSWD_RIGHT_LIMIT + 1)
                .limit(RANDOM_PASSWD_SIZE)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
