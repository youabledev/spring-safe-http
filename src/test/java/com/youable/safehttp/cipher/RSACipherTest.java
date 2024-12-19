package com.youable.safehttp.cipher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

class RSACipherTest {
    @DisplayName("RSA encrypt/decrypt")
    @Test
    void test() throws Exception {
        // given
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        HttpCipher cipher = new RSACipher.RSACipherBuilder(keyPair.getPublic(), keyPair.getPrivate()).build();
        String plainText = "{\"key\":\"value\"}";

        // when
        String encryptedText = cipher.encrypt(plainText);
        String decryptedText = cipher.decrypt(encryptedText);

        // then
        assertNotNull(encryptedText);
        assertNotEquals(plainText, encryptedText);
        assertEquals(plainText, decryptedText);
    }
}