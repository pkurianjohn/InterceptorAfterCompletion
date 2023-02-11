package com.tokev.demo.interceptordemospring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {
    @GetMapping(value = "/non-string-response", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimplePojo> nonStringResponse(){
        return new ResponseEntity<>(getSimplePojo(), HttpStatus.OK);
    }

    @GetMapping(value = "/string-response", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> stringResponse() throws JsonProcessingException {
        return new ResponseEntity<>((new ObjectMapper()).writeValueAsString(getSimplePojo()), HttpStatus.OK);
    }

    private SimplePojo getSimplePojo(){
        return new SimplePojo("John Doe", 31);
    }
}
