package zakaria.avito.pokedex.pokeapi;

import retrofit2.Call;
import retrofit2.http.GET;
import zakaria.avito.pokedex.models.ApiResponse;

public interface ApiService {

    @GET("/api/v2/pokemon")
    Call<ApiResponse> getPokemonsNames();

}
