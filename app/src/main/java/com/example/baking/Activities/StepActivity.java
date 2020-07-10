package com.example.baking.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Html;
import android.util.Log;
import android.view.View;

import com.example.baking.Fragments.StepFragment;
import com.example.baking.POJOs.Step;
import com.example.baking.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

public class StepActivity extends AppCompatActivity {
    //Declaring global Variables
    private StepFragment stepFragment;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        //Setting title with custom textColor.
        getSupportActionBar().setTitle((Html.fromHtml("<font color=\""+getColor(R.color.textColor)+"\">"+ getString(R.string.steps)+"</font>")));

        //Getting data from intent
        Intent intent = getIntent();
        ArrayList<Step> steps = intent.getParcelableArrayListExtra("Steps");
        int position = intent.getIntExtra("Position", 0);

        //Setting up the fragment to show it.
        stepFragment = new StepFragment();
        stepFragment.setSteps(position, steps);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.stepFragmentContainer,stepFragment)
                .commit();

        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            //Code for Portrait Mode
            stepFragment.isLandscape(false);
        } else {
            //Code for Landscape Mode
            stepFragment.isLandscape(true);
        }
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            stepFragment.setLandscapeMode(true);
        }else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            stepFragment.setLandscapeMode(false);
        }
    }

}