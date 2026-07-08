package com.sts.jpa.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;

public class PasswordCrypto {

    private static final String SECRET_KEY = "MySecretKey12345"; // Example only
    private static final String ALGORITHM = "AES";

    /**
     * Encrypts a plain text password using AES.
     *
     * @param password The plain text password
     * @return Base64 encoded encrypted string
     * @throws GeneralSecurityException if encryption fails
     */
    public static String encrypt(String password) throws GeneralSecurityException {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // ECB for simplicity; use CBC/GCM in production
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts an AES encrypted password.
     *
     * @param encryptedPassword Base64 encoded encrypted string
     * @return Decrypted plain text password
     * @throws GeneralSecurityException if decryption fails
     */
    public static String decrypt(String encryptedPassword) throws GeneralSecurityException {
        if (encryptedPassword == null) {
            throw new IllegalArgumentException("Encrypted password cannot be null");
        }

        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        try {

            String encrypted = encrypt("root");
            System.out.println("Encrypted Password: " + encrypted);

        } catch (GeneralSecurityException e) {
            System.err.println("Encryption/Decryption error: " + e.getMessage());
        }
    }
}
