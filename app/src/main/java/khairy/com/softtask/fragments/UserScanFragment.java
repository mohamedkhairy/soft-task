package khairy.com.softtask.fragments;

import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import khairy.com.softtask.MainActivity;
import khairy.com.softtask.R;

import static android.content.Context.WIFI_SERVICE;

public class UserScanFragment extends Fragment {


    @BindView(R.id.scan)
    Button btnScan;
    @BindView(R.id.listviewip)
     ListView listViewIp;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    ArrayList<String> ipList;
    ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.user_scaner_frg, container, false);
        ButterKnife.bind(this , view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ipList = new ArrayList();
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1, ipList);

        listViewIp.setAdapter(adapter);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ScanIpTask(getIpAdress()).execute();
            }
        });

    }

    public String getIpAdress(){
        WifiManager wm = (WifiManager) getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        Log.d("xoxo" , ip);
        for (int i = ip.length()-1 ; ip.length() >= i ; i--){

            if (ip.charAt(i) == '.'){
                ip = ip.substring(0,i)+".";
                break;
            }
        }
        Log.d("xoxo" , ip);
        return ip;
    }

    private class ScanIpTask extends AsyncTask<Void, String, Void> {


        private String subnet ;
        static final int lower = 1;
        static final int upper = 250;
        static final int timeout = 1000;

        public ScanIpTask(String subnet) {
            this.subnet = subnet;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            ipList.clear();
            adapter.notifyDataSetInvalidated();
            Toast.makeText(getActivity(), "Scan IP...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... params) {


            for (int i = lower; i <= upper; i++) {
                String host = subnet + i;

                try {

                    InetAddress inetAddress = InetAddress.getByName(host);
                    if (inetAddress.isReachable(timeout)){
                        publishProgress(inetAddress.toString());
                    }

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            ipList.add(values[0]);
            adapter.notifyDataSetInvalidated();
            Toast.makeText(getActivity(), values[0], Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Done", Toast.LENGTH_LONG).show();
        }
    }
}
