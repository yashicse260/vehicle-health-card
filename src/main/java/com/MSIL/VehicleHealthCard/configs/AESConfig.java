package com.MSIL.VehicleHealthCard.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class AESConfig {

    @Value("${aes.key}")
    private String key;

    @Value("${aes.nonce}")
    private String nonce;

    public byte[] getKeyBytes() {
        return key.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] getNonceBytes() {
        return nonce.getBytes(StandardCharsets.UTF_8);
    }
}
