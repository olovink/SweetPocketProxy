package com.candy;


import com.candy.network.Network;
import com.candy.server.ServerManager;
import com.candy.utils.Config;
import com.candy.utils.SweetUtils;
import com.candy.utils.Utils;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


@Log4j2
@Getter
public class SweetProxy {

    @Getter
    private String motd;
    @Getter
    private static String address;
    @Getter
    private int port;

    @Getter
    private ServerManager serverManager;
    @Getter
    private Network network;

    public static final long START_TIME = System.currentTimeMillis();
    private final AtomicBoolean running = new AtomicBoolean(true);

    public static void main(String[] args) {
        SweetProxy proxy = new SweetProxy();
        log.info("Starting proxy on version: " + SweetUtils.getVersion() + " (" + SweetUtils.getMinecraftProtocol() + ")");
        proxy.enableProxy();
    }

    public void enableProxy() {
        running.set(true);

        this.loadProperties();
        this.loadConfiguration();

        log.info("Proxy enabled on 0.0.0.0");
        log.info("Server startup took " + (double) (System.currentTimeMillis() - START_TIME) / 1000 + " seconds");

        this.serverManager = new ServerManager(this);
        this.network = new Network(this);
    }

    private void loadProperties() {
        log.info("Loading proxy.properties...");

        File file = new File(SweetUtils.DATA_PATH + "proxy.properties");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                log.error(exception);
            }

            Map<Object, Object> defaultProperties = new HashMap<Object, Object>();
            defaultProperties.put("proxy-name", SweetUtils.SOFTWARE_NAME);
            defaultProperties.put("proxy-ip", "0.0.0.0");
            defaultProperties.put("proxy-port", 19132);

            Config config = new Config(file, Config.PROPERTIES);
            config.setAll(defaultProperties);
            config.save();
        }

        Config config = new Config(file, Config.PROPERTIES);
        this.motd = (String) config.getAll().get("proxy-name");
        this.address = (String) config.getAll().get("proxy-ip");
        this.port = Integer.parseInt((String) config.getAll().get("proxy-port"));
    }

    private void loadConfiguration() {
        log.info("Loading proxy.yml...");

        File file = new File(SweetUtils.DATA_PATH + "proxy.yml");
        if(!file.exists()) {
            Utils.saveResource("proxy.yml");
        }

        Config config = new Config(file, Config.YAML);
        System.out.println(config.getAll());
    }

    public void shutdown() {
        if (running.compareAndSet(true, false)) {
            synchronized (this) {
                this.notify();
            }
            log.info("Proxy disabled");
        }
    }

    public static String getAddress() {
        return address;
    }

}