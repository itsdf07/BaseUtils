package com.itsdf07.tcp;

import com.itsdf07.alog.ALog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description ：封装局域网内部无线设备通讯连接：Socket
 * @Author itsdf07
 * @Time 2018/6/3
 */
public class SocketClientService {
    private static SocketClientService instance;

    private Socket mSocket;
    private PrintWriter mPrintWriter;
    private BufferedReader mBfreader;
    private MessageThread mMessageThread;// 负责接收消息的线程
    private Map<String, User> mOnLineUsers = new HashMap<String, User>();// 所有在线用户

    private MsgCallback msgCallback;

    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    /**
     * 消息回调
     */
    public interface MsgCallback {
        void onResponseResult(String msg);
    }

    public static SocketClientService getInstance() {
        if (null == instance) {
            instance = new SocketClientService();
        }
        return instance;
    }

    public SocketClientService init() {
        return instance;
    }

    public SocketClientService setMsgCallback(MsgCallback callback) {
        msgCallback = callback;
        return instance;
    }

    /**
     * 连接服务器
     *
     * @param host 地址(IP)
     * @param port 端口
     */
    public boolean connectServer(String host, int port) {
        // 连接服务器
        try {
            mSocket = new Socket(host, port);// 根据端口号和服务器ip建立连接
            ALog.d("socket status:%s,input status:%s,output status:%s", mSocket.isConnected(), mSocket.isInputShutdown(), mSocket.isOutputShutdown());
            if (mSocket.isConnected()) {
                ALog.i("已成功连接目标地址->%s:%s", host, port);
            } else {
                ALog.e("目标地址连接失败->%s:%s", host, port);
            }
            mPrintWriter = new PrintWriter(mSocket.getOutputStream());
            mBfreader = new BufferedReader(new InputStreamReader(mSocket.getInputStream(), "GBK"));
            // 发送客户端用户基本信息(用户名和ip地址)
//            sendMessage(name + "@" + mSocket.getLocalAddress().toString());
            // 开启接收消息的线程
            mMessageThread = new MessageThread(mBfreader);
            mMessageThread.start();
            return true;
        } catch (Exception e) {
            ALog.e("Exception:host=%s,port=%s", host, port);
            return false;
        }
    }

    /**
     * 发送消息
     *
     * @param message
     */
    public void sendMessage(String message) {
        ALog.d("send:%s", message);
        if (null == mPrintWriter) {
            return;
        }
        mPrintWriter.println(message);
        mPrintWriter.flush();
    }

    /**
     * 客户端主动关闭连接
     */
    @SuppressWarnings("deprecation")
    public synchronized boolean closeConnection() {
        try {
            sendMessage("CLOSE");// 发送断开连接命令给服务器
            mMessageThread.stop();// 停止接受消息线程
            // 释放资源
            if (mBfreader != null) {
                mBfreader.close();
            }
            if (mPrintWriter != null) {
                mPrintWriter.close();
            }
            if (mSocket != null) {
                mSocket.close();
            }
            return true;
        } catch (IOException e) {
            ALog.e("Exception:%s", e.toString());
            return false;
        }
    }

    // 不断接收消息的线程
    class MessageThread extends Thread {
        private BufferedReader reader;

        // 接收消息线程的构造方法
        public MessageThread(BufferedReader reader) {
            this.reader = reader;
        }

        // 被动的关闭连接
        public synchronized void closeCon() throws Exception {
            // 被动的关闭连接释放资源
            if (reader != null) {
                reader.close();
            }
            if (mPrintWriter != null) {
                mPrintWriter.close();
            }
            if (mSocket != null) {
                mSocket.close();
            }
        }

        @Override
        public void run() {
            String message = "";
            ALog.i("已经准备好接收服务器信息了:%s", message);
            while (true) {
                try {
                    message = reader.readLine();
                    ALog.i("msg from service:%s", message);
                    StringTokenizer stringTokenizer = new StringTokenizer(message, "/@");
                    String command = stringTokenizer.nextToken();// 命令
                    ALog.i("service command:%s", command);
                    if (command.equals("CLOSE"))// 服务器已关闭命令
                    {
                        if (msgCallback != null) {
                            msgCallback.onResponseResult("服务器已关闭");
                        }
                        closeCon();// 被动的关闭连接
                        return;// 结束线程
                    } else if (command.equals("ADD")) {// 有用户上线更新在线列表
                        String username = "";
                        String userIp = "";
                        if ((username = stringTokenizer.nextToken()) != null
                                && (userIp = stringTokenizer.nextToken()) != null) {
                            User user = new User(username, userIp);
                            mOnLineUsers.put(username, user);
                        }
                        if (msgCallback != null) {
                            msgCallback.onResponseResult("username已上线");
                        }
                    } else if (command.equals("DELETE")) {// 有用户下线更新在线列表
                        String username = stringTokenizer.nextToken();
                        User user = mOnLineUsers.get(username);
                        if (msgCallback != null) {
                            msgCallback.onResponseResult("username已下线");
                        }
                        mOnLineUsers.remove(user);
                    } else if (command.equals("MAX")) {// 人数已达上限
                        if (msgCallback != null) {
                            msgCallback.onResponseResult("人数已达上限");
                        }
                        closeCon();// 被动的关闭连接
                        return;// 结束线程
                    } else {// 普通消息
                        if (msgCallback != null) {
                            msgCallback.onResponseResult(message);
                        }
                    }
                } catch (IOException e) {
                    ALog.e("Exception:%s", e.toString());
                } catch (Exception e) {
                    ALog.e("Exception:%s", e.toString());
                }
            }
        }
    }


    /**
     * 默认服务器地址：IP(192.168.2.152)
     *
     * @return
     */
    public String defaultHost() {
        return "192.168.2.152";
    }

    /**
     * 默认服务器端口号:7777
     *
     * @return
     */
    public int defaultPort() {
        return 7777;
    }

    public void put2CachedThreadPool(Runnable runnable) {
        if (cachedThreadPool == null) {
            cachedThreadPool = Executors.newCachedThreadPool();
        }
        cachedThreadPool.execute(runnable);
    }

    /**
     * Created by dengfu.su on 2016/12/3.
     */
    //用户信息类
    public class User {
        private String name;
        private String ip;

        public User(String name, String ip) {
            this.name = name;
            this.ip = ip;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }
    }
}
