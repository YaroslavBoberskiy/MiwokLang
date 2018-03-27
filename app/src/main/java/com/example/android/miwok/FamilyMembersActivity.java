package com.example.android.miwok;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyMembersActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

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

        ListView familyListViev = (ListView) findViewById(R.id.familyListView);

        familyListViev.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int soundResId = familyListItemContents.get(i).getSoundResId();
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

        familyListViev.setAdapter(familyWordsAdapter);

    }

    public void releaseMediaPlayer () {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
