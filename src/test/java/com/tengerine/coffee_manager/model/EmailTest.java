package com.tengerine.coffee_manager.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void testInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            var email = new Email("acccc");
        });
    }

    @Test
    void testValidEmail() {
        var email = new Email("hello@naver.com");
        var email2 = new Email("hello@naver.com");
        assertEquals(email.getAddress(), email2.getAddress());
    }
}