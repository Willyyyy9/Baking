package com.example.baking.Loaders;

import android.content.Context;

import com.example.baking.BakingUtils;
import com.example.baking.POJOs.Recipe;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class RecipeLoader extends AsyncTaskLoader<ArrayList<Recipe>> {

    private String url;

    public RecipeLoader(@NonNull Context context, String url) {
        super(context);
        this.url = url;
    }

    @Nullable
    @Override
    public ArrayList<Recipe> loadInBackground() {
        if (url == null) {
            return null;
        }
        return BakingUtils.fetchRecipeListData(url);
    }

    @Override
    protected void onStartLoading() { forceLoad(); }
}
