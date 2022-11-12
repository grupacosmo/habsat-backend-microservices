package pl.edu.pk.cosmo.habsatbackend.userservice.utils.validators;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import pl.edu.pk.cosmo.habsatbackend.userservice.utils.RandomPasswordGenerator;

import javax.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class EmailValidatorTest {

    @InjectMocks
    private EmailValidator emailValidator;

    @BeforeEach
    private void beforeEach(){
        emailValidator = new EmailValidator();
    }

    @Test
    void emailIsNull_shouldReturnFalse() {
        final String email = null;

        boolean ret = emailValidator.isValid(email, any(ConstraintValidatorContext.class));

        assertThat(ret).isEqualTo(false);
    }

    @Test
    void emailsAreNotValid_shouldReturnFalse() {
        final RandomPasswordGenerator randomPasswordGenerator =  new RandomPasswordGenerator(new Random());
        IntStream.rangeClosed(1, 10)
                .mapToObj(i -> randomPasswordGenerator.generateRandomPassword())
                .forEach(email -> {
                    assertFalse(emailValidator.isValid(email, any(ConstraintValidatorContext.class)));
                });
    }

    @Test
    void emailAreValid_shouldReturnTrue() {
        final Faker faker = new Faker();

        IntStream.rangeClosed(1, 10)
                .mapToObj(i -> faker.internet().emailAddress())
                .forEach(email -> {
                    assertTrue(emailValidator.isValid(email, any(ConstraintValidatorContext.class)));
                });
    }
}