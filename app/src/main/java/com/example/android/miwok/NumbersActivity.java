package com.example.android.miwok;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        releaseMediaPlayer();

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

        numbersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int soundResId = numbersListItemContents.get(i).getSoundResId();
                mediaPlayer = MediaPlayer.create(view.getContext(), soundResId);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        releaseMediaPlayer();
                    }
                });
            }
        });

        numbersListView.setAdapter(numbersItemsAdapter);

    }

    public void releaseMediaPlayer () {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
