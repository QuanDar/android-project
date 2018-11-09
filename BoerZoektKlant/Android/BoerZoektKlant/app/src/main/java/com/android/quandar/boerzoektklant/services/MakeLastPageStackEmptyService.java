package com.android.quandar.boerzoektklant.services;

import android.app.Activity;
import android.content.SharedPreferences;

public class MakeLastPageStackEmptyService extends Activity {
    public void makeEmpty(){
        SharedPreferences prefs = getSharedPreferences("LastPageStack", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("farmerActivity", null);
        editor.putString("farmerListActivity", null);
    }
}
