package com.tokev.demo.interceptordemospring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InterceptorDemoSpringApplicationTests {
    @Value(value="${local.server.port}")
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Invoke API with non string response. As it is non string, the new custom message converter will be used. It will find out the content lenght, so response will be written before invoking after completion")
    void nonStringContentTypeReturnsImmediatelyWithCustomConverter() throws InterruptedException {
        long timeBeforeInvoke = System.currentTimeMillis();
        this.restTemplate.getForObject("http://localhost:"+port+"/non-string-response", String.class);
        long timeAfterInvoke = System.currentTimeMillis();
        Assertions.assertFalse((timeAfterInvoke - timeBeforeInvoke) >= 3000);
        Thread.sleep(5000);
    }

    @Test
    @DisplayName("Invoke API with string response. As it is string, the response gets committed immediately with or without wait in main Thread for audit event")
    void stringContentTypeReturnsImmediatelyWithMainThreadNoWait() throws InterruptedException {
        long timeBeforeInvoke = System.currentTimeMillis();
        this.restTemplate.getForObject("http://localhost:"+port+"/string-response", String.class);
        long timeAfterInvoke = System.currentTimeMillis();
        Assertions.assertTrue((timeAfterInvoke - timeBeforeInvoke) < 3000);
        Thread.sleep(5000);
    }
}
