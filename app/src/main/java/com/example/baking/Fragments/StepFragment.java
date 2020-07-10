package com.example.baking.Fragments;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.baking.POJOs.Step;
import com.example.baking.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Objects;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import static android.view.View.GONE;

public class StepFragment extends Fragment {
    //Declaration of Views
    @BindView(R.id.stepMediaPlayer) SimpleExoPlayerView stepPlayerView;
    @BindView(R.id.shortDescriptionTextView) TextView shortDescriptionTextView;
    @BindView(R.id.descriptionTextView) TextView descriptionTextView;
    @BindView(R.id.textLinearLayout) LinearLayout textLinearLayout;
    @BindView(R.id.buttonsLinearLayout) ConstraintLayout buttonsLinearLayout;
    @BindView(R.id.nextButton) Button nextButton;
    @BindView(R.id.previousButton) Button previousButton;

    //Global Variables
    private ArrayList<Step> steps;
    private String videoURL = "";
    private int stepPosition;
    private SimpleExoPlayer simpleExoPlayer;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private boolean isTabletBoolean;
    private boolean isLandscape;


    //Public Constructor
    public StepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_fragment,container,false);
        ButterKnife.bind(this,rootView);

        //Populating views with data
        shortDescriptionTextView.setText(steps.get(stepPosition).getShortDescription());
        descriptionTextView.setText(steps.get(stepPosition).getDescription());

        //If Device is tablet
        if(isTabletBoolean){
            textLinearLayout.setVisibility(View.VISIBLE);
            buttonsLinearLayout.setVisibility(GONE);
        } else {
            //If Device is mobile
            if(isLandscape){
                buttonsLinearLayout.setVisibility(GONE);
                textLinearLayout.setVisibility(GONE);
                Log.d("TAG", "setLandscapeMode: landscape");
            }else {
                buttonsLinearLayout.setVisibility(View.VISIBLE);
                textLinearLayout.setVisibility(View.VISIBLE);
                Log.d("TAG", "setLandscapeMode: portrait");
            }
        }

        //OnClickListener for nextButton
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((stepPosition+1) == steps.size()){
                    Toast.makeText(getContext(), "That is the last step", Toast.LENGTH_SHORT).show();
                }else {
                    if(simpleExoPlayer != null){
                        releasePlayer();
                        playWhenReady = true;
                        currentWindow = 0;
                        playbackPosition = 0;
                    }
                    stepPosition++;
                    initializePlayer();
                    shortDescriptionTextView.setText(steps.get(stepPosition).getShortDescription());
                    descriptionTextView.setText(steps.get(stepPosition).getDescription());
                }
            }
        });

        //OnClickListener for previousButton
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((stepPosition-1) < 0){
                    Toast.makeText(getContext(), "That is the first step", Toast.LENGTH_SHORT).show();
                }else {
                    if(simpleExoPlayer != null){
                        releasePlayer();
                        playWhenReady = true;
                        currentWindow = 0;
                        playbackPosition = 0;
                    }
                    stepPosition--;
                    initializePlayer();
                    shortDescriptionTextView.setText(steps.get(stepPosition).getShortDescription());
                    descriptionTextView.setText(steps.get(stepPosition).getDescription());
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    private void initializePlayer() {
        //Getting the videoURL that will be shown in the mediaPlayerView
        String video = steps.get(stepPosition).getVideoURL();
        String thumbnail = steps.get(stepPosition).getThumbnailURL();
        if(video.length() == 0 && thumbnail.length() != 0){
            videoURL = thumbnail;
        }else if(video.length() != 0 && thumbnail.length() == 0){
            videoURL = video;
        }else if (video.isEmpty() && thumbnail.isEmpty()) {
            videoURL = "";
        }
        if(videoURL.isEmpty()){
            //If there is no link, then the playerView will not be showing
            stepPlayerView.setVisibility(GONE);
        }else{
            //If there is a link, playerView will stream the video
            stepPlayerView.setVisibility(View.VISIBLE);
            if (simpleExoPlayer == null) {
                simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(Objects.requireNonNull(getContext()));
                stepPlayerView.setPlayer(simpleExoPlayer);
                Uri uri = Uri.parse(videoURL);
                MediaSource mediaSource = buildMediaSource(uri);
                simpleExoPlayer.setPlayWhenReady(playWhenReady);
                simpleExoPlayer.seekTo(currentWindow, playbackPosition);
                simpleExoPlayer.prepare(mediaSource, false, false);
            }
        }
    }

    private MediaSource buildMediaSource (Uri uri){
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(Objects.requireNonNull(getContext()), "exoplayer-codelab");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
        }

    private void releasePlayer () {
        if (simpleExoPlayer != null) {
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            playbackPosition = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    public void setSteps(int position, ArrayList<Step> stepArrayList){
        //Setters for the ArrayList<Step> and the position
        steps = stepArrayList;
        stepPosition = position;
    }

    public void setLandscapeMode(Boolean state){
        //Checks if device is in landscape or portrait mode
        if(isTabletBoolean){
            //Nothing to be done
        }else {
            if(state){
                buttonsLinearLayout.setVisibility(GONE);
                textLinearLayout.setVisibility(GONE);
                Log.d("TAG", "setLandscapeMode: landscape");
            }else {
                buttonsLinearLayout.setVisibility(View.VISIBLE);
                textLinearLayout.setVisibility(View.VISIBLE);
                Log.d("TAG", "setLandscapeMode: portrait");
            }
        }
    }

    public void isLandscape(boolean state){
        isLandscape = state;
    }

    public void isTablet(boolean isTablet){
        //Checking if device is tablet
        isTabletBoolean = isTablet;
    }
    }
