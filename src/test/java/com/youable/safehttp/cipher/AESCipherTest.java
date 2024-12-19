package com.youable.safehttp.cipher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

public class AESCipherTest {
    @DisplayName("AES encrypt/decrypt")
    @Test
    void testEncryptAndDecrypt() throws Exception {
        // given
        byte[] key = new byte[32];
        new SecureRandom().nextBytes(key);
        String privateKey = Base64.getEncoder().encodeToString(key);

        HttpCipher cipher = new AESCipher.AESCipherBuilder(privateKey).build();

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