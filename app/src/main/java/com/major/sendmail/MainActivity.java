package com.major.sendmail;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Order> orders = new ArrayList<>();

    private String mFilename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int length = Const.OrderInfo.orderOne.length;
        for (int i = 0; i < length; i++) {
            Order order = new Order(Const.OrderInfo.orderOne[i][0], Const.OrderInfo.orderOne[i][1],
                    Const.OrderInfo.orderOne[i][2], Const.OrderInfo.orderOne[i][3]);
            orders.add(order);
        }
        findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String path = Environment.getExternalStorageDirectory() + File.separator + "xls" + File.separator + "excel_" + System.currentTimeMillis() / 1000 + ".xls";
                    mFilename = ExcelUtil.writeExcel(MainActivity.this, orders, path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btn1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFilename != null) {
                    sendMail("1558667079@qq.com", "订单", "邮件由系统自动发送，请不要回复！", mFilename);
                    Toast.makeText(MainActivity.this, "邮件发送成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "文件不存在", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void sendMail(final String toMail, final String title, final String body, final String path) {
        new Thread(new Runnable() {
            public void run() {
                EmailUtil emailUtil = new EmailUtil();
                try {
                    String account = "cmmailserver@canmou123.com";
                    String password = "CANmou123";
                    // String authorizedPwd = "vxosxkgtwrtxvoqz";
                    emailUtil.sendMail(toMail, account, "smtp.mxhichina.com", account, password, title, body, path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
