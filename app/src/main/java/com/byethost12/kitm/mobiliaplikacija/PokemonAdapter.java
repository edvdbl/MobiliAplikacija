package com.byethost12.kitm.mobiliaplikacija;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by mokytojas on 2018-02-14.
 */

public class PokemonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private LayoutInflater inflater;
    private List<Pokemonas> pokemonai = Collections.emptyList();
    //private Pokemonas currentPokemon;

    public static final String ENTRY_ID = "com.byethost12.kitm.mobiliaplikacija";

    //konstruktorius reikalingas susieti
    // esama langa ir perduoti sarasa pokemonui is DB
    public PokemonAdapter(Context context,List<Pokemonas> pokemonai){
        this.context = context;
        this.pokemonai = pokemonai;
        inflater = LayoutInflater.from(context);
    }

    @Override
    //inflate the layout when viewholder is created
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_pokemon,parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    //bind data
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //get current position of item in
        // recyclerview to bind data and assign value from list
        MyHolder myHolder = (MyHolder) holder;
        Pokemonas currentPokemon = pokemonai.get(position);
        myHolder.tvPavadinimas.setText(           currentPokemon.getName());
        myHolder.tvTipas.setText("Tipas: "      + currentPokemon.getType());
        myHolder.tvGalia.setText("Galia: "      + currentPokemon.getCp());
        myHolder.tvId.setText("ID: "            + currentPokemon.getId());
        myHolder.tvSavybes.setText("Savybės: "  + currentPokemon.getAbilities());
    }

    @Override
    public int getItemCount() {
        return pokemonai.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvPavadinimas,tvTipas,tvGalia,tvSavybes, tvId;

        public MyHolder(View itemView){
            super(itemView);
            tvPavadinimas   = (TextView)itemView.findViewById(R.id.pavadinimas);
            tvTipas         = (TextView)itemView.findViewById(R.id.tipas);
            tvGalia         = (TextView)itemView.findViewById(R.id.galia);
            tvSavybes       = (TextView)itemView.findViewById(R.id.savybes);
            tvId            = (TextView)itemView.findViewById(R.id.id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int itemPosition = getAdapterPosition();
            int pokemonaiID = pokemonai.get(itemPosition).getId();

            // TODO: siųsti pasirinkto pokemono objektą vietoj id
            Pokemonas pokemonas = pokemonai.get(itemPosition);

            Intent intent = new Intent(context, EntryActivity.class);

            intent.putExtra(ENTRY_ID, pokemonaiID);
            context.startActivity(intent);
        }
    }
}


