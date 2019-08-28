package com.example01;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by z on 2018/11/17.
 */
public class UDPSend {
    //创建datagramsocket对象
    //创建datagrampocket对象，并封装数据
    //发送数据
    //释放数据流
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        byte[] buff = "hello".getBytes();
        DatagramPacket packet = new DatagramPacket(buff, buff.length, InetAddress.getByName("192.168.31.23"), 12306);
        socket.send(packet);
        socket.close();
    }
}
