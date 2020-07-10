package com.example.baking.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.widget.Toast;

import com.example.baking.Fragments.IngredientFragment;
import com.example.baking.Fragments.RecipeDetailFragment;
import com.example.baking.Fragments.StepFragment;
import com.example.baking.POJOs.Ingredient;
import com.example.baking.POJOs.Recipe;
import com.example.baking.POJOs.Step;
import com.example.baking.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity implements RecipeDetailFragment.OnIngredientsClickListener,
        RecipeDetailFragment.onStepsClickListener {
    private Recipe recipe;
    private boolean isTablet;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);


        //setting boolean to know if device is tablet or not.
        isTablet = findViewById(R.id.detailFragmentTabletContainer) != null;

        //Getting data from intent
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("Recipe");

        //Setting title with custom textColor.
        getSupportActionBar().setTitle((Html.fromHtml("<font color=\""+getColor(R.color.textColor)+"\">"+recipe.getName()+"</font>")));

        //Setting the fragment to show it.
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setIngredientsAndSteps(recipe.getIngredients(),recipe.getSteps());
        fragmentManager = getSupportFragmentManager();
        if(isTablet){
            //If device is tablet
            fragmentManager.beginTransaction()
                    .add(R.id.detailFragmentTabletContainer, recipeDetailFragment)
                    .commit();
        }else {
            //if device is a handset
            fragmentManager.beginTransaction()
                    .add(R.id.detailFragmentContainer,recipeDetailFragment)
                    .commit();
        }


    }


    @Override
    public void onIngredientsSelected(ArrayList<Ingredient> ingredients) {
        //OnClickListener if the ingredient item is selected
        if (isTablet){
            //If the device is tablet, the screen will be a two pane screen
            //The ingredientFragment will be showed on the second pane
            IngredientFragment ingredientFragment = new IngredientFragment();
            ingredientFragment.setIngredients(ingredients);
            fragmentManager.beginTransaction()
                    .replace(R.id.stepOrIngredientFragmentTabletContainer,ingredientFragment)
                    .commit();
        }else {
            //If the device is mobile, IngredientActivity will be loaded which contains the ingredientFragment
            final Intent intent = new Intent(this, IngredientActivity.class);
            intent.putParcelableArrayListExtra("Ingredients",ingredients);
            startActivity(intent);
        }

    }

    @Override
    public void onStepsSelected(int position, ArrayList<Step> steps) {
        //OnClickListener if the step items are selected
        if(isTablet){
            //If the device is tablet, the screen will be a two pane screen
            //The stepFragment will be showed on the second pane
            StepFragment stepFragment = new StepFragment();
            stepFragment.setSteps(position,steps);
            fragmentManager.beginTransaction()
                    .replace(R.id.stepOrIngredientFragmentTabletContainer,stepFragment)
                    .commit();
            stepFragment.isTablet(true);
        }else {
            //If the device is mobile, StepActivity will be loaded which contains the stepFragment
            final Intent intent = new Intent(this,StepActivity.class);
            intent.putParcelableArrayListExtra("Steps",steps);
            intent.putExtra("Position",position);
            startActivity(intent);
        }

    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}