package khairy.com.softtask.connection;

import java.util.List;

import khairy.com.softtask.json.SSIDvalue;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ConnectInterface {

    @GET("task/srv.php")
    Call<SSIDvalue> getValue();
}
