package com.github.marcoral.simplenettyclient.example.packet.in;

import com.github.marcoral.nettyencodingutil.BytesDecoder;
import com.github.marcoral.simplenettyclient.api.packet.PacketDeserializer;
import com.github.marcoral.simplenettyclient.api.packet.PacketIn;

public class PongPacketDeserializer implements PacketDeserializer {
    @Override
    public PacketIn deserialize(BytesDecoder bytesDecoder) {
        return new PongPacket();
    }
}