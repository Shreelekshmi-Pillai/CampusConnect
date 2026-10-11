package util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtil {

    private static final int ITERATIONS = 210000;
    private static final int SALT_LENGTH = 16;
    private static final int HASH_LENGTH = 32;

    public static String hashPassword(char[] password)
            throws Exception {

        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);

        PBEKeySpec spec = new PBEKeySpec(
            password, salt, ITERATIONS, HASH_LENGTH * 8
        );

        byte[] hash = null;

        try {
            SecretKeyFactory factory =
                SecretKeyFactory.getInstance(
                    "PBKDF2WithHmacSHA256"
                );

            hash = factory.generateSecret(spec).getEncoded();

            return "PBKDF2_SHA256$" + ITERATIONS + "$"
                + toHex(salt) + "$" + toHex(hash);

        } finally {
            spec.clearPassword();

            if (hash != null) {
                java.util.Arrays.fill(hash, (byte) 0);
            }

            java.util.Arrays.fill(salt, (byte) 0);
        }
    }

    public static boolean verifyPassword(
            char[] password, String storedHash)
            throws Exception {

        if (storedHash == null) {
            return false;
        }

        String[] parts = storedHash.split("\\$");

        if (parts.length != 4
                || !parts[0].equals("PBKDF2_SHA256")) {
            return false;
        }

        int iterations = Integer.parseInt(parts[1]);

        if (iterations < 10000 || iterations > 1000000) {
            return false;
        }

        byte[] salt = fromHex(parts[2]);
        byte[] expected = fromHex(parts[3]);

        PBEKeySpec spec = new PBEKeySpec(
            password, salt, iterations, expected.length * 8
        );

        byte[] actual = null;

        try {
            SecretKeyFactory factory =
                SecretKeyFactory.getInstance(
                    "PBKDF2WithHmacSHA256"
                );

            actual = factory.generateSecret(spec).getEncoded();

            return MessageDigest.isEqual(expected, actual);

        } finally {
            spec.clearPassword();

            if (actual != null) {
                java.util.Arrays.fill(actual, (byte) 0);
            }

            java.util.Arrays.fill(expected, (byte) 0);
            java.util.Arrays.fill(salt, (byte) 0);
        }
    }

    private static String toHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();

        for (byte b : bytes) {
            result.append(String.format("%02x", b & 0xff));
        }

        return result.toString();
    }

    private static byte[] fromHex(String value) {
        if (value.length() % 2 != 0) {
            throw new IllegalArgumentException("Invalid hex");
        }

        byte[] result = new byte[value.length() / 2];

        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) Integer.parseInt(
                value.substring(i * 2, i * 2 + 2), 16
            );
        }

        return result;
    }
}