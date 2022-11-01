package pl.edu.pk.cosmo.habsatbackend.postsservice.validators;

import org.junit.jupiter.api.Test;
import pl.edu.pk.cosmo.habsatbackend.postsservice.validators.AlphanumericValidator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AlphanumericValidatorUnitTest {
    private AlphanumericValidator validator = new AlphanumericValidator();

    @Test
    public void shouldPassWithCorrectValues() {
        assertThat(validator.isValid("letters-and-numbers_123", null)).isTrue();
        assertThat(validator.isValid("OnlyLetters", null)).isTrue();
        assertThat(validator.isValid("__42__", null)).isTrue();
        assertThat(validator.isValid("daucus_carota", null)).isTrue();
    }

    @Test
    public void shouldFailWithIncorrectValues() {
        assertThat(validator.isValid("letters with spaces", null)).isFalse();
        assertThat(validator.isValid("¯\\_(ツ)_/¯", null)).isFalse();
        assertThat(validator.isValid("just+ordinary-sentence=123", null)).isFalse();
        assertThat(validator.isValid("#what's_up", null)).isFalse();
    }
}
