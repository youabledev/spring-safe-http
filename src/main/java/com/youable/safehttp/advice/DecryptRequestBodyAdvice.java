package com.youable.safehttp.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youable.safehttp.annotation.DecryptRequest;
import com.youable.safehttp.cipher.HttpCipher;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;


@ControllerAdvice
public class DecryptRequestBodyAdvice implements RequestBodyAdvice {
    private final HttpCipher httpCipher;

    public DecryptRequestBodyAdvice(HttpCipher httpCipher) {
        this.httpCipher = httpCipher;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasMethodAnnotation(DecryptRequest.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(
            HttpInputMessage inputMessage,
            MethodParameter parameter,
            Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType
    ) throws IOException {
        String encryptedBody = new String(inputMessage.getBody().readAllBytes());
        try {
            String decryptedBody = httpCipher.decrypt(encryptedBody);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readTree(decryptedBody);
            return new DecryptedHttpInputMessage(decryptedBody, inputMessage.getHeaders());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
