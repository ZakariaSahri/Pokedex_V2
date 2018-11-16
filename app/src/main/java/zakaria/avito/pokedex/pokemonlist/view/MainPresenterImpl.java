package zakaria.avito.pokedex.pokemonlist.view;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import zakaria.avito.pokedex.R;
import zakaria.avito.pokedex.pokemonlist.models.Pokemon;

public class MainPresenterImpl implements MainContract.presenter, MainContract.GetPokemonIntractor.OnFinishedListener {


    private MainContract.MainView mainView;
    private MainContract.GetPokemonIntractor getPokemonIntractor;

    public MainPresenterImpl(MainContract.MainView mainView, MainContract.GetPokemonIntractor getPokemonIntractor) {
        this.mainView = mainView;
        this.getPokemonIntractor = getPokemonIntractor;
    }

    public MainPresenterImpl(){}


    @Override
    public void onDestroy() {

        mainView = null;

    }

    @Override
    public void requestDataFromServer() {
        getPokemonIntractor.getNoticeArrayList(this);

    }

    @Override
    public void loadImages(int i, Context context, ImageView sprite) {
        Glide.with(context)
                .load("http://pokeapi.co/media/sprites/pokemon/" + i + ".png")
                .apply(new RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.imagenotprovided))
                .thumbnail(0.1f)
                .into(sprite);
    }

    @Override
    public void onFinished(ArrayList<Pokemon> pokemonArrayList) {
        if(mainView != null){
            mainView.setDataToRecyclerView(pokemonArrayList);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if(mainView != null){
            mainView.onResponseFailure(t);
        }
    }
}
