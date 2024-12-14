package com.youable.safehttp.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


public class DecryptedHttpInputMessage implements HttpInputMessage {
    private final InputStream body;
    private final HttpHeaders headers;

    public DecryptedHttpInputMessage(String body, HttpHeaders headers) {
        this.body = new ByteArrayInputStream(body.getBytes());
        this.headers = headers;
    }
    @Override
    public InputStream getBody() throws IOException {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }
}
