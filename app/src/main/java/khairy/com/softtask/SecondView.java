package khairy.com.softtask;

import android.support.v4.app.Fragment;
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
import khairy.com.softtask.fragments.LoginFragment;
import khairy.com.softtask.fragments.UserScanFragment;
import khairy.com.softtask.json.SSIDvalue;
import khairy.com.softtask.mqtt_side.MqttClientFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondView extends AppCompatActivity {

    MqttClientFragment mqttClientFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_view);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mqttClientFragment = new MqttClientFragment();
        fragmentTransaction.replace(R.id.placeholder, mqttClientFragment, MqttClientFragment.class.getSimpleName());
        fragmentTransaction.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                FragmentManager fragManager = this.getSupportFragmentManager();
                int count = this.getSupportFragmentManager().getBackStackEntryCount();
                String frag = fragManager.getFragments().get(count > 0 ? count - 1 : count).getTag();

                if (frag.equals("MqttClientFragment")) {
                    finish();
                } else {
                    getSupportFragmentManager().popBackStack(MqttClientFragment.BACK_STACK_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }

                break;

        }
        return true;
    }


}
