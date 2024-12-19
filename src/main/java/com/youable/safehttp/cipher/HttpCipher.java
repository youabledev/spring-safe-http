package com.youable.safehttp.cipher;

/**
 * <p>use the httpCipher interface to implement the encryption/decryption algorithm.
 * The object you implement must be registered as a bean.
 * <pre>{@code
 * @Configuration
 * public class CipherConfig {
 *     @Bean
 *     @Primary
 *     public HttpCipher aesCipher() {
 *         return new AESCipher.AESCipherBuilder("3mlng8uGI+k2CGVDKR/EAwI+uN5pzoITSkREjzxZc7M=").build();
 *     }
 * }</pre>
 * <p>Create Configuration to enroll HttpCipher</p>
 * @author youabledev
 * @since 0.0.1
 * @see com.youable.safehttp.advice.DecryptRequestBodyAdvice
 * @see com.youable.safehttp.aop.EncryptResponseAspect
 */
public interface HttpCipher {
    String encrypt(String data) throws Exception;

    String decrypt(String data) throws Exception;
}
