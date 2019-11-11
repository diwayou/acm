package com.diwayou.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class UdpClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        try (DatagramSocket client = new DatagramSocket()) {
            String message = "你好啊!";
            byte[] buf = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName("255.255.255.255"), 12345);

            while (true) {
                TimeUnit.SECONDS.sleep(2);

                client.send(packet);
            }
        }
    }
}
