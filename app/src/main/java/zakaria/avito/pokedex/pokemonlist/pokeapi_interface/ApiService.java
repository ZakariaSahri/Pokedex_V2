package zakaria.avito.pokedex.pokemonlist.pokeapi_interface;

import retrofit2.Call;
import retrofit2.http.GET;
import zakaria.avito.pokedex.pokemonlist.models.ApiResponse;

public interface ApiService {

    @GET("pokemon")
    Call<ApiResponse> getPokemonsNames();

}
