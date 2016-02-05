package com.jejefcgb.android;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        Snackbar.make(fab, "Click on the button to start the child activity", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Task t = new Task(MainActivity.this);
                t.execute("");
            }
        });



    }

    public static class Lock {
        private boolean condition;
        public boolean conditionMet() {
            return condition;
        }
        public void setCondition(boolean condition) {
            this.condition = condition;
        }
    }

    public static final Lock LOCK = new Lock();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

class Task extends AsyncTask<String, Void, Boolean> {

    private Activity activity;

    public Task(Activity activity){
        Log.i("TASK","NEW TASK");
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i("TAG","ONPREEX");
    }

    @Override
    protected Boolean doInBackground(String... params) {
        Log.i("TAG","DO IN BACKGROUND");

        Intent i = new Intent(activity, ChildActivity.class);
        activity.startActivity(i);
        synchronized (MainActivity.LOCK) {
            while (!MainActivity.LOCK.conditionMet()) {
                try {
                    MainActivity.LOCK.wait();
                } catch (InterruptedException e) {
                    Log.e("TAG", "Exception when waiting for condition", e);
                    return false;
                }
            }
        }
        Log.i("TAG","AFTER SYNCHRONIZED");
        return true;

    }

    @Override
    protected void onPostExecute(Boolean aVoid) {
        super.onPostExecute(aVoid);
        Log.i("TAG","OnPostExecute");
    }
}
