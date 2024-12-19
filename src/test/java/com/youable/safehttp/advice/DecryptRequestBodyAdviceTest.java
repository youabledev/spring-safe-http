package com.youable.safehttp.advice;

import com.youable.safehttp.annotation.DecryptRequest;
import com.youable.safehttp.cipher.HttpCipher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DecryptRequestBodyAdviceTest {
    @DisplayName("DecryptRequestBodyAdvice should support methods annotated with @DecryptRequest")
    @Test
    void testDecryptRequestAnnotation() {
        // given
        HttpCipher mockHttpCiper = mock(HttpCipher.class);
        DecryptRequestBodyAdvice advice = new DecryptRequestBodyAdvice(mockHttpCiper);
        MethodParameter methodParameter = mock(MethodParameter.class);

        // when
        when(methodParameter.hasMethodAnnotation(DecryptRequest.class)).thenReturn(true);
        boolean supports = advice.supports(methodParameter, String.class, null);

        // then
        assert(supports);
    }

    @DisplayName("Decrypted request body should match expected JSON")
    @Test
    void testBeforeBodyRead() throws Exception {
        // given
        String encryptedBody = "SXuMtzsL8X05TCRuyOHEGA==";
        String decryptedBody = "{\"key\":\"value\"}";
        HttpCipher mockHttpCipher = mock(HttpCipher.class);
        when(mockHttpCipher.decrypt(encryptedBody)).thenReturn(decryptedBody);

        DecryptRequestBodyAdvice advice = new DecryptRequestBodyAdvice(mockHttpCipher);

        HttpHeaders headers = new HttpHeaders();
        DecryptedHttpInputMessage inputMessage = new DecryptedHttpInputMessage(encryptedBody, headers);

        MethodParameter parameter = mock(MethodParameter.class);

        // when
        HttpInputMessage result = advice.beforeBodyRead(inputMessage, parameter, String.class, null);

        // then
        assertNotNull(result);
        assertEquals(decryptedBody, new String(result.getBody().readAllBytes()));
    }

    @DisplayName("Decryption failure should throw RuntimeException")
    @Test
    void testDecryptFailure() throws Exception {
        // given
        String encryptedBody = "SXuMtzsL8X05TCRuyOHEGA==";
        HttpCipher mockHttpCipher = mock(HttpCipher.class);
        when(mockHttpCipher.decrypt(encryptedBody)).thenThrow(new RuntimeException("Decryption failed"));

        DecryptRequestBodyAdvice advice = new DecryptRequestBodyAdvice(mockHttpCipher);

        HttpHeaders headers = new HttpHeaders();
        DecryptedHttpInputMessage inputMessage = new DecryptedHttpInputMessage(encryptedBody, headers);

        MethodParameter parameter = mock(MethodParameter.class);

        // when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            advice.beforeBodyRead(inputMessage, parameter, String.class, null);
        });

        assertEquals("Decryption failed", exception.getCause().getMessage());
    }
}