package com.example.baking.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baking.Adapters.IngredientRVAdapter;
import com.example.baking.POJOs.Ingredient;
import com.example.baking.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientFragment extends Fragment {
    //Initializing views
    @BindView(R.id.ingredientRecyclerView) RecyclerView ingredientRecyclerView;
    private ArrayList<Ingredient> ingredients;

    //Public Constructor
    public IngredientFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflating the IngredientFragment
        View rootView = inflater.inflate(R.layout.ingredient_fragment,container,false);
        ButterKnife.bind(this,rootView);

        //Setting the layout manager for the the RecyclerView and inflating the RecyclerView
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        IngredientRVAdapter ingredientRVAdapter = new IngredientRVAdapter(ingredients);
        ingredientRecyclerView.setAdapter(ingredientRVAdapter);

        return rootView;
    }

    public void setIngredients(ArrayList<Ingredient> ingredientArrayList){
        //Setter to populate the ingredientRecyclerView with data
        ingredients = ingredientArrayList;
    }
}
