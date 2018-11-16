package zakaria.avito.pokedex.pokemonlist.view;

import java.util.ArrayList;

import zakaria.avito.pokedex.pokemonlist.models.Pokemon;

public interface RecyclerItemClickListener {

    void onItemClick(Pokemon pokemon);
    void onItemLongClick(int position,ArrayList<Pokemon> pokemons);

}
