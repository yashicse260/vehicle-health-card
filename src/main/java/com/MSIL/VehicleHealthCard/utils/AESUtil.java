
package com.MSIL.VehicleHealthCard.utils;

import com.MSIL.VehicleHealthCard.configs.AESConfig;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AESUtil {

    private static final String AES_ALGO = "AES/GCM/NoPadding";
    private final byte[] KEY;
    private final byte[] NONCE;

    public AESUtil(AESConfig config) {
        this.KEY = config.getKeyBytes();
        this.NONCE = config.getNonceBytes();
    }

    /** Encrypt plain text using AES-256-GCM and return Base64 encoded string */
    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGO);
        SecretKeySpec keySpec = new SecretKeySpec(KEY, "AES");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, NONCE);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /** Decrypt Base64 encoded AES-256-GCM encrypted text */
    public String decrypt(String encryptedText) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGO);
        SecretKeySpec keySpec = new SecretKeySpec(KEY, "AES");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, NONCE);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);
        byte[] decoded = Base64.getDecoder().decode(encryptedText);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted, StandardCharsets.UTF_8);
    }
}
