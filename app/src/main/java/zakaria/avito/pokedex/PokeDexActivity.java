package zakaria.avito.pokedex;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zakaria.avito.pokedex.connectioncheck.ConnectionReceiver;
import zakaria.avito.pokedex.connectioncheck.PokeDexApplication;
import zakaria.avito.pokedex.models.Pokemon;
import zakaria.avito.pokedex.models.ApiResponse;
import zakaria.avito.pokedex.pokeapi.ApiService;

public class PokeDexActivity extends AppCompatActivity implements ConnectionReceiver.ConnectionReceiverListener {

    private static final String TAG = "PokeDexActivity";
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private boolean dataLoaded = false;
    private ImageView noConnectionImg, noConnectionBackground;
    private TextView noConnextionText1,noConnectionText2;
    private Button retryBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);

        noConnectionImg = findViewById(R.id.no_internet_icon);
        noConnectionBackground = findViewById(R.id.no_connection_back);
        noConnextionText1 = findViewById(R.id.no_connection_text1);
        noConnectionText2 = findViewById(R.id.no_connection_text2);
        retryBtn = findViewById(R.id.retry_btn);

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkConnection();
            }
        });

        checkConnection();

        recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new MyAdapter(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);


    }

    private void LoadData() {
        if (!dataLoaded){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://pokeapi.co")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiService service = retrofit.create(ApiService.class);
            Call<ApiResponse> call = service.getPokemonsNames();

            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                    if (response.isSuccessful()) {
                        dataLoaded = true;
                        ApiResponse apiResponse = response.body();
                        ArrayList<Pokemon> pokemons;

                        if (apiResponse != null) {
                            pokemons = apiResponse.getResults();
                            mAdapter.add(pokemons);
                        }

                    } else {
                        Log.e(TAG, " onResponse: " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Log.e(TAG, " onFailure: " + t.getStackTrace().toString());
                }
            });
        }

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(!isConnected) {
            //Hide the recyclerview and show the connection icon with text
            showConnectionError();
        }else {
            //Hide the icon and text and show the recyclerview + loaddata()
            hideConnectionError();
            LoadData();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        PokeDexApplication.getInstance().setConnectionListener(this);
    }


    private void checkConnection() {
        boolean isConnected = ConnectionReceiver.isConnected(getApplicationContext());
        if(!isConnected) {
            //Hide the recyclerview and show the connection icon with text
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show();
            showConnectionError();
        }else {
            //Hide the icon and text and show the recyclerview + loaddata()
            hideConnectionError();
            LoadData();
        }
    }

    private void showConnectionError(){
        noConnectionImg.setVisibility(View.VISIBLE);
        noConnectionBackground.setVisibility(View.VISIBLE);
        noConnextionText1.setVisibility(View.VISIBLE);
        noConnectionText2.setVisibility(View.VISIBLE);
        retryBtn.setVisibility(View.VISIBLE);

    }

    private void hideConnectionError(){
        noConnectionImg.setVisibility(View.GONE);
        noConnectionBackground.setVisibility(View.GONE);
        noConnextionText1.setVisibility(View.GONE);
        noConnectionText2.setVisibility(View.GONE);
        retryBtn.setVisibility(View.GONE);
    }
}
