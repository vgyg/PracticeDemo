package com.example01;

import java.io.IOException;
import java.lang.management.BufferPoolMXBean;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by z on 2018/11/17.
 */
public class UDPReceive {
    public static void main(String[] arg) throws IOException {
        DatagramSocket socket = new DatagramSocket(12306);
        byte[] buff = new byte[1024];
        System.out.println("-----------------");
        DatagramPacket packet = new DatagramPacket(buff, 1024);
        socket.receive(packet);
        System.out.println("getAddress: " + packet.getAddress().getHostName());
        System.out.println("packet.getData(): " +  packet.getData().toString());
        System.out.println("getLength: " + packet.getLength());
        socket.close();
        System.out.println("-----------------");
    }
}
