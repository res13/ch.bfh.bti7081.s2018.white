package ch.bfh.bti7081.s2018.white.pms.common.util;

import junit.framework.TestCase;

public class PasswordHasherTest extends TestCase {

    public void testHasher() {
        String salt = PasswordHasher.getRandomSalt();
        assertNotNull(salt);
        assertNotSame("", salt);
        String plaintextPassword = "123456";
        String passwordHash = null;
        try {
            passwordHash = PasswordHasher.hashPlainTextPassword(plaintextPassword, salt);
            assertNotNull(passwordHash);
            assertNotSame("", passwordHash);
        } catch (Exception e) {
            fail("Could not create password hash");
        }
        String errorMessage = "Password hasher broken";
        assertTrue(errorMessage, PasswordHasher.validate(plaintextPassword, salt, passwordHash));
        assertFalse(errorMessage, PasswordHasher.validate("1234567", salt, passwordHash));
        assertFalse(errorMessage, PasswordHasher.validate("", salt, passwordHash));
        assertFalse(errorMessage, PasswordHasher.validate(plaintextPassword, "asdf", passwordHash));
        assertFalse(errorMessage, PasswordHasher.validate(plaintextPassword, salt, ""));
        assertFalse(errorMessage, PasswordHasher.validate(plaintextPassword, "asdf", ""));
    }
}
