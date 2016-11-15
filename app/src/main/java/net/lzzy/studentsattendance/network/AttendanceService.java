package net.lzzy.studentsattendance.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;


public class AttendanceService extends Thread {
    private String id;
    private String name;
    private Handler handler;
    private final String address;
    private final int port;
    private static final String JSON_STU_NAME = "NAME";
    private static final String JSON_STU_ID = "ID";

    public AttendanceService(String address, int port, String stuId, String name, Handler handler) {
        this.id = stuId;
        this.name = name;
        this.handler = handler;
        this.address = address;
        this.port = port;
    }

    private byte[] getOutInfo() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSON_STU_NAME, name);
        jsonObject.put(JSON_STU_ID, id);
        return jsonObject.toString().getBytes();
    }


    @Override
    public void run() {
        try {
            Socket socket = new Socket(address, port);
            socket.getOutputStream().write(getOutInfo());
            socket.getOutputStream().flush();
            socket.shutdownOutput();
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int b = socket.getInputStream().read();
                if (b == -1)
                    break;
                arrayOutputStream.write(b);
            }
            String json = new String(arrayOutputStream.toByteArray(), "UTF-8");
            JSONObject jsonObject = new JSONObject(json);

            if (jsonObject.getBoolean("RESULT"))
                handler.sendEmptyMessage(1);
            else {
                Message message = new Message();
                message.what = 0;
                Bundle bundle = new Bundle();
                bundle.putString("EXTRA_INFO", jsonObject.getString("EXTRA_INFO"));
                message.setData(bundle);
                handler.sendMessage(message);
            }
        } catch (JSONException | IOException e) {
            handler.sendEmptyMessage(0);
        }

    }
}
