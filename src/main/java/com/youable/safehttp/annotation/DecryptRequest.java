package com.youable.safehttp.annotation;

import java.lang.annotation.*;

/**
 * <p>Used at the method level. Decrypts the ciphertext
 * of a parameter defined as a RequestBody or PathVariable.
 * @author youabledev
 * @since 0.0.1
 * @see EncryptResponse
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DecryptRequest {
}
