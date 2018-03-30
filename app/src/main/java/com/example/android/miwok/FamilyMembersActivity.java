package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyMembersActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_members);

        releaseMediaPlayer();

        final ArrayList<ListItemContent> familyListItemContents = new ArrayList<ListItemContent>();

        familyListItemContents.add(new ListItemContent("father", "әpә", R.drawable.family_father, R.raw.family_father));
        familyListItemContents.add(new ListItemContent("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        familyListItemContents.add(new ListItemContent("son", "angsi", R.drawable.family_son, R.raw.family_son));
        familyListItemContents.add(new ListItemContent("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        familyListItemContents.add(new ListItemContent("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        familyListItemContents.add(new ListItemContent("young brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        familyListItemContents.add(new ListItemContent("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        familyListItemContents.add(new ListItemContent("young sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        familyListItemContents.add(new ListItemContent("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        familyListItemContents.add(new ListItemContent("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));


        ListItemAdapter<ListItemContent> familyWordsAdapter = new ListItemAdapter<ListItemContent>(this, familyListItemContents, R.color.category_family);

        ListView familyListView = (ListView) findViewById(R.id.familyListView);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        familyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int soundResId = familyListItemContents.get(i).getSoundResId();

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

        familyListView.setAdapter(familyWordsAdapter);

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