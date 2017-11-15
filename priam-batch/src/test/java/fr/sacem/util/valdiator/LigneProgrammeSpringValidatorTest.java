package fr.sacem.util.valdiator;

import fr.sacem.domain.LigneProgramme;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;

/**
 * Created by benmerzoukah on 14/11/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
public class LigneProgrammeSpringValidatorTest {

    @Configuration
    static class ContextConfiguration {

        @Bean
        public LigneProgrammeSpringValidator validator() {
            return new LigneProgrammeSpringValidator();
        }
    }

    @Autowired
    LigneProgrammeSpringValidator validator;
    LigneProgramme ligneProgramme;
    Errors errors;

    @Before
    public void setUp() {
        ligneProgramme = new LigneProgramme();
        ligneProgramme.setIde12("12145888");
        ligneProgramme.setDurDif("45");
        ligneProgramme.setTax("478");
        ligneProgramme.setNbrDif("54");
        ligneProgramme.setDurDifCtna("233");
        ligneProgramme.setMt("2145");
        errors = new BeanPropertyBindingResult(ligneProgramme, "ligneProgramme");

    }

    @Test
    public void supports() throws Exception {
        assertThat(validator.supports(LigneProgramme.class)).isTrue();
    }

    @Test
    public void testValidateKO() throws Exception {
        validator.validate(ligneProgramme, errors);
        assertThat(errors).isNotNull();
        assertThat(errors.hasErrors()).isTrue();
    }

    @Test
    public void validateNumericFieldsOK() throws Exception {
        validator.validateNumericFields(errors, ligneProgramme);
        assertThat(errors.hasErrors()).isFalse();
    }

    @Test
    public void validateNumericField() throws Exception {

    }

}