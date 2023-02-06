package com.candy.network.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollDatagramChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.log4j.Log4j2;

import java.net.DatagramPacket;
import java.util.concurrent.ConcurrentLinkedQueue;

@Log4j2
public class UDPServerSocket extends ChannelInboundHandlerAdapter {

    protected Bootstrap bootstrap;
    protected Channel channel;

    protected ConcurrentLinkedQueue<DatagramPacket> packets = new ConcurrentLinkedQueue<>();

    public UDPServerSocket(String ip, int port) {
        this.bootstrap = new Bootstrap();

        Class<? extends Channel> channel;
        EventLoopGroup group;
        if(Epoll.isAvailable()) {
            channel = EpollDatagramChannel.class;
            group = new EpollEventLoopGroup();
            log.info("Epoll status is true");
        }
        else {
            channel = NioDatagramChannel.class;
            group = new NioEventLoopGroup();
            log.info("Epoll status is false");
        }

        this.bootstrap.channel(channel);
        this.bootstrap.group(group);
        this.bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        this.bootstrap.handler(this);

        try {
            this.bootstrap.bind(ip, port).sync().channel();
        }
        catch (Exception e) {
            log.error("Could not bind to " + ip + ":" + port + ". Maybe server is already running on that port.");
            System.exit(1);
        }

        log.info("Successfully bound to " + ip + ":" + port + "!");
    }

    public DatagramPacket readPacket() {
        return this.packets.poll();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof DatagramPacket) {
            this.packets.add((DatagramPacket) msg);
        }
    }
}