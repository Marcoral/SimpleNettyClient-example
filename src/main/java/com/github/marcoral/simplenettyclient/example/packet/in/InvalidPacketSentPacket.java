package com.github.marcoral.simplenettyclient.example.packet.in;

import com.github.marcoral.simplenettyclient.api.packet.PacketIn;
import io.netty.channel.ChannelHandlerContext;

public class InvalidPacketSentPacket implements PacketIn {
    private final String cause;
    public InvalidPacketSentPacket(String cause) {
        this.cause = cause;
    }

    @Override
    public void handle(ChannelHandlerContext channelHandlerContext) {
        System.out.println("From server: " + cause);
    }
}
