package com.gnr.esgi.googlenewsreader.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import com.gnr.esgi.googlenewsreader.GNRApplication;
import com.gnr.esgi.googlenewsreader.R;
import com.gnr.esgi.googlenewsreader.utils.Config;

public class SettingsActivity extends AppCompatActivity {

    CheckBox nighModeCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Config.getTheme());
        setContentView(R.layout.activity_settings);

        nighModeCheckbox = (CheckBox) findViewById(R.id.settings_night_mode_checkbox);
        nighModeCheckbox.setChecked(GNRApplication.getUser().getNightMode());
        nighModeCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GNRApplication.getUser().setNightMode(nighModeCheckbox.isChecked());

                // Restart application after changing skin
                Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
