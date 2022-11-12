package pl.edu.pk.cosmo.habsatbackend.userservice.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class RandomPasswordGeneratorTest {
    private RandomPasswordGenerator randomPasswordGenerator = new RandomPasswordGenerator(new Random());

    @Test
    void shouldGenerateRandomPassword() {
        List<String> passwords = IntStream.rangeClosed(1, 8)
                .mapToObj(i -> randomPasswordGenerator.generateRandomPassword())
                .collect(Collectors.toList());

        assertAll(
                ()-> Objects.equals(passwords.size(), 8),
                ()-> Objects.equals(Set.of(passwords).size(), passwords.size())
        );
    }
}