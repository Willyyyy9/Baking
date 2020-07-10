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
import android.util.Log;
import android.widget.TextView;

import com.example.baking.Fragments.RecipeFragment;
import com.example.baking.POJOs.BakingVariables;
import com.example.baking.POJOs.Recipe;
import com.example.baking.R;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements RecipeFragment.OnRecipeClickListener{
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("File",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //Setting title with custom textColor.
        getSupportActionBar().setTitle((Html.fromHtml("<font color=\""+getColor(R.color.textColor)+"\">"+getString(R.string.app_name)+"</font>")));

        //Setting up and showing the fragment in the activity
        if(findViewById(R.id.recipeFragmentTabletContainer) != null){
            //If the device was a tablet
            RecipeFragment recipeFragment = new RecipeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                .add(R.id.recipeFragmentTabletContainer, recipeFragment)
                .commit();
            recipeFragment.isTablet(findViewById(R.id.recipeFragmentTabletContainer) != null);
            Log.d("TAG", "onCreate: tablet");
        } else {
            //If device was a mobile handset
            RecipeFragment recipeFragment = new RecipeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipeFragmentContainer, recipeFragment)
                    .commit();
            recipeFragment.isTablet(findViewById(R.id.recipeFragmentTabletContainer) != null);
            Log.d("TAG", "onCreate: mobile");
    }
    }


    @Override
    public void onRecipeSelected(Recipe recipeSelected) {
        //OnCLickListener for the recipeRecyclerView
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("Recipe", recipeSelected);
        editor.putString("name",recipeSelected.getName());
        Gson gson = new Gson();
        String json = gson.toJson(recipeSelected.getIngredients());
        editor.putString("ingredients",json);
        editor.apply();
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}