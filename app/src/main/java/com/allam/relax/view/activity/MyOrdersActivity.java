package com.allam.relax.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.allam.relax.R;
import com.allam.relax.view.NavigationDrawer;

public class MyOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.myorders_activity);
        setSupportActionBar(toolbar);

        NavigationDrawer.getDrawer(this, toolbar, NavigationDrawer.myOrdersID);


    }
}
