package com.github.marcoral.simplenettyclient.example.api;

import com.github.marcoral.simplenettyclient.api.Client;
import com.github.marcoral.simplenettyclient.api.ClientCreator;
import com.github.marcoral.simplenettyclient.api.builder.ClientBuilder;
import com.github.marcoral.simplenettyclient.api.event.EventManager;
import com.github.marcoral.simplenettyclient.api.event.handlers.ConnectionEstablishedEventHandler;
import com.github.marcoral.simplenettyclient.api.event.handlers.ConnectionLostEventHandler;
import com.github.marcoral.simplenettyclient.api.packet.PacketDeserializer;
import com.github.marcoral.simplenettyclient.api.packet.PacketOut;
import com.github.marcoral.simplenettyclient.api.packet.PacketSerializer;
import com.github.marcoral.simplenettyclient.example.packet.in.InvalidPacketSentPacket;
import com.github.marcoral.simplenettyclient.example.packet.in.LoginErrorResponsePacketDeserializer;
import com.github.marcoral.simplenettyclient.example.packet.in.LoginSuccessResponsePacketDeserializer;
import com.github.marcoral.simplenettyclient.example.packet.in.PongPacketDeserializer;
import com.github.marcoral.simplenettyclient.example.packet.out.LoginRequestPacketSerializer;
import com.github.marcoral.simplenettyclient.example.packet.out.PingPacketSerializer;
import com.github.marcoral.simplenettyclient.example.packet.out.SomeIntDataPacketSerializer;
import com.github.marcoral.simplenettyserver.example.api.packet.ToClientPacket;
import com.github.marcoral.simplenettyserver.example.api.packet.ToServerPacket;
import io.netty.channel.ChannelFuture;
import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;

import java.util.concurrent.Future;

public class ExampleClient {
    private final Client client;
    private ExampleClient(Client client) {
        this.client = client;
    }

    public static ExampleClient setupAt(String host, int port) throws Exception {
        ClientBuilder builder = prepareBuilder();
        Client client = builder.connect(host, port).get();
        return new ExampleClient(client);
    }

    private static ClientBuilder prepareBuilder() {
        final ClientBuilder builder = ClientCreator.createClient()
                .adjustEventManager(ExampleClient::adjustEventManager);
        addPacketSerializers(builder);
        addPacketDeserializers(builder);
        builder.setOnInvalidPacketSent(InvalidPacketSentPacket::new);
        return builder;
    }

    private static void adjustEventManager(EventManager eventManager) {
        eventManager.addEventHandler((ConnectionEstablishedEventHandler) e -> System.out.println("Successfully connected to the server: " + e.getChannelHandlerContext().channel().remoteAddress()));
        eventManager.addEventHandler((ConnectionLostEventHandler) e -> System.out.println("Lost connection with the server: " + e.getChannelHandlerContext().channel().remoteAddress()));
    }

    private static void addPacketSerializers(ClientBuilder builder) {
        IntObjectMap<PacketSerializer<?>> serializers = new IntObjectHashMap<>();
        serializers.put(ToServerPacket.PING.getID(), new PingPacketSerializer());
        serializers.put(ToServerPacket.SIMPLE_DATA_PACKET.getID(), new SomeIntDataPacketSerializer());
        serializers.put(ToServerPacket.LOGIN_REQUEST.getID(), new LoginRequestPacketSerializer());

        if(serializers.size() != ToServerPacket.values().length)
            throw new IllegalStateException("Server expects %d types of packets, but there is %d serializers!");
        serializers.forEach(builder::addPacketSerializer);
    }

    private static void addPacketDeserializers(ClientBuilder builder) {
        IntObjectMap<PacketDeserializer> deserializers = new IntObjectHashMap<>();
        deserializers.put(ToClientPacket.PONG.getID(), new PongPacketDeserializer());
        deserializers.put(ToClientPacket.LOGIN_RESPONSE_ERROR.getID(), new LoginErrorResponsePacketDeserializer());
        deserializers.put(ToClientPacket.LOGIN_RESPONSE_SUCCESS.getID(), new LoginSuccessResponsePacketDeserializer());

        if(deserializers.size() != ToClientPacket.values().length)
            throw new IllegalStateException("Server sends %d types of packets, but there is %d deserializers!");
        deserializers.forEach(builder::addPacketDeserializer);
    }

    public void shutdown() {
        client.shutdown();
    }

    public Future<?> getCloseFuture() {
        return client.getCloseFuture();
    }

    public ChannelFuture sendPacket(PacketOut packet) throws Exception {
        return client.sendPacket(packet);
    }

    public EventManager getEventManager() {
        return client.getEventManager();
    }
}