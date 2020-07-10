package com.example.baking;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;

import com.example.baking.POJOs.Ingredient;
import com.example.baking.POJOs.Recipe;
import com.example.baking.POJOs.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class BakingUtils {

    //Empty Constructor
    private BakingUtils(){
    }

    private static URL createURL(String stringURL){
        URL url = null;
        try{
            url = new URL(stringURL);
        }catch (Exception e){
            Log.e("BakingUtils", "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        //Makes the HTTPConnection
        String jsonResponse = "";
        if(url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("BakingUtils", "Error response code: " + urlConnection.getResponseCode());

            }
        }catch (IOException e) {
            Log.e("BakingUtils", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        //Forms the JSON Object retrieved from the connection
        StringBuilder stringBuilder = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line!=null){
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }



    private static ArrayList<Recipe> extractDataFromJSON(String recipesJSON){

        ArrayList<Recipe> recipes = new ArrayList<>();
        try{
            JSONArray rootArray = new JSONArray(recipesJSON);
            for(int i = 0; i<rootArray.length();i++){

                ArrayList<Ingredient> ingredients = new ArrayList<>();
                ArrayList<Step> steps = new ArrayList<>();
                JSONObject recipe = rootArray.getJSONObject(i);
                String name = recipe.getString("name");

                JSONArray ingredientsJSONArray = recipe.getJSONArray("ingredients");
                //Populating the ingredients ArrayList
                for(int j = 0; j<ingredientsJSONArray.length(); j++){
                    JSONObject ingredient = ingredientsJSONArray.getJSONObject(j);
                    double quantityFromJSON = ingredient.getDouble("quantity");
                    String  quantity = formatQuantity(quantityFromJSON);
                    String measure = clarifyMeasurement(ingredient.getString("measure"));
                    String ingredientComponent = alterString(ingredient.getString("ingredient"));
                    ingredients.add(new Ingredient(quantity,measure,ingredientComponent));
                }

                JSONArray stepsJSONArray = recipe.getJSONArray("steps");
                //Populating the steps Arraylist
                for(int k = 0; k<stepsJSONArray.length(); k++){
                    JSONObject step = stepsJSONArray.getJSONObject(k);
                    String shortDescription = step.getString("shortDescription");
                    String description = step.getString("description");
                    String videoURL = step.getString("videoURL");
                    String thumbnailURL = step.getString("thumbnailURL");
                    steps.add(new Step(shortDescription,description,videoURL,thumbnailURL));
                }
                recipes.add(new Recipe(name,ingredients,steps));
            }
        }catch(JSONException e){
            Log.e("BakingUtils", "Problem parsing the news JSON results", e);
        }
        return recipes;
    }

    private static String clarifyMeasurement(String measure){
        switch(measure){
            case "CUP":
                return "Cup";
            case "TBLSP":
                return "Table Spoon";
            case "TSP":
                return "Tea Spoon";
            case "K":
                return "Knife";
            case "G":
                return "Gram";
            case "OZ":
                return "Ounce";
            case "UNIT":
                return "Unit";
            default:
                return measure;
        }
    }

    private static String alterString(String string){
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static ArrayList<Recipe> fetchRecipeListData(String requestURL){
        //Gets recipes from JSON API
        URL url = createURL(requestURL);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e("BakingUtils", "Problem making the HTTP request.", e);
        }
        return extractDataFromJSON(jsonResponse);
    }

    public static String formatQuantity(double quantity){
        DecimalFormat decimalFormat = new DecimalFormat("###.#");
        return decimalFormat.format(quantity);
    }



}
