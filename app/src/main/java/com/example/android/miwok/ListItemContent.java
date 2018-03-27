package com.example.android.miwok;

/**
 * ListItemContent class which provides content for custom ListItem in listView
 */

public class ListItemContent {

    /** Original Miwok language word  */
    private String miwokWord;

    /** Translated word into your native language word  */
    private String translatedWord;

    /** Image in list item */
    private int imageId;

    /** Sound of list item */
    private int soundResId;

    public ListItemContent(String translatedWord, String miwokWord, int imageId, int soundResId) {
        this.miwokWord = miwokWord;
        this.translatedWord = translatedWord;
        this.imageId = imageId;
        this.soundResId = soundResId;
    }

    public ListItemContent(String translatedWord, String miwokWord, int soundResId) {
        this.miwokWord = miwokWord;
        this.translatedWord = translatedWord;
        this.soundResId = soundResId;
    }

    public String getMiwokWord() {
        return miwokWord;
    }

    public String getTranslatedWord() {
        return translatedWord;
    }

    public int getImageId() {
        return imageId;
    }

    public int getSoundResId() {
        return soundResId;
    }
}
