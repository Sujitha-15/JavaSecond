package com.utilitysuite.tools;

import com.utilitysuite.UtilityTool;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

/**
 * Simple AES encryption/decryption utility. Use a passphrase to derive a 128-bit key.
 * Not intended as production-ready secret management; for learning/demo only.
 */
public class EncryptionTool implements UtilityTool {
    private final java.util.Scanner sc = new java.util.Scanner(System.in);

    @Override
    public void start() {
        System.out.println("\n--- Encryption Utility (AES) ---");
        System.out.println("1. Encrypt text");
        System.out.println("2. Decrypt text");
        System.out.print("Choice: ");
        String choice = sc.nextLine().trim();
        try {
            System.out.print("Enter passphrase: ");
            String pass = sc.nextLine();
            SecretKeySpec key = createKey(pass);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            if (choice.equals("1")) {
                System.out.print("Plain text: ");
                String plain = sc.nextLine();
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] enc = cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8));
                System.out.println("Encrypted (Base64): " + Base64.getEncoder().encodeToString(enc));
            } else if (choice.equals("2")) {
                System.out.print("Base64 cipher text: ");
                String ct = sc.nextLine();
                cipher.init(Cipher.DECRYPT_MODE, key);
                byte[] dec = cipher.doFinal(Base64.getDecoder().decode(ct));
                System.out.println("Decrypted: " + new String(dec, StandardCharsets.UTF_8));
            } else {
                System.out.println("Unknown choice");
            }
        } catch (Exception e) {
            System.out.println("Crypto error: " + e.getMessage());
        }
    }

    private SecretKeySpec createKey(String pass) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        byte[] key = sha.digest(pass.getBytes(StandardCharsets.UTF_8));
        key = Arrays.copyOf(key, 16); // use 128-bit key
        return new SecretKeySpec(key, "AES");
    }
}
