package zakaria.avito.pokedex.pokemonlist.view;

import android.content.Context;
import android.widget.ImageView;

import java.util.ArrayList;

import zakaria.avito.pokedex.pokemonlist.models.Pokemon;

public interface MainContract {

    /**
     * Call when user interact with the view and other when view OnDestroy()
     * */
    interface presenter{

        void onDestroy();

        void requestDataFromServer();

        void loadImages(int i, Context context, ImageView sprite);
    }

    /**
     * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
     * while the setDataToRecyclerView and onResponseFailure is fetched from the GetNoticeInteractorImpl class
     **/
    interface MainView {

        void setDataToRecyclerView(ArrayList<Pokemon> pokemonArrayList);

        void onResponseFailure(Throwable throwable);

    }

    /**
     * Intractors are classes built for fetching data from your database, web services, or any other data source.
     **/
    interface GetPokemonIntractor {

        interface OnFinishedListener {
            void onFinished(ArrayList<Pokemon> pokemonArrayList);
            void onFailure(Throwable t);
        }

        void getNoticeArrayList(OnFinishedListener onFinishedListener);
    }

}
