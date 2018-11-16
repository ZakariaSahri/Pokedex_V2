package zakaria.avito.pokedex.pokemonlist.view;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zakaria.avito.pokedex.pokemonlist.models.ApiResponse;
import zakaria.avito.pokedex.pokemonlist.network.RetrofitInstance;
import zakaria.avito.pokedex.pokemonlist.pokeapi_interface.ApiService;

public class GetPokemonIntractorIplm implements MainContract.GetPokemonIntractor {


    @Override
    public void getNoticeArrayList(final OnFinishedListener onFinishedListener) {
        /** Create handle for the RetrofitInstance interface*/
        ApiService service = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
        /** Call the method with parameter in the interface to get the notice data*/
        Call<ApiResponse> call = service.getPokemonsNames();

        /**Log the URL called*/
        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                onFinishedListener.onFinished(response.body().getResults());

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
