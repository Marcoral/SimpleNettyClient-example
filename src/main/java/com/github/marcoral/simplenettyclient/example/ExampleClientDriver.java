package com.github.marcoral.simplenettyclient.example;

import com.github.marcoral.simplenettyclient.example.api.ExampleClient;
import com.github.marcoral.simplenettyclient.example.api.packet.out.LoginRequestPacket;
import com.github.marcoral.simplenettyclient.example.api.packet.out.PingPacket;
import com.github.marcoral.simplenettyclient.example.api.packet.out.SomeIntDataPacket;
import com.github.marcoral.simplenettyserver.example.api.ExampleServerConstants;

import java.util.Scanner;

public class ExampleClientDriver {
    public static void main(String[] args) throws Exception {
        ExampleClient c = launch(args);
        processInput(c);
        c.getCloseFuture().get();
    }

    public static ExampleClient launch(String[] args) throws Exception {
        String host = "localhost";
        int port = ExampleServerConstants.DEFAULT_PORT;
        if(args.length > 0)
            host = args[0];

        if(args.length > 1)
            port = Integer.parseInt(args[1]);

        return ExampleClient.setupAt(host, port);
    }

    private static void processInput(ExampleClient c) {
        Thread t = new Thread(() -> {
            try(Scanner sc = new Scanner(System.in)) {
                for(String command = sc.nextLine(); ; command = sc.nextLine()) {
                    command = command.toLowerCase();
                    switch (command) {
                        case "ping":
                            c.sendPacket(new PingPacket());
                            break;
                        case "login":
                            System.out.println("Enter username:");
                            String requestedNickname = sc.nextLine();
                            c.sendPacket(new LoginRequestPacket(requestedNickname));
                            break;
                        case "data":
                            System.out.println("Enter integer to send:");
                            int data = Integer.parseInt(sc.nextLine());
                            c.sendPacket(new SomeIntDataPacket(data));
                            break;
                        case "exit":
                            return;
                        default:
                            System.out.println("Unknown command.");
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.setDaemon(true);
        t.start();
    }
}
