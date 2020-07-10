package com.example.baking.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.baking.POJOs.Recipe;
import com.example.baking.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeRVAdapter extends RecyclerView.Adapter<RecipeRVAdapter.RecipeViewHolder> {

    //Defining Global Variables
    private ArrayList<Recipe> recipes;
    final private  ListItemClickListener listItemClickListener;

    //Defining an interface for the ClickListener
    public interface ListItemClickListener{
        void onClickItemList(int clickItemIndex);
    }

    //Constructor of the adapter
    public RecipeRVAdapter(ArrayList<Recipe> recipes, ListItemClickListener clickListener) {
        this.recipes = recipes;
        this.listItemClickListener = clickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflating the Activity
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item,parent,false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        //Populating the RecyclerView with Data
        Recipe recipe = recipes.get(position);
        String name = recipe.getName();
        holder.recipeNameTextView.setText(name);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    //ViewHolder of the RecyclerView
    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Initializing Views using ButterKnife
        @BindView(R.id.recipeNameTextView) TextView recipeNameTextView;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            //ClickListener of the RecyclerView
            listItemClickListener.onClickItemList(getAdapterPosition());
        }
    }
}
