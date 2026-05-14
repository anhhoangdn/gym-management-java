package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordUtil {

    private PasswordUtil() {
    }

    public static String hashPassword(String password) {
        if (password == null)
            return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean verifyPassword(String rawPassword, String storedPassword) {
        if (rawPassword == null || storedPassword == null)
            return false;

        // Xử lý tương thích ngược với mật khẩu cũ (có tiền tố sha256$ do AI cũ tạo)
        if (storedPassword.startsWith("sha256$")) {
            String hash = hashPassword(rawPassword);
            return hash != null && storedPassword.substring(7).equals(hash);
        }

        // Tương thích với pass chưa hash (plain text) hoặc pass mới đã hash (ko có tiền
        // tố)
        String hashed = hashPassword(rawPassword);
        return rawPassword.equals(storedPassword) || (hashed != null && hashed.equals(storedPassword));
    }
}
