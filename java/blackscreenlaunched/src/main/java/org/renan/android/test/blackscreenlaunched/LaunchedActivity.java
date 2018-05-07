package org.renan.android.test.blackscreenlaunched;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LaunchedActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transparent);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        TextView text = (TextView) findViewById(R.id.transText);
        text.setText("Yo dude");
        Intent intent = getIntent();
        final String extra =  intent.getStringExtra("anExtraStringValue");
        if (null != extra)
            text.setText("Yo dude : " + extra);
        final Button return_btn = (Button) findViewById(R.id.return_btn);
        return_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String r = "";
                Integer i = null;
                if (null != extra)
                {
                    i = Integer.valueOf(extra)*2;
                    r = i.toString();
                }
                Toast.makeText(view.getContext(),"Returning result…",Toast.LENGTH_SHORT).show();
                setResult(21, getIntent().putExtra("theStringResult",r));
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launched, menu);
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
}
