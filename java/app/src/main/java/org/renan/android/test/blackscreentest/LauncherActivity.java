package org.renan.android.test.blackscreentest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LauncherActivity extends AppCompatActivity
{

    private String aValue = "42";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        TextView text = (TextView) findViewById(R.id.aText);
        text.setText("Hello - " + aValue);

        final Button start_btn = (Button) findViewById(R.id.start_activity);
        start_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Starting activity…",Toast.LENGTH_SHORT).show();
                doStartActivity();
            }
        });
    }
    public static final String CUSTOM_INTENT = "org.renan.android.dev.blackscreentest.intent.action.TEST";
    public void doStartActivity()
    {
        Intent i = new Intent();
        i.setAction(CUSTOM_INTENT);
        i.putExtra("anExtraStringValue", aValue);
        startActivityForResult(i, 1234);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launcher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234)
        {
            Log.i("result", "onActivityResult: "+resultCode);
            aValue = data.getStringExtra("theStringResult");
            TextView text = (TextView) findViewById(R.id.aText);
            text.setText("Hello - " + aValue);
        }
    }
}
