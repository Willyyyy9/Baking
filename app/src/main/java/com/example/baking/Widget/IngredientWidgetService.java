package com.example.baking.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.baking.POJOs.Ingredient;
import com.example.baking.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class IngredientWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientWidgetItemFactory(getApplicationContext(), intent);
    }

    class IngredientWidgetItemFactory implements RemoteViewsFactory {
        private Context context;
        private int appWidgetId;
        private ArrayList<Ingredient> ingredientArrayList;
        private String title;


        IngredientWidgetItemFactory(Context context, Intent intent){
            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
            SharedPreferences sharedPreferences = getSharedPreferences("File",MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("ingredients", "");
            Type type = new TypeToken<ArrayList<Ingredient>>() {}.getType();
            ingredientArrayList = gson.fromJson(json,type);
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredientArrayList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_item);
            String quantityTextViewText = ingredientArrayList.get(position).getQuantity() + " " + ingredientArrayList.get(position).getMeasure();
            views.setTextViewText(R.id.quantityWidgetTextView, quantityTextViewText);
            views.setTextViewText(R.id.ingredientWidgetTextView, ingredientArrayList.get(position).getIngredient());
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
