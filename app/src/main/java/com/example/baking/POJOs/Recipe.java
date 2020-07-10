package com.example.baking.POJOs;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Parcelable {

    //Global Variables
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;


    //Public Constructor
    public Recipe(String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }


    protected Recipe(Parcel in) {
        name = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        steps = in.createTypedArrayList(Step.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    //Getters
    public String getName() { return name; }

    public ArrayList<Ingredient> getIngredients() { return ingredients; }

    public ArrayList<Step> getSteps() { return steps; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
    }
}
