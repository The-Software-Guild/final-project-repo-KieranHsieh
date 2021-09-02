package com.sg.song_rec.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.song_rec.entities.application.ObjectSerializationException;
import com.sg.song_rec.util.reflection.ObjectMapperParseException;
import org.springframework.http.*;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class HttpRequestBuilder {
    private HttpMethod method;
    private String endpoint;
    private String body;
    private HttpHeaders headers = new HttpHeaders();
    private RestTemplate template;

    public HttpRequestBuilder(RestTemplate template) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        this.template = template;
    }

    /**
     * Gets the method
     *
     * @return org.springframework.http.HttpMethod The method
     */
    public HttpMethod getMethod() {
        return method;
    }

    public HttpRequestBuilder setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    /**
     * Gets the endpoint
     *
     * @return java.lang.String The endpoint
     */
    public String getEndpoint() {
        return endpoint;
    }

    public HttpRequestBuilder setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public HttpRequestBuilder addHeader(String key, String value) {
        headers.set(key, value);
        return this;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public HttpRequestBuilder setContentType(MediaType mediaType) {
        headers.setContentType(mediaType);
        return this;
    }

    public HttpRequestBuilder setBody(Object o) throws ObjectSerializationException {
        if(Objects.equals(headers.getContentType(), MediaType.APPLICATION_JSON)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                body = mapper.writeValueAsString(o);
            }
            catch(JsonProcessingException e) {
                throw new ObjectSerializationException(e.getMessage());
            }
        }
        else if(Objects.equals(headers.getContentType(), MediaType.APPLICATION_FORM_URLENCODED)) {
            com.sg.song_rec.util.mappers.ObjectMapper mapper = new com.sg.song_rec.util.mappers.ObjectMapper();
            try {
                body = mapper.writeValueToString(o);
            } catch (ObjectMapperParseException e) {
                throw new ObjectSerializationException(e.getMessage());
            }
        }
        return this;
    }

    public <T> ResponseEntity<T> exchange(Class<T> returnType) {
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        return template.exchange(endpoint, method, request, returnType);
    }
}
