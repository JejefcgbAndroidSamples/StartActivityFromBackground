package com.jejefcgb.android;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class ChildActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        Snackbar.make(fab, "Click on the button to stop the activity.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                synchronized (MainActivity.LOCK) {
                    MainActivity.LOCK.setCondition(true);
                    MainActivity.LOCK.notifyAll();
                }

                finish();
            }
        });

        Toast.makeText(ChildActivity.this, "Activity Started From Background", Toast.LENGTH_SHORT).show();
    }

}
