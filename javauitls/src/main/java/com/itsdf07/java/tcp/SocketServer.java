package com.itsdf07.java.tcp;

import java.net.BindException;

/**
 * @Description:
 * @Author itsdf07
 * @Time 2018/7/21/021 10:03
 */

public class SocketServer {
    //监听端口7777
    private static final int PORT = 7777;

    public static void main(String[] args) {
        System.out.println("Starting Server:port=" + PORT);
        ServerSocketService mServerScoketService = new ServerSocketService();
        try {
            boolean isStart = mServerScoketService.onServerStart(1, PORT);
            if (isStart){
                System.out.println("Starting Server successfully:port=" + PORT);
            } else {
                System.out.println("Starting Server failed:port=" + PORT);
            }
        } catch (BindException e) {
            System.out.println("Starting Server failed::port=" + PORT);
        }
//        try {
//            System.out.println("等待客户端");
//            ServerSocket serverSocket = new ServerSocket(PORT);
//            Socket clientSocket = serverSocket.accept();
//            System.out.println("客户端上线:"  + clientSocket.getInetAddress().getHostAddress());
//            while (true) {
//                //循环监听客户端请求
//                try {
//                    //获取输入流
//                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                    //获取从客户端发来的信息
//                    String msg = in.readLine();
//                    System.out.println("客户端消息:"+msg);
//                } catch (IOException e) {
//                    System.out.println("读写错误");
//                    e.printStackTrace();
//                } finally {
//                    serverSocket.close();
//                    clientSocket.close();
//                    System.out.println("服务器关闭");
//                    break;
//                }
//            }
//
//        } catch (Exception e) {
//            System.out.println("端口被占用");
//            e.printStackTrace();
//        }
    }
}
