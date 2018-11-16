package zakaria.avito.pokedex.pokemonlist.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import zakaria.avito.pokedex.R;
import zakaria.avito.pokedex.pokemonlist.models.Pokemon;
import zakaria.avito.pokedex.utils.ConnectionReceiver;
import zakaria.avito.pokedex.PokeDexApplication;

public class PokeDexActivity extends AppCompatActivity implements  MainContract.MainView,ConnectionReceiver.ConnectionReceiverListener {



    RecyclerView recyclerView;
    private ImageView noConnectionImg, noConnectionBackground;
    private TextView noConnectionText1,noConnectionText2;
    private Button retryBtn;
    private MainContract.presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);
        initializeViews();
        initializeRecyclerView();

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkConnection();
            }
        });


        presenter = new MainPresenterImpl(this, new GetPokemonIntractorIplm());
        presenter.requestDataFromServer();
        checkConnection();




    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(!isConnected) {
            showConnectionError();
        }else {
            hideConnectionError();
            presenter.requestDataFromServer();
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
            presenter.requestDataFromServer();
        }
    }

    private void showConnectionError(){
        noConnectionImg.setVisibility(View.VISIBLE);
        noConnectionBackground.setVisibility(View.VISIBLE);
        noConnectionText1.setVisibility(View.VISIBLE);
        noConnectionText2.setVisibility(View.VISIBLE);
        retryBtn.setVisibility(View.VISIBLE);

    }

    private void hideConnectionError(){
        noConnectionImg.setVisibility(View.GONE);
        noConnectionBackground.setVisibility(View.GONE);
        noConnectionText1.setVisibility(View.GONE);
        noConnectionText2.setVisibility(View.GONE);
        retryBtn.setVisibility(View.GONE);
    }

    @Override
    public void setDataToRecyclerView(ArrayList<Pokemon> pokemonArrayList) {
        MyAdapter adapter = new MyAdapter(getApplicationContext(),pokemonArrayList , recyclerItemClickListener);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(PokeDexActivity.this,
                "Something went wrong...Error message: " + throwable.getMessage(),
                Toast.LENGTH_LONG).show();
    }
    private RecyclerItemClickListener recyclerItemClickListener = new RecyclerItemClickListener() {
        @Override
        public void onItemClick(Pokemon pokemon) {

            Toast.makeText(PokeDexActivity.this,
                    "Pokemone name:  " + pokemon.getName(),
                    Toast.LENGTH_LONG).show();

        }

        @Override
        public void onItemLongClick(int position ,ArrayList<Pokemon> pokemons) {
            pokemons.remove(position);

        }
    };

    private void initializeRecyclerView() {


        recyclerView = findViewById(R.id.recyclerView);
        //recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);


    }

    private void initializeViews() {


        noConnectionImg = findViewById(R.id.no_internet_icon);
        noConnectionBackground = findViewById(R.id.no_connection_back);
        noConnectionText1 = findViewById(R.id.no_connection_text1);
        noConnectionText2 = findViewById(R.id.no_connection_text2);
        retryBtn = findViewById(R.id.retry_btn);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
