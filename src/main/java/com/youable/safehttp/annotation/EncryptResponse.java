package com.youable.safehttp.annotation;

import java.lang.annotation.*;

/**
 * <p>Used at the method level. Encrypts the entire
 * ResponseBody in Json format.
 * @author youabledev
 * @since 0.0.1
 * @see DecryptRequest
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptResponse {
}
