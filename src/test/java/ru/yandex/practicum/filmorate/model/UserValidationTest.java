package ru.yandex.practicum.filmorate.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserValidationTest {
        @Autowired
        private Validator validator;

        @Test
        public void shouldBeValidUser() {
                User validUser = User.builder()
                                .id(0)
                                .email("foo@bar.com")
                                .login("John")
                                .name("Doe")
                                .birthday(LocalDate.of(1999, 02, 05))
                                .build();
                Set<ConstraintViolation<User>> violations = validator.validate(validUser, Update.class);
                assertTrue(violations.isEmpty());
        }

        @Test
        public void shouldBeInvalidUserEmail() {
                User invalidUser = User.builder()
                                .id(0)
                                .email("foo.bar.com")
                                .login("John")
                                .name("Doe")
                                .birthday(LocalDate.of(1999, 02, 05))
                                .build();
                Set<ConstraintViolation<User>> violations = validator.validate(invalidUser, Update.class);
                assertTrue(!violations.isEmpty());
                assertTrue(violations.size() == 1);
                assertTrue(violations.iterator().next().getMessage()
                                .equals("электронная почта не может быть пустой и должна содержать символ @"));
        }

        @Test
        public void shouldBeInvalidUserLogin() {
                User invalidUser = User.builder()
                                .id(0)
                                .email("foo@bar.com")
                                .login("John Doe")
                                .name("")
                                .birthday(LocalDate.of(1999, 02, 05))
                                .build();
                Set<ConstraintViolation<User>> violations = validator.validate(invalidUser, Update.class);
                assertTrue(!violations.isEmpty());
                assertTrue(violations.size() == 1);
                assertTrue(violations.iterator().next().getMessage()
                                .equals("логин не может быть пустым и содержать пробелы"));

                invalidUser = User.builder()
                                .id(0)
                                .email("foo@bar.com")
                                .login("")
                                .name("")
                                .birthday(LocalDate.of(1999, 02, 05))
                                .build();
                violations = validator.validate(invalidUser, Update.class);
                assertTrue(!violations.isEmpty());
                assertTrue(violations.size() == 1);
                assertTrue(violations.iterator().next().getMessage()
                                .equals("логин не может быть пустым и содержать пробелы"));
        }

        @Test
        public void shouldBeInvalidUserBirthday() {
                User invalidUser = User.builder()
                                .id(0)
                                .email("foo@bar.com")
                                .login("John")
                                .name("Doe")
                                .birthday(LocalDate.of(2099, 02, 05))
                                .build();
                Set<ConstraintViolation<User>> violations = validator.validate(invalidUser, Update.class);
                assertTrue(!violations.isEmpty());
                assertTrue(violations.size() == 1);
                assertTrue(violations.iterator().next().getMessage().equals("дата рождения не может быть в будущем"));

                invalidUser = User.builder()
                                .id(0)
                                .email("foo@bar.com")
                                .login("John")
                                .name("Doe")
                                .birthday(null)
                                .build();
                violations = validator.validate(invalidUser, Update.class);
                assertTrue(!violations.isEmpty());
                assertTrue(violations.size() == 1);
                assertTrue(violations.iterator().next().getMessage().equals("дата рождения не может быть пустой"));
        }
}
