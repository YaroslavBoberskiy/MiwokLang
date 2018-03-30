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

public class PhrasesActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);

        releaseMediaPlayer();

        final ArrayList<ListItemContent> phrasesListItemContents = new ArrayList<ListItemContent>();

        phrasesListItemContents.add(new ListItemContent("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        phrasesListItemContents.add(new ListItemContent("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        phrasesListItemContents.add(new ListItemContent("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        phrasesListItemContents.add(new ListItemContent("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        phrasesListItemContents.add(new ListItemContent("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        phrasesListItemContents.add(new ListItemContent("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        phrasesListItemContents.add(new ListItemContent("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        phrasesListItemContents.add(new ListItemContent("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        phrasesListItemContents.add(new ListItemContent("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        phrasesListItemContents.add(new ListItemContent("Come here.", "әnni'nem", R.raw.phrase_come_here));

        ListItemAdapter<ListItemContent> phrasesWordsAdapter = new ListItemAdapter<ListItemContent>(this, phrasesListItemContents, R.color.category_phrases);

        ListView phrasesListView = (ListView) findViewById(R.id.phrasesListView);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        phrasesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int soundResId = phrasesListItemContents.get(i).getSoundResId();

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

        phrasesListView.setAdapter(phrasesWordsAdapter);

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
