package com.tokev.demo.interceptordemospring;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuditEvent extends Thread{
    @SneakyThrows
    @Override
    public void run() {
        log.info("Audit event started");
        Thread.sleep(3000);
        log.info("Audit event completed");
    }
}
