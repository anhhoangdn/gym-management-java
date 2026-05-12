package util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordUtil {

    private static final String PREFIX = "sha256$";

    private PasswordUtil() {
    }

    public static String hashPassword(String rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        return PREFIX + sha256Hex(rawPassword);
    }

    public static String hashPassword(char[] rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        return PREFIX + sha256Hex(rawPassword);
    }

    public static boolean verifyPassword(String rawPassword, String storedPassword) {
        if (rawPassword == null || storedPassword == null || storedPassword.isEmpty()) {
            return false;
        }

        if (storedPassword.startsWith(PREFIX)) {
            return hashPassword(rawPassword).equals(storedPassword);
        }

        // Backward compatibility for old plain-text data.
        return rawPassword.equals(storedPassword);
    }

    public static boolean verifyPassword(char[] rawPassword, String storedPassword) {
        if (rawPassword == null || storedPassword == null || storedPassword.isEmpty()) {
            return false;
        }

        if (storedPassword.startsWith(PREFIX)) {
            return hashPassword(rawPassword).equals(storedPassword);
        }

        if (storedPassword.length() != rawPassword.length) {
            return false;
        }

        for (int i = 0; i < rawPassword.length; i++) {
            if (storedPassword.charAt(i) != rawPassword[i]) {
                return false;
            }
        }
        return true;
    }

    private static String sha256Hex(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    }

    private static String sha256Hex(char[] value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            ByteBuffer buffer = StandardCharsets.UTF_8.encode(CharBuffer.wrap(value));
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            byte[] hash = digest.digest(bytes);
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = 0;
            }
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    }
}
