package com.example.baking.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baking.Adapters.RecipeDetailRVAdapter;
import com.example.baking.POJOs.Ingredient;
import com.example.baking.POJOs.Step;
import com.example.baking.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment implements RecipeDetailRVAdapter.ListItemClickListener {

    //Declaring Global Variables
    @BindView(R.id.recipeDetailRecyclerView) RecyclerView recipeDetailRecyclerView;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private OnIngredientsClickListener mIngredientCallback;
    private onStepsClickListener mStepCallback;

    public interface OnIngredientsClickListener {
        void onIngredientsSelected(ArrayList<Ingredient> ingredients);
    }

    public interface onStepsClickListener {
        void onStepsSelected(int position, ArrayList<Step> steps);
    }


    //Empty Constructor
    public RecipeDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the RecipeDetailFragment
        View rootView = inflater.inflate(R.layout.recipe_detail_fragment,container,false);
        ButterKnife.bind(this,rootView);

        //Setting the layout manager for the the RecyclerView and inflating the RecyclerView
        recipeDetailRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecipeDetailRVAdapter recipeDetailRVAdapter = new RecipeDetailRVAdapter(ingredients,steps,this);
        recipeDetailRecyclerView.setAdapter(recipeDetailRVAdapter);

        return rootView;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            //Checking that the OnIngredientClickListener is working
            mIngredientCallback = (OnIngredientsClickListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnIngredientsClickListener");
        }
        try{
            //Checking that the OnStepClickListener is working
            mStepCallback = (onStepsClickListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement onStepsClickListener");
        }
    }

    public void setIngredientsAndSteps(ArrayList<Ingredient> ingredientArrayList, ArrayList<Step> stepArrayList){
        //A setter for the ArrayList<Ingredient> and ArrayList<Step>.
        ingredients = ingredientArrayList;
        steps = stepArrayList;
    }

    @Override
    public void onClickItemList(int clickItemIndex) {
        //ClickListener for the RecyclerView
        if(clickItemIndex == 0){
            mIngredientCallback.onIngredientsSelected(ingredients);
        }else{
            mStepCallback.onStepsSelected(clickItemIndex-1,steps);
        }

    }
}
