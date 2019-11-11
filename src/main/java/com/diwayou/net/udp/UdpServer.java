package com.diwayou.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class UdpServer {

    public static void main(String[] args) throws SocketException {
        try (DatagramSocket server = new DatagramSocket(12345)) {
            server.setSoTimeout(3000);

            byte[] buf = new byte[8192];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                try {
                    server.receive(packet);

                    process(packet);
                } catch (IOException e) {
                    System.out.println("超时?");
                }
            }
        }
    }

    private static void process(DatagramPacket packet) {
        System.out.println(String.format("address = %s:%d, len = %d, data = %s",
                packet.getAddress(),
                packet.getPort(),
                packet.getLength(),
                new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8)));
    }
}
