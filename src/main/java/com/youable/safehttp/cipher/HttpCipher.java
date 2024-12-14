package com.youable.safehttp.cipher;

/**
 * <p>use the httpCipher interface to implement the encryption/decryption algorithm.
 * The object you implement must be registered as a bean.
 * @author youabledev
 * @since 0.0.1
 * @see com.youable.safehttp.advice.DecryptRequestBodyAdvice
 * @see com.youable.safehttp.aop.EncryptResponseAspect
 */
public interface HttpCipher {
    String encrypt(String data) throws Exception;

    String decrypt(String data) throws Exception;
}
