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

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        final ArrayList<ListItemContent> numbersListItemContents = new ArrayList<ListItemContent>();

        numbersListItemContents.add(new ListItemContent("one", "lutti", R.drawable.number_one, R.raw.number_one));
        numbersListItemContents.add(new ListItemContent("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        numbersListItemContents.add(new ListItemContent("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        numbersListItemContents.add(new ListItemContent("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        numbersListItemContents.add(new ListItemContent("five", "massokka", R.drawable.number_five, R.raw.number_five));
        numbersListItemContents.add(new ListItemContent("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        numbersListItemContents.add(new ListItemContent("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        numbersListItemContents.add(new ListItemContent("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        numbersListItemContents.add(new ListItemContent("nine", "wo’e", R.drawable.number_nine, R.raw.number_nine));
        numbersListItemContents.add(new ListItemContent("ten", "na’aacha", R.drawable.number_ten, R.raw.number_ten));

        ListItemAdapter<ListItemContent> numbersItemsAdapter =
                new ListItemAdapter<ListItemContent>(this, numbersListItemContents, R.color.category_numbers);

        ListView numbersListView = (ListView) findViewById(R.id.numbersListView);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        numbersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int soundResId = numbersListItemContents.get(i).getSoundResId();

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

        numbersListView.setAdapter(numbersItemsAdapter);

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
