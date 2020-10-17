package com.github.marcoral.simplenettyclient.example.packet.in;


import com.github.marcoral.nettyencodingutil.BytesDecoder;
import com.github.marcoral.simplenettyclient.api.packet.PacketDeserializer;

public class LoginErrorResponsePacketDeserializer implements PacketDeserializer {
    @Override
    public LoginErrorResponsePacket deserialize(BytesDecoder bytesDecoder) {
        CharSequence cause = bytesDecoder.readCharSequence();
        return new LoginErrorResponsePacket(cause);
    }
}