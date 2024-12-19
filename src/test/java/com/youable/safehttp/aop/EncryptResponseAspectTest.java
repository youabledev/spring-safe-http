package com.youable.safehttp.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youable.safehttp.cipher.HttpCipher;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EncryptResponseAspectTest {
    @DisplayName("EncryptResponseAspect should match expected Response Body")
    @Test
    void testEncryptResponse() throws Throwable {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        HttpCipher mockHttpCipher = mock(HttpCipher.class);
        EncryptResponseAspect aspect = new EncryptResponseAspect(mockHttpCipher, objectMapper);

        ProceedingJoinPoint mockJoinPoint = mock(ProceedingJoinPoint.class);

        Person response = new Person("hongkildong", 12);
        String responseJson = objectMapper.writeValueAsString(response);
        String encryptedData = "o3Y8Yiuffa+LNkTftpgSGmBasze4poDDbuQ3GVIwmiY=";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        ResponseEntity<Person> mockResponseEntity =
                new ResponseEntity<>(response, headers, HttpStatus.OK);

        // when
        when(mockJoinPoint.proceed()).thenReturn(mockResponseEntity);
        when(mockHttpCipher.encrypt(responseJson)).thenReturn(encryptedData);

        // Act
        Object result = aspect.encryptResponse(mockJoinPoint, null);

        // then
        assertTrue(result instanceof ResponseEntity<?>);
        ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
        assertEquals(encryptedData, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(headers, responseEntity.getHeaders());

        verify(mockJoinPoint, times(1)).proceed();
        verify(mockHttpCipher, times(1)).encrypt(responseJson);
    }

    private record Person(String name, Integer age) {}
}