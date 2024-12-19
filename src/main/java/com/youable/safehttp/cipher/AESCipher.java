package com.youable.safehttp.cipher;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESCipher implements HttpCipher {
    private final String privateKey;
    private final String ALGORITHM = "AES";
    private final int BEGIN_INDEX = 0;
    private final int END_INDEX = 16;
    private final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    @Override
    public String encrypt(String data) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(privateKey), ALGORITHM);
        IvParameterSpec IV = new IvParameterSpec(privateKey.substring(BEGIN_INDEX, END_INDEX).getBytes());
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, IV);
        byte[] encryptByte = cipher.doFinal(data.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptByte);
    }

    @Override
    public String decrypt(String data) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(privateKey), ALGORITHM);
        IvParameterSpec IV = new IvParameterSpec(privateKey.substring(BEGIN_INDEX, END_INDEX).getBytes());
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IV);
        byte[] decodeByte = cipher.doFinal(Base64.getDecoder().decode(data));
        return new String(decodeByte);
    }

    private AESCipher(AESCipherBuilder builder) {
        this.privateKey = builder.privateKey;
    }

    public static class AESCipherBuilder {
        private String privateKey;

        public AESCipherBuilder(String PRIVATE_KEY) {
            this.privateKey = PRIVATE_KEY;
        }

        public AESCipher build() {
            return new AESCipher(this);
        }
    }
}
