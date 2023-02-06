package com.candy;


import com.candy.utils.SweetUtils;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.atomic.AtomicBoolean;



@Log4j2
@Getter
public class SweetProxy {

    public static final long START_TIME = System.currentTimeMillis();
    private final AtomicBoolean running = new AtomicBoolean(true);

    public static void main(String[] args) {
        SweetProxy proxy = new SweetProxy();
        log.info("Starting proxy on version: " + SweetUtils.getVersion() + " (" + SweetUtils.getMinecraftProtocol() + ")");
        proxy.enableProxy();
    }

    public void enableProxy() {
        running.set(true);
        log.info("Proxy enabled on 0.0.0.0");
        log.info("Server startup took " + (double) (System.currentTimeMillis() - START_TIME) / 1000 + " seconds");

        run();
    }

    private void run() {
        while (running.get()) {
            try {
                synchronized (this) {
                    this.wait();
                }
            } catch (InterruptedException exception) {
                // ignore
            }

        }

    }
    public void shutdown() {
        if (running.compareAndSet(true, false)) {
            synchronized (this) {
                this.notify();
            }
        }
    }
}