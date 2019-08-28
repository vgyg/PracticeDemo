package com.example01;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by z on 2018/11/17.
 */
public class TCPServer {
    public static void main(String[] arg) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket socket = serverSocket.accept();
        InputStream in = socket.getInputStream();
        File upload = new File("e:\\a\\r.jpg");
        if(!upload.exists()){
            upload.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(new File(""));
        byte[] data = new byte[1024];
        int len = -1;
        while ((len=in.read(data))!=-1){
            fos.write(data,0,len);
        }
        socket.getOutputStream().write("图片上传成功".getBytes());
        fos.close();
        socket.close();
        serverSocket.close();
    }
}
