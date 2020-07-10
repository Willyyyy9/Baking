package com.example.baking.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.baking.POJOs.Ingredient;
import com.example.baking.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientRVAdapter extends RecyclerView.Adapter<IngredientRVAdapter.IngredientViewHolder> {
    private ArrayList<Ingredient> ingredients;

    //Constructor of the Adapter
    public IngredientRVAdapter(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflating the Activity
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item,parent,false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        //Populating the RecyclerView with Data
        String quantityText = ingredients.get(position).getQuantity() + " " + ingredients.get(position).getMeasure();
        holder.quantityTextView.setText(quantityText);
        holder.ingredientTextView.setText(ingredients.get(position).getIngredient());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        //Initializing Views using ButterKnife
        @BindView(R.id.quantityTextView) TextView quantityTextView;
        @BindView(R.id.ingredientTextView) TextView ingredientTextView;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
