package com.example.baking.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.baking.POJOs.Ingredient;
import com.example.baking.POJOs.Step;
import com.example.baking.R;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailRVAdapter extends RecyclerView.Adapter<RecipeDetailRVAdapter.RecipeDetailViewHolder> {

    //Declaring Global Variables
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    final private ListItemClickListener listItemClickListener;

    //Defining an interface for the ClickListener
    public interface ListItemClickListener{
        void onClickItemList(int clickItemIndex);
    }

    //Constructor of the Adapter
    public RecipeDetailRVAdapter(ArrayList<Ingredient> ingredients, ArrayList<Step> steps, ListItemClickListener listItemClickListener) {
        this.ingredients = ingredients;
        this.steps = steps;
        this.listItemClickListener = listItemClickListener;
    }

    @NonNull
    @Override
    public RecipeDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflating the Activity
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_detail_item,parent,false);
        return new RecipeDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailViewHolder holder, int position) {
        //Populating the RecyclerView with Data
        if(position == 0){
            holder.detailTextView.setText(R.string.ingredients);
            holder.videoAvailableTextView.setVisibility(View.GONE);
            holder.videoIconImageView.setVisibility(View.GONE);
        }else{
            holder.detailTextView.setText(steps.get(position-1).getShortDescription());
            if(steps.get(position-1).getThumbnailURL().isEmpty() && steps.get(position-1).getVideoURL().isEmpty()){
                holder.videoAvailableTextView.setVisibility(View.GONE);
                holder.videoIconImageView.setVisibility(View.GONE);
            }else{
                holder.videoAvailableTextView.setVisibility(View.VISIBLE);
                holder.videoIconImageView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return steps.size() + 1;
    }


    //ViewHolder of the RecyclerView
    public class RecipeDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Initializing Views using ButterKnife
        @BindView(R.id.detailTextView) TextView detailTextView;
        @BindView(R.id.videoAvailableTextView) TextView videoAvailableTextView;
        @BindView(R.id.videoIconImageView) ImageView videoIconImageView;

        public RecipeDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void onClick(View v) {
            //ClickListener of the RecyclerView
            listItemClickListener.onClickItemList(getAdapterPosition());
        }
    }
}
