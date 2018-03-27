/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent numbersIntent;
    private Intent familyIntent;
    private Intent phrasesIntent;
    private Intent colorsIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);


        // Find all textViews
        TextView numbersTv = (TextView) findViewById(R.id.numbers);
        TextView familyTv = (TextView) findViewById(R.id.family);
        TextView phrasesTv = (TextView) findViewById(R.id.phrases);
        TextView colorsTv = (TextView) findViewById(R.id.colors);

        // Set Event listeners on View objects
        numbersTv.setOnClickListener(this);
        familyTv.setOnClickListener(this);
        phrasesTv.setOnClickListener(this);
        colorsTv.setOnClickListener(this);

        //Create Intents for activities
        numbersIntent = new Intent(MainActivity.this, NumbersActivity.class);
        familyIntent = new Intent(MainActivity.this, FamilyMembersActivity.class);
        phrasesIntent = new Intent(MainActivity.this, PhrasesActivity.class);
        colorsIntent = new Intent(MainActivity.this, ColorsActivity.class);

    }

    @Override
    public void onClick(View view) {
        // Perform action on click
        switch(view.getId()) {
            case R.id.numbers:
                startActivity(numbersIntent);
                break;
            case R.id.family:
                startActivity(familyIntent);
                break;

            case R.id.phrases:
                startActivity(phrasesIntent);
                break;

            case R.id.colors:
                startActivity(colorsIntent);
                break;
        }
    }
}
