package com.candy.network.server;

import lombok.Getter;

public class SweetServer extends Thread {

    @Getter
    private final String ip;
    @Getter
    private final int port;

    public SweetServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("FakeServer thread");

        UDPServerSocket socket = new UDPServerSocket(ip, port);
    }
}