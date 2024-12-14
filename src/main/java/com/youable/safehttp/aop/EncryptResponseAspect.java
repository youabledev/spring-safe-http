package com.youable.safehttp.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youable.safehttp.annotation.EncryptResponse;
import com.youable.safehttp.cipher.HttpCipher;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * <p>Encrypts the response value of the controller method with @EncryptResponse applied.
 * if you need an encrypted response value, you must apply @EncryptResponse.
 * Use an implementation of the HttpCipher interface as the encryption algorithm.
 * Requires an implementation of th HttpCipher interface.
 * @author youabledev
 * @since 0.0.1
 * @see HttpCipher
 * @see com.youable.safehttp.annotation.EncryptResponse
 */
@Aspect
@Component
public class EncryptResponseAspect {
    private final ObjectMapper objectMapper;

    private final HttpCipher httpCipher;

    public EncryptResponseAspect(HttpCipher httpCipher, ObjectMapper objectMapper) {
        this.httpCipher = httpCipher;
        this.objectMapper = objectMapper;
    }

    @Around("@annotation(EncryptResponse)")
    public Object encryptResponse(ProceedingJoinPoint joinPoint, EncryptResponse encryptResponse) throws Throwable {
        Object result = joinPoint.proceed();

        if (result instanceof ResponseEntity<?> responseEntity) {
            Object body = responseEntity.getBody();
            String bodyString = objectMapper.writeValueAsString(body);
            String encryptedData = httpCipher.encrypt(bodyString);

            return ResponseEntity
                    .status(responseEntity.getStatusCode())
                    .headers(responseEntity.getHeaders())
                    .body(encryptedData);
        }

        return result;
    }
}
