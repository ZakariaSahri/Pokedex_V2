package zakaria.avito.pokedex;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import zakaria.avito.pokedex.models.Pokemon;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Pokemon> pokemonsNames;
    private Context context;

    public MyAdapter(Context context) {
        this.context = context;
        pokemonsNames = new ArrayList<>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView sprite;
        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            sprite = itemView.findViewById(R.id.sprite);
            name = itemView.findViewById(R.id.name);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent
                .getContext())
                .inflate(R.layout.pokemon_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon p = pokemonsNames.get(position);
        holder.name.setText(p.getName());
        int i = pokemonsNames.indexOf(p) + 1;

        Glide.with(context)
                .load("http://pokeapi.co/media/sprites/pokemon/" + i + ".png")
                .apply(new RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.imagenotprovided))
                .thumbnail(0.1f)
                .into(holder.sprite);
    }

    @Override
    public int getItemCount() {
        return pokemonsNames.size();
    }

    public void add(ArrayList<Pokemon> pokemonsList) {
        pokemonsNames.addAll(pokemonsList);
        notifyDataSetChanged();
    }


}
