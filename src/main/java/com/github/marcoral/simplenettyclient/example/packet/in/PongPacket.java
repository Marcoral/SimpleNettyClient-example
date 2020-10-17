package com.github.marcoral.simplenettyclient.example.packet.in;

import com.github.marcoral.simplenettyclient.api.packet.PacketIn;
import io.netty.channel.ChannelHandlerContext;

public class PongPacket implements PacketIn {
    @Override
    public void handle(ChannelHandlerContext channelHandlerContext) {
        System.out.println("Server responded to ping!");
    }
}