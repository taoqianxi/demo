package cn.tao.socket.bio;


import java.io.IOException;
import java.net.*;

public class BioServiceSocker {

    public static void main(String [] args){
        try {
            ServerSocket serverSocket=new ServerSocket(8888);
            Socket socket=null;
            System.out.println("***服务器即将启动，等待客户端的连接***");
            while(true){
                //调用accept()方法开始监听，等待客户端的连接
                socket=serverSocket.accept();

                //创建一个新的线程
                BioServerThread serverThread=new BioServerThread(socket);
                //启动线程
                new Thread(serverThread).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
