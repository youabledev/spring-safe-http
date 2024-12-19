package com.youable.safehttp.cipher;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class RSACipher implements HttpCipher {
    private PublicKey publicKey;
    private PrivateKey privateKey;

    @Override
    public String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    @Override
    public String decrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(data));
        return new String(decryptedBytes);
    }

    private RSACipher(RSACipherBuilder builder) {
        this.publicKey = builder.publicKey;
        this.privateKey = builder.privateKey;
    }

    public static class RSACipherBuilder {
        private PublicKey publicKey;
        private PrivateKey privateKey;

        public RSACipherBuilder(PublicKey publicKey, PrivateKey privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public RSACipher build() {
            return new RSACipher(this);
        }
    }
}