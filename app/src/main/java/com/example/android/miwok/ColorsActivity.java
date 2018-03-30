package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        releaseMediaPlayer();

        final ArrayList<ListItemContent> colorsListItemContents = new ArrayList<ListItemContent>();

        colorsListItemContents.add(new ListItemContent("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        colorsListItemContents.add(new ListItemContent("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        colorsListItemContents.add(new ListItemContent("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        colorsListItemContents.add(new ListItemContent("gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        colorsListItemContents.add(new ListItemContent("black", "kululli", R.drawable.color_black, R.raw.color_black));
        colorsListItemContents.add(new ListItemContent("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        colorsListItemContents.add(new ListItemContent("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        colorsListItemContents.add(new ListItemContent("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        ListItemAdapter<ListItemContent> colorsItemsAdapter = new ListItemAdapter<ListItemContent>(this, colorsListItemContents, R.color.category_colors);

        ListView colorsListView = (ListView) findViewById(R.id.colorsListView);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        colorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int soundResId = colorsListItemContents.get(i).getSoundResId();

                releaseMediaPlayer();

                int audioFocusResult = audioManager.requestAudioFocus(
                        audioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (audioFocusResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(view.getContext(), soundResId);
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });

        audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_LOSS:
                        releaseMediaPlayer();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
//                        mediaPlayer.setVolume(0.5f, 0.5f);
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                        break;
                    case AudioManager.AUDIOFOCUS_GAIN:
                        if (!mediaPlayer.isPlaying())
                            mediaPlayer.start();
                        break;
                }
            }
        };

        colorsListView.setAdapter(colorsItemsAdapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }
}
