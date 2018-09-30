package khairy.com.softtask;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import khairy.com.softtask.connection.ConnectInterface;
import khairy.com.softtask.fragments.UserScanFragment;
import khairy.com.softtask.json.SSIDvalue;
import khairy.com.softtask.mqtt_side.MqttClientFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondView extends AppCompatActivity {

//    private final String URL = "http://ronixtech.com/ronix_services/";
//    private static Retrofit retrofit;
//    private SSIDvalue ssiDvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_view);
//        getData();

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.placeholder, new UserScanFragment(), UserScanFragment.class.getSimpleName());
//        fragmentTransaction.commit();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, new MqttClientFragment(), MqttClientFragment.class.getSimpleName());
        fragmentTransaction.commit();

    }


//    public void getData(){
//        retrofit = new retrofit2.Retrofit.Builder()
//                .baseUrl(URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        ConnectInterface connect = retrofit.create(ConnectInterface.class);
//        Call<SSIDvalue> call = connect.getValue();
//
//        call.enqueue(new Callback<SSIDvalue>() {
//
//            @Override
//            public void onResponse(Call<SSIDvalue> call, Response<SSIDvalue> response) {
//                ssiDvalue = response.body();
//                Log.d("ssid" , ssiDvalue.getSSID());
//            }
//
//            @Override
//            public void onFailure(Call<SSIDvalue> call, Throwable t) {
//                Toast.makeText(SecondView.this , t.getLocalizedMessage() , Toast.LENGTH_LONG).show();
//                Log.d("MAIN" , t.getMessage() + "\n" + t.getLocalizedMessage());
//                t.getStackTrace();
//            }
//        });
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home :
                    finish();
                break;
        }
        return true;
    }


}
