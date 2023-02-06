package com.candy.player;

import com.candy.SweetProxy;
import lombok.Getter;

public class Player {

    @Getter
    private final SweetProxy proxy;
    @Getter
    protected String name;

    public Player(SweetProxy proxy, String name) {
        this.proxy = proxy;
        this.name = name;
    }


    public void sendMessage(String message) {
    }
}