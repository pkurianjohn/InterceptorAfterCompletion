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
    @DisplayName("Invoke API with string response and no wait. As it is string, the response gets committed immediately with or without wait in main Thread for audit event")
    void stringContentTypeReturnsImmediatelyWithMainThreadNoWait() {
        long timeBeforeInvoke = System.currentTimeMillis();
        this.restTemplate.getForObject("http://localhost:"+port+"/string-response", String.class);
        long timeAfterInvoke = System.currentTimeMillis();
        Assertions.assertTrue((timeAfterInvoke - timeBeforeInvoke) < 3000);
    }

    @Test
    @DisplayName("Invoke API with non string response and no wait. As it is non string, the response gets committed after audit event, but main thread does not wait for audit event thread, so response gets committed immediately")
    void nonStringContentTypeReturnsAfterAuditEventWithMainThreadNoWait() {
        long timeBeforeInvoke = System.currentTimeMillis();
        this.restTemplate.getForObject("http://localhost:"+port+"/non-string-response?thread", String.class);
        long timeAfterInvoke = System.currentTimeMillis();
        Assertions.assertTrue((timeAfterInvoke - timeBeforeInvoke) < 3000);
    }

    @Test
    @DisplayName("Invoke API with non string response and wait. As it is non string, the response gets committed after audit event, as main thread wait for audit event thread, response gets committed only after audit event is completed")
    void nonStringContentTypeReturnsAfterAuditEventWithMainThreadWait() {
        long timeBeforeInvoke = System.currentTimeMillis();
        this.restTemplate.getForObject("http://localhost:"+port+"/non-string-response", String.class);
        long timeAfterInvoke = System.currentTimeMillis();
        Assertions.assertTrue((timeAfterInvoke - timeBeforeInvoke) >= 3000);
    }
}
