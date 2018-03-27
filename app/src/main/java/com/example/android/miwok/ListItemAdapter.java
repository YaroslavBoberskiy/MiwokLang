package com.example.android.miwok;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 * Custom ArrayAdapter to process
 */

public class ListItemAdapter<W> extends ArrayAdapter<ListItemContent> {

    public static final int NO_IMAGE_PROVIDED = -1;
    private int colorResId;
//    MediaPlayer mp;
//    private Context currentContext;

    public ListItemAdapter(Context context, List<ListItemContent> objects, int colorResId) {
        super(context, 0, objects);
        this.colorResId = colorResId;
//        this.currentContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).
                    inflate(R.layout.list_item, parent, false);
        }

        final ListItemContent currentListItemContent = getItem(position);

        TextView miwokTV = (TextView) listItemView.findViewById(R.id.originwordTextView);
        TextView translationTV = (TextView) listItemView.findViewById(R.id.translationTextView);
        ImageView imageIV = (ImageView) listItemView.findViewById(R.id.imageImageView);

        if (currentListItemContent.getImageId() == NO_IMAGE_PROVIDED) {
            imageIV.setVisibility(View.GONE);
        }

        imageIV.setImageResource(currentListItemContent.getImageId());
        miwokTV.setText(currentListItemContent.getMiwokWord());
        translationTV.setText(currentListItemContent.getTranslatedWord());
        listItemView.setBackgroundColor(ContextCompat.getColor(getContext(), colorResId));

//        listItemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mp = MediaPlayer.create(currentContext, currentListItemContent.getSoundResId());
//                mp.start();
//            }
//        });

        return listItemView;
    }
}
