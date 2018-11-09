package com.android.quandar.boerzoektklant.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.android.quandar.boerzoektklant.R;

public class Dispatcher extends AppCompatActivity {
    public static final String ITEM_DETAIL_ACTIVITY = "itemDetailActivity";
    public static final String ITEM_LIST_ACTIVITY = "itemListActivity";
    public static final String LAST_PAGE_STACK = "LastPageStack";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Class<?> activityClass;

        try {
            SharedPreferences prefs = getSharedPreferences(LAST_PAGE_STACK, MODE_PRIVATE);
            Boolean itemDetailActivity = prefs.getBoolean(ITEM_DETAIL_ACTIVITY, false);
            Boolean itemListActivity = prefs.getBoolean(ITEM_LIST_ACTIVITY, true);

            if (itemListActivity) {
                activityClass = Class.forName(ItemListActivity.class.getName());
                startActivity(new Intent(this, activityClass));
            } else {
                activityClass = Class.forName(ItemDetailActivity.class.getName());
                startActivity(new Intent(this, activityClass));
            }
        } catch(ClassNotFoundException ex) {
            activityClass = ItemListActivity.class;
        }

    }


}
