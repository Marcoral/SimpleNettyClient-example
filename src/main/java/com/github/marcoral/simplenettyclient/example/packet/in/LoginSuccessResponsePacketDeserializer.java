package com.github.marcoral.simplenettyclient.example.packet.in;


import com.github.marcoral.nettyencodingutil.BytesDecoder;
import com.github.marcoral.simplenettyclient.api.packet.PacketDeserializer;

public class LoginSuccessResponsePacketDeserializer implements PacketDeserializer {
    @Override
    public LoginSuccessResponsePacket deserialize(BytesDecoder bytesDecoder) {
        return new LoginSuccessResponsePacket();
    }
}