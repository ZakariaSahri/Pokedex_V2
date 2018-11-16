package zakaria.avito.pokedex.pokemonlist.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import zakaria.avito.pokedex.R;
import zakaria.avito.pokedex.pokemonlist.models.Pokemon;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Pokemon> pokemonsNames;
    private RecyclerItemClickListener recyclerItemClickListener;
    private MainContract.presenter presenter;
    private Context context;



    public MyAdapter(Context context,ArrayList<Pokemon> pokemonsNames , RecyclerItemClickListener recyclerItemClickListener) {
        this.context = context;
        this.pokemonsNames = pokemonsNames;
        this.recyclerItemClickListener = recyclerItemClickListener;
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Pokemon p = pokemonsNames.get(position);
        holder.name.setText(p.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerItemClickListener.onItemClick(pokemonsNames.get(position));
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                recyclerItemClickListener.onItemLongClick(position,pokemonsNames);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, pokemonsNames.size());
                return true;
            }
        });
        presenter = new MainPresenterImpl();

       presenter.loadImages(p.getNumber(),context,holder.sprite);

    }

    @Override
    public int getItemCount() {
        return pokemonsNames.size();
    }

    /*
    void deleteItem(int index) {
        presenter.deleteItem(index,pokemonsNames);
        notifyItemRemoved(index);
        notifyItemRangeChanged(index, pokemonsNames.size());
    }*/

}
