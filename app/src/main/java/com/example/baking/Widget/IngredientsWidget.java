package com.example.baking.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.baking.Activities.MainActivity;
import com.example.baking.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidget extends AppWidgetProvider {
    private static String title;
    SharedPreferences sharedPreferences;


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {




        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent serviceIntent = new Intent(context,IngredientWidgetService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

        views.setOnClickPendingIntent(R.id.recipeWidgetTextView,pendingIntent);
        views.setRemoteAdapter(R.id.ingredientStackViewView,serviceIntent);
        views.setEmptyView(R.id.ingredientStackViewView, R.id.widgetEmptyView);
        views.setTextViewText(R.id.recipeWidgetTextView,title);



        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

            Intent serviceIntent = new Intent(context,IngredientWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
            views.setOnClickPendingIntent(R.id.recipeWidgetTextView,pendingIntent);
            views.setRemoteAdapter(R.id.ingredientStackViewView,serviceIntent);
            views.setEmptyView(R.id.ingredientStackViewView, R.id.widgetEmptyView);
            views.setTextViewText(R.id.recipeWidgetTextView,title);
        }


    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        SharedPreferences sharedPreferences = context.getSharedPreferences("File",MODE_PRIVATE);
        title = sharedPreferences.getString("name","Recipe");
        Log.d("TAG", "onEnabled: " + title);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

