/*
 * Copyright (C) 2016 Andrew Lord
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and limitations under the License.
 */

package com.github.andrewlord1990.materialandroidsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.github.andrewlord1990.materialandroidsample.color.ColorChooserDialog;
import com.github.andrewlord1990.materialandroidsample.color.ColorSampleActivity;
import com.github.andrewlord1990.materialandroidsample.color.Colors;

import java.util.ArrayList;

public class SampleActivity extends AppCompatActivity implements ColorChooserDialog.Listener {

    private static final int REQUEST_PRIMARY_COLOR = 0;
    private static final int REQUEST_ACCENT_COLOR = 1;

    private View primaryColorSquare;
    private View accentColorSquare;

    private static int primaryColor;
    private static int accentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        primaryColor = ContextCompat.getColor(this, R.color.md_cyan_500);
        accentColor = ContextCompat.getColor(this, R.color.md_red_a200);

        setContentView(R.layout.activity_sample);

        setupToolbar();
        setupFab();

        setupColorSample();
    }

    private void setupFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupColorSample() {
        primaryColorSquare = findViewById(R.id.primary_color_square);
        if (primaryColorSquare != null) {
            refreshPrimaryColorSquare();
            primaryColorSquare.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    showColorChooser(
                            REQUEST_PRIMARY_COLOR,
                            R.string.sample_primary_color,
                            Colors.getPrimaryColors(v.getContext()));
                }
            });
        }
        accentColorSquare = findViewById(R.id.accent_color_square);
        refreshAccentColorSquare();
        accentColorSquare.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorChooser(
                        REQUEST_ACCENT_COLOR,
                        R.string.sample_accent_color,
                        Colors.getAccentColors(v.getContext()));
            }
        });
        Button colorSample = (Button) findViewById(R.id.color_sample_button);
        if (colorSample != null) {
            colorSample.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onColorSampleSelected();
                }
            });
        }
    }

    private void showColorChooser(int requestCode, @StringRes int title, ArrayList<Integer> colors) {
        Bundle args = new Bundle();
        args.putInt(ColorChooserDialog.ARG_REQUEST_CODE, requestCode);
        args.putString(ColorChooserDialog.ARG_TITLE, getString(title));
        args.putIntegerArrayList(ColorChooserDialog.ARG_COLORS, colors);
        ColorChooserDialog dialog = new ColorChooserDialog();
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), ColorChooserDialog.TAG);
    }

    private void onColorSampleSelected() {
        Intent intent = new Intent(this, ColorSampleActivity.class);
        intent.putExtra(ColorSampleActivity.EXTRA_PRIMARY_COLOR, primaryColor);
        intent.putExtra(ColorSampleActivity.EXTRA_ACCENT_COLOR, accentColor);
        startActivity(intent);
    }

    @Override
    public void onColorSelected(int requestCode, int color) {
        switch (requestCode) {
            case REQUEST_PRIMARY_COLOR:
                primaryColor = color;
                refreshPrimaryColorSquare();
                break;
            case REQUEST_ACCENT_COLOR:
                accentColor = color;
                refreshAccentColorSquare();
                break;
        }
    }

    private void refreshPrimaryColorSquare() {
        primaryColorSquare.setBackgroundColor(primaryColor);
    }

    private void refreshAccentColorSquare() {
        accentColorSquare.setBackgroundColor(accentColor);
    }
}
