package com.candy;


import com.candy.utils.SweetUtils;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.atomic.AtomicBoolean;

@Log4j2
public class SweetProxy {

    private final AtomicBoolean running = new AtomicBoolean(true);

    public static void main(String[] args) {
        SweetProxy proxy = new SweetProxy();
        log.info("Starting proxy on version: " + SweetUtils.getVersion() + " (" + SweetUtils.getMinecraftProtocol() + ")");
        proxy.enableProxy();
    }

    public void enableProxy() {
        running.set(true);
        if (running.get()) {
            log.info("Proxy enabled on 0.0.0.0");
        }
    }
}