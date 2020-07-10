package com.example.baking.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baking.Adapters.RecipeRVAdapter;
import com.example.baking.Loaders.RecipeLoader;
import com.example.baking.POJOs.BakingVariables;
import com.example.baking.POJOs.Recipe;
import com.example.baking.R;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class RecipeFragment extends Fragment implements RecipeRVAdapter.ListItemClickListener,
        LoaderManager.LoaderCallbacks<ArrayList<Recipe>> {

    //Declaring Global Variables
    @BindView(R.id.recipesRecyclerView) RecyclerView recipesRecyclerView;
    @BindView(R.id.loading_indicator) ProgressBar loadingIndicator;
    @BindView(R.id.empty_view) TextView emptyStateTextView;
    private BakingVariables bakingVariables = new BakingVariables();
    private ArrayList<Recipe> recipes;
    private OnRecipeClickListener mCallback;
    private boolean isTabletBoolean;


    //An interface to send data to Activity
    public interface OnRecipeClickListener {
        void onRecipeSelected(Recipe recipe);
    }


    //Empty Constructor
    public RecipeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the RecipeFragment
        View rootView = inflater.inflate(R.layout.recipes_fragment,container,false);
        ButterKnife.bind(this,rootView);

        //Making the loadingIndicator visible
        loadingIndicator.setVisibility(View.VISIBLE);
        emptyStateTextView.setVisibility(View.GONE);

        //Setting the layout manager for the the RecyclerView
        if(isTabletBoolean){
            recipesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
            Log.d("TAG", "onCreateView: tablet");
        }else {
            recipesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            Log.d("TAG", "onCreateView: mobile");
        }

        //Checking Connectivity for connecting to get data
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            //Initializing the Recipe Loader
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BakingVariables.RECIPE_LOADER_ID,null,this);
        }else{
            loadingIndicator.setVisibility(GONE);
            emptyStateTextView.setVisibility(View.VISIBLE);
            emptyStateTextView.setText(getString(R.string.no_internet_connection));
            Toast.makeText(getActivity(), "No Internet Conncetion", Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //This makes sure that the host activity implemented the click listener
        try{
            mCallback = (OnRecipeClickListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnRecipeClickListener");
        }
    }

    @Override
    public void onClickItemList(int clickItemIndex) {
        //Communicating the index of the item clicked in the recyclerView to the MainActivity
        Recipe recipe = recipes.get(clickItemIndex);
        Log.d("TAG", "onClickItemList: "+ recipe.getName());
        mCallback.onRecipeSelected(recipe);
    }

    @NonNull
    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int id, @Nullable Bundle args) {
        return new RecipeLoader(Objects.requireNonNull(getContext()), bakingVariables.recipeUrl);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> data) {
        //Handling data once recieved
        if (data != null && !data.isEmpty()) {
            recipes = data;
            recipesRecyclerView.setVisibility(View.VISIBLE);
            RecipeRVAdapter recipeRVAdapter = new RecipeRVAdapter(data,this);
            recipesRecyclerView.setAdapter(recipeRVAdapter);
        }else if(data == null || data.isEmpty()){
            emptyStateTextView.setText(R.string.no_recipes);
            emptyStateTextView.setVisibility(View.VISIBLE);
            recipesRecyclerView.setVisibility(GONE);
            loadingIndicator.setVisibility(GONE);
        }
        loadingIndicator.setVisibility(GONE);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Recipe>> loader) {
        //Setting the adapter to Null when the loader is reset.
        recipesRecyclerView.setAdapter(null);
    }

    public void isTablet(boolean isTablet){
        isTabletBoolean = isTablet;
    }
}
