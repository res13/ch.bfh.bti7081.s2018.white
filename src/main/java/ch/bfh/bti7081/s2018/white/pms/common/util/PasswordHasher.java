package ch.bfh.bti7081.s2018.white.pms.common.util;

import ch.bfh.bti7081.s2018.white.pms.persistence.JpaUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class PasswordHasher {

    private static final String KEY_FACTORY = "PBKDF2WithHmacSHA1";

    public static final Logger log = LogManager.getLogger(PasswordHasher.class.getName());



    public static String hashPlainTextPassword(String plaintextPassword, String salt) throws Exception {
        Base64.Decoder dec = Base64.getDecoder();
        KeySpec spec = new PBEKeySpec(plaintextPassword.toCharArray(), dec.decode(salt), 65536, 128);
        SecretKeyFactory f = SecretKeyFactory.getInstance(KEY_FACTORY);
        byte[] hash = f.generateSecret(spec).getEncoded();
        Base64.Encoder enc = Base64.getEncoder();
        return enc.encodeToString(hash);
    }

    public static String getRandomSalt() {
        byte[] salt = new byte[16];
        new Random().nextBytes(salt);
        Base64.Encoder enc = Base64.getEncoder();
        return enc.encodeToString(salt);
    }

    public static boolean validate(String plaintextPassword, String salt, String passwordHash) {
        try {
            return hashPlainTextPassword(plaintextPassword, salt).equals(passwordHash);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }
}
