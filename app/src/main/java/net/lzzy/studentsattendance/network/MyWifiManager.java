package net.lzzy.studentsattendance.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.util.List;


public class MyWifiManager {

    private static final String SERVICE_SSID = "qiandao";
    private static final int WIFICIPHER_NOPASS = 1;
    private static final int WIFICIPHER_WEP = 2;
    private static final int WIFICIPHER_WPA = 3;

    private WifiManager manager;
    private Context context;
    private OnConnectSuccess onWIfiListener;
    private NetworkInfo info;

    public WifiManager getManager() {
        return manager;
    }

    public interface OnConnectSuccess {
        void onConnectSuccess(boolean state);

        void isWifiChange();
    }


    public void startBoardCast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(wifiReceive, filter);
    }

    public boolean isConnectCorrect() {
        return info != null && info.getState() == NetworkInfo.State.CONNECTED && manager.getConnectionInfo().getSSID().equals("\"" + SERVICE_SSID + "\"");

    }


    public void startAttend() {

        if (manager.getWifiState() == WifiManager.WIFI_STATE_ENABLING)
            return;
        if (!manager.isWifiEnabled()) {
            manager.setWifiEnabled(true);
            return;
        }
        startConnect();
    }

    private BroadcastReceiver wifiReceive = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onWIfiListener.isWifiChange();
            switch (intent.getAction()) {
                case WifiManager.WIFI_STATE_CHANGED_ACTION:

                    break;
                case WifiManager.NETWORK_STATE_CHANGED_ACTION:

                    info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                    if (onWIfiListener != null)
                        onWIfiListener.onConnectSuccess(isConnectCorrect());
                    break;
            }
        }
    };

    public String getServeIp() {
        return intToIp(manager.getDhcpInfo().serverAddress);


    }

    private String intToIp(int i) {

        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

    public MyWifiManager(Context context, OnConnectSuccess onWIfiListener) {
        manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        this.context = context;
        this.onWIfiListener = onWIfiListener;
        startBoardCast();
    }

    public void startConnect() {

        WifiConfiguration configuration = isExsits(SERVICE_SSID);
        if (configuration == null) {
            configuration = createWifiInfo(SERVICE_SSID, "", 1);
            manager.addNetwork(configuration);
        }

        manager.enableNetwork(configuration.networkId, true);
    }

    private WifiConfiguration isExsits(String SSID) {
        List<WifiConfiguration> existingConfigs = manager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                return existingConfig;
            }
        }
        return null;
    }

    public WifiConfiguration createWifiInfo(String SSID, String password, int type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";
        if (type == WIFICIPHER_NOPASS) {
            config.wepKeys[0] = "\"" + "\"";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (type == WIFICIPHER_WEP) {
            config.preSharedKey = "\"" + password + "\"";
            config.hiddenSSID = false;
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers
                    .set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers
                    .set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers
                    .set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers
                    .set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (type == WIFICIPHER_WPA) {
            config.preSharedKey = "\"" + password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers
                    .set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement
                    .set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedGroupCiphers
                    .set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        } else {
            return null;
        }
        return config;
    }

}
