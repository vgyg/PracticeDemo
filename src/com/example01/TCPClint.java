package com.example01;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP客户端
 * 1、创建客户端socket对象，指定要连接的服务器地址和端口
 * 2、获取服务器端反馈的信息
 * 3、关闭流资源
 */
public class TCPClint {

    public static void main(String[] arg) throws IOException {
        Socket socket = new Socket("192.168.31.117", 8888);

        OutputStream out = socket.getOutputStream();
        FileInputStream fis = new FileInputStream("E:\\1.jpg");
        int len = -1;
        byte[] bytes = new byte[1024];
        while ((len = fis.read(bytes)) != -1) {
            out.write(bytes, 0, len);
        }
        socket.shutdownOutput();
        InputStream in = socket.getInputStream();
        len = in.read(bytes);
        System.out.println(new String(bytes, 0, len));
        fis.close();
        socket.close();
    }
}
