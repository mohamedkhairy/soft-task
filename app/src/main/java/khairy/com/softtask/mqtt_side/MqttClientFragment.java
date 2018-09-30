package khairy.com.softtask.mqtt_side;

import android.content.Context;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import khairy.com.softtask.R;
import khairy.com.softtask.SecondView;
import khairy.com.softtask.connection.ConnectInterface;
import khairy.com.softtask.json.SSIDvalue;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MqttClientFragment extends Fragment {

    private final String URL = "http://ronixtech.com/ronix_services/";
    private static Retrofit retrofit;
    private SSIDvalue ssiDvalue;
    private MqttHelper mqttHelper;
    private WifiManager wifiManager;

    @BindView(R.id.ssid)
    TextView ssidTxt;
    @BindView(R.id.password)
    TextView passtxt;
    @BindView(R.id.searchtxt)
    TextView searchTxt;
    @BindView(R.id.linearSearch)
    LinearLayout linearSearch;

    @BindView(R.id.wifilist)
    ListView wifilist;


    ArrayList<String> wifiArray;
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.mqtt_frg_view, container, false);
        ButterKnife.bind(this , view);
        linearSearch.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wifiArray = new ArrayList();
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1, wifiArray);
        wifilist.setAdapter(adapter);

        getData();
    }

    public void getData(){
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ConnectInterface connect = retrofit.create(ConnectInterface.class);
        Call<SSIDvalue> call = connect.getValue();

        call.enqueue(new Callback<SSIDvalue>() {

            @Override
            public void onResponse(Call<SSIDvalue> call, Response<SSIDvalue> response) {
                ssiDvalue = response.body();
                ssidTxt.setText("SSID : " + ssiDvalue.getSSID());
                searchTxt.setText("Searching for " + ssiDvalue.getSSID());
                startMqtt();
                Log.d("ssid" , ssiDvalue.getSSID());
            }

            @Override
            public void onFailure(Call<SSIDvalue> call, Throwable t) {
                Toast.makeText(getActivity() , t.getLocalizedMessage() , Toast.LENGTH_LONG).show();
                Log.d("MAIN" , t.getMessage() + "\n" + t.getLocalizedMessage());
                t.getStackTrace();
            }
        });
    }

    private void startMqtt(){
        mqttHelper = new MqttHelper(getActivity().getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug",mqttMessage.toString());


                JSONObject object = new JSONObject(mqttMessage.toString());
                String pass = object.getString("PASS");
                if (pass != null){
                    passtxt.setText("PASS : " + pass);
                    scan();
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

    private void  scan(){

        wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        boolean success = wifiManager.startScan();

        if (success) {
            List<ScanResult> results = wifiManager.getScanResults();

            WifiConfiguration wifiConfig = new WifiConfiguration();
            wifiConfig.SSID = String.format("\"%s\"", "5hairi-Disk");
            wifiConfig.preSharedKey = String.format("\"%s\"", "khairy666");


//            int netId = wifiManager.addNetwork(wifiConfig);
//            wifiManager.disconnect();
//            wifiManager.enableNetwork(netId, true);
//            wifiManager.reconnect();

            if(wifiConfig.SSID.equals("5hairi-Disk")){
                Log.d("wifiii" , wifiConfig.SSID + " greeeeeeeeen");

                ((TextView)wifilist.getChildAt(0)).setTextColor(Color.GREEN);
            }

            for (ScanResult scanResult : results){
                Log.d("wifiii" , scanResult.SSID);
                wifiArray.add(scanResult.SSID);
                adapter.notifyDataSetInvalidated();
            }

            linearSearch.setVisibility(View.GONE);

        } else {
            Log.d("wifiii" , "noooooooooooo");

        }
    }

}
