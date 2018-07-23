package com.itsdf07.debug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itsdf07.R;
import com.itsdf07.alog.ALog;
import com.itsdf07.tcp.SocketClientService;

/**
 * @Description: tcp通讯协议Debug界面
 * @Author itsdf07
 * @Time 2018/7/23/023 15:14
 */

public class TcpDebugActivity extends AppCompatActivity implements SocketClientService.MsgCallback {
    private EditText etIp;
    private EditText etPort;
    private EditText etMsg;
    private TextView tvMsg;

    private String host;
    private int port;
    private SocketClientService socketClientService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tcpdebug_activity);
        init();
    }

    private void init() {
        socketClientService = SocketClientService.getInstance().setMsgCallback(this);
        initView();
    }

    private void initView() {
        etIp = (EditText) findViewById(R.id.etAddressIp);
        etIp.setText(SocketClientService.getInstance().defaultHost());
        etPort = (EditText) findViewById(R.id.etAddressPort);
        etPort.setText(SocketClientService.getInstance().defaultPort() + "");
        etMsg = (EditText) findViewById(R.id.etMsg);
        tvMsg = (TextView) findViewById(R.id.tvMeg);
        tvMsg.setMovementMethod(ScrollingMovementMethod.getInstance());
        findViewById(R.id.btnTcpConn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etIp == null || etPort == null) {
                    Toast.makeText(TcpDebugActivity.this, "初始化失败:etIp=" + etIp + ",etPort=" + etPort, Toast.LENGTH_SHORT).show();
                    return;
                }
                host = etIp.getText().toString();
                String mPort = etPort.getText().toString();
                if (TextUtils.isEmpty(host) || TextUtils.isEmpty(mPort)) {
                    Toast.makeText(TcpDebugActivity.this, "地址或者端口内容为空:host=" + host + ",mPort=" + mPort, Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    port = Integer.parseInt(mPort);
                } catch (NumberFormatException e) {
                    ALog.e("port转成int类型错误", e.toString());
                    Toast.makeText(TcpDebugActivity.this, "端口类型不对", Toast.LENGTH_SHORT).show();
                    return;
                }
                socketClientService.put2CachedThreadPool(new Runnable() {
                    @Override
                    public void run() {
                        socketClientService.connectServer(host, port);
                        String content = "itsdf07";
                        socketClientService.sendMessage(content);
                    }
                });
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                }).start();
            }
        });
        findViewById(R.id.btnTcpDisConn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketClientService.put2CachedThreadPool(new Runnable() {
                    @Override
                    public void run() {
                        String content = "itsdf07 to disconnect";
                        socketClientService.sendMessage(content);
                        socketClientService.closeConnection();
                    }
                });
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//
//                    }
//                }).start();

            }
        });
        findViewById(R.id.btnTcpSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketClientService.put2CachedThreadPool(new Runnable() {
                    @Override
                    public void run() {
                        socketClientService.sendMessage(etMsg.getText().toString());
                    }
                });
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                }).start();
            }
        });
        findViewById(R.id.btnTcpSendData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketClientService.put2CachedThreadPool(new Runnable() {
                    @Override
                    public void run() {
                        String content = "7E0100002D01345547748500EF002C012F373031313142534A2D413642440000000000000000000000003534373734383501C2B3485732393230787E";
                        socketClientService.sendMessage(content);
                    }
                });
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                }).start();
            }
        });
    }

    @Override
    public void onResponseResult(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvMsg.setText(tvMsg.getText().toString() + "\n" + msg);
            }
        });
    }
}
