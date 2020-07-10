package com.example.baking.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Html;
import android.util.Log;

import com.example.baking.Fragments.IngredientFragment;
import com.example.baking.POJOs.Ingredient;
import com.example.baking.R;

import java.util.ArrayList;

public class IngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Getting data from intent and showing it.
        Intent intent = getIntent();
        ArrayList<Ingredient> ingredients = intent.getParcelableArrayListExtra("Ingredients");

        //Setting title with custom textColor.
        getSupportActionBar().setTitle((Html.fromHtml("<font color=\""+getColor(R.color.textColor)+"\">"+ getString(R.string.ingredients)+"</font>")));

        //Setting up the ingredientFragment to show it.
        IngredientFragment ingredientFragment = new IngredientFragment();
        ingredientFragment.setIngredients(ingredients);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.ingredientFragmentContainer,ingredientFragment)
                .commit();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}