package ch.bfh.bti7081.s2018.white.pms.services.impl;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.common.util.PasswordHasher;
import ch.bfh.bti7081.s2018.white.pms.persistence.JpaUtility;
import junit.framework.TestCase;

import java.time.LocalDate;

public class UserServiceImplTest extends TestCase {

    private UserServiceImpl<User> userService = new UserServiceImpl<>(User.class);

    private Patient testUser;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        testUser = new Patient();
        testUser.setDateOfBirth(LocalDate.of(1993, 11, 13));
        testUser.setEmail("andreas.erb@gmx.ch");
        testUser.setName("Erb");
        testUser.setSurname("Andreas");
        String salt = PasswordHasher.getRandomSalt();
        testUser.setSalt(salt);
        testUser.setPasswordHash(PasswordHasher.hashPlainTextPassword("123456", salt));
        new JpaUtility().execute(
                (em) -> {
                    em.persist(testUser);
                    return null;
                });
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        new JpaUtility().execute(
                (em) -> {
                    Patient testUserToDelete = em.find(Patient.class, testUser.getId());
                    em.remove(testUserToDelete);
                    return null;
                });
    }

    public void testAuthentication() {
        try {
            User foundUser = userService.authenticate("andreas.erb@gmx.ch", "123456");
            assertEquals("Authentication test with correct username and password fails", testUser.getId(), foundUser.getId());
        } catch (Exception e) {
            fail("Authentication test with correct username and password fails");
        }
        try {
            userService.authenticate("asdf", "123456");
            fail("Authentication test with wrong username and correct password is valid?");
        } catch (Exception e) {
            assertEquals("Wrong exception thrown", MessageHandler.USERNAME_OR_PASSWORD_INCORRECT, e.getMessage());
        }
        try {
            userService.authenticate("andreas.erb@gmx.ch", "");
            fail("Authentication test with correct username and wrong password is valid?");
        } catch (Exception e) {
            assertEquals("Wrong exception thrown", MessageHandler.USERNAME_OR_PASSWORD_INCORRECT, e.getMessage());
        }
        try {
            userService.authenticate("", "");
            fail("Authentication test with wrong username and wrong password is valid?");
        } catch (Exception e) {
            assertEquals("Wrong exception thrown", MessageHandler.USERNAME_OR_PASSWORD_INCORRECT, e.getMessage());
        }
    }

    public void testEmailUniqueness() {
        Patient testUser2 = new Patient();
        testUser2.setDateOfBirth(LocalDate.of(1993, 11, 13));
        testUser2.setEmail("andreas.erb@gmx.ch");
        testUser2.setName("Erb");
        testUser2.setSurname("Andreas");
        String salt = PasswordHasher.getRandomSalt();
        testUser2.setSalt(salt);
        try {
            testUser2.setPasswordHash(PasswordHasher.hashPlainTextPassword("123456", salt));
        } catch (Exception e) {
            fail("Could not create user for test");
        }
        try {
            userService.saveOrUpdateEntity(testUser2);
            fail("It should have failed");
        } catch (Exception e) {
            assertEquals("Wrong exception thrown", MessageHandler.EMAIL_ALREADY_IN_USE, e.getMessage());
        }
    }

}
