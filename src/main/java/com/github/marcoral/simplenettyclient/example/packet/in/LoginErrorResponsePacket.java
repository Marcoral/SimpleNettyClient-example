package com.github.marcoral.simplenettyclient.example.packet.in;


import com.github.marcoral.simplenettyclient.api.packet.PacketIn;
import io.netty.channel.ChannelHandlerContext;

public class LoginErrorResponsePacket implements PacketIn {
    private final CharSequence cause;
    public LoginErrorResponsePacket(CharSequence cause) {
        this.cause = cause;
    }

    @Override
    public void handle(ChannelHandlerContext channelHandlerContext) {
        System.out.println("Failed to login: " + cause);
    }
}