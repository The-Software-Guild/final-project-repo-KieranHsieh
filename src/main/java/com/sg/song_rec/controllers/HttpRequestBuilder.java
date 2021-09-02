package com.sg.song_rec.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.song_rec.entities.application.ObjectSerializationException;
import com.sg.song_rec.util.mappers.ObjectMapperParseException;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * A utility class for building HttpRequests
 */
public class HttpRequestBuilder {
    private HttpMethod method;
    private String endpoint;
    private String body;
    private HttpHeaders headers = new HttpHeaders();
    private RestTemplate template;

    /**
     * Constructs a new HttpRequestBuilder
     * @param template The RestTemplate object to use when making requests
     */
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

    /**
     * Sets the HttpRequest method used by the request
     * @param method The method to use
     * @return The calling builder object
     */
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

    /**
     * Sets the target endpoint for the HttpRequestBuilder. This
     * property method MUST be called in order to make a valid request
     * @param endpoint The endpoint targeted by the request
     * @return The calling HttpRequestBuilder
     */
    public HttpRequestBuilder setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    /**
     * Adds a header with the specified key and value to the HttpRequestBuilder
     * @param key The key of the header entry
     * @param value The value of the header entry
     * @return The calling HttpRequestBuilder
     */
    public HttpRequestBuilder addHeader(String key, String value) {
        headers.set(key, value);
        return this;
    }

    /**
     * Gets the headers associated with the HttpRequestBuilder
     * @return The headers used by the HttpRequestBuilder
     */
    public HttpHeaders getHeaders() {
        return headers;
    }

    /**
     * Sets the expected content type of the HttpRequestBuilder.
     * Currently, the only supported content types are MediaType.APPLICATION_JSON and MediaType.APPLICATION_FORM_URLENCODED
     * @param mediaType The media type representation of the content type
     * @return The calling HttpRequestBuilder
     */
    public HttpRequestBuilder setContentType(MediaType mediaType) {
        headers.setContentType(mediaType);
        return this;
    }

    /**
     * Sets the body of the request
     * @param o The object to be serialized into the body based on the builder's content type
     * @return The calling HttpRequestBuilder
     * @throws ObjectSerializationException Thrown when the object could not be serialized
     */
    public HttpRequestBuilder setBody(Object o) throws ObjectSerializationException {
        // Process the object using the jaxon ObjectMapper
        if(Objects.equals(headers.getContentType(), MediaType.APPLICATION_JSON)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                body = mapper.writeValueAsString(o);
            }
            catch(JsonProcessingException e) {
                throw new ObjectSerializationException(e.getMessage());
            }
        }
        // Process the object using the song_rec.util ObjectMapper
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

    /**
     * Calls RestTemplate.exchange with parameters taken from the HttpRequestBuilder's properties
     * @param returnType The return type of the request
     * @param <T> The return type of the request
     * @return A ResponseEntity containing the response body in it's body
     */
    public <T> ResponseEntity<T> exchange(Class<T> returnType) {
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        return template.exchange(endpoint, method, request, returnType);
    }
}
