package com.candy.network;

import com.candy.SweetProxy;
import com.candy.network.server.SweetServer;
import lombok.Getter;

public class Network {

    @Getter
    private final SweetProxy proxy;

    private final SweetServer sweetServer;

    public Network(SweetProxy proxy) {
        this.proxy = proxy;

        this.sweetServer = new SweetServer(SweetProxy.getAddress(), this.getProxy().getPort());
        this.sweetServer.start();
    }

    public void tick() {
    }

}