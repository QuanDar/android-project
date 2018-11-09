package com.android.quandar.boerzoektklant.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.quandar.boerzoektklant.R;
import com.android.quandar.boerzoektklant.models.Favorite;
import com.android.quandar.boerzoektklant.viewModels.FavoriteViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import static com.android.quandar.boerzoektklant.activities.Dispatcher.ITEM_DETAIL_ACTIVITY;
import static com.android.quandar.boerzoektklant.activities.Dispatcher.ITEM_LIST_ACTIVITY;
import static com.android.quandar.boerzoektklant.activities.Dispatcher.LAST_PAGE_STACK;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ItemDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private double latitude = 50.938207;
    private double longitude = 5.348064;
    private String farmerMarker = "De ultimate Vleesboer";
    private FavoriteViewModel favoriteViewModel;
    public static final String SHARED_PREFS = "sharedPrefs";
    private static final String ADDRESS_TEXT = "addressText";
    private static final String TITLE_TEXT = "titleText";
    private static final String RATING_NUMBER = "ratingNumber";
    private static final String DESCRIPTION_TEXT = "descriptionText";
    private static final String FARMER_IMAGE_URL = "farmerImageUrl";
    private static final String PHONE_NUMBER_TEXT = "phoneNumberText";
    private static final String HOUSE_NUMBER = "houseNumberText";
    public static final String LOAD_DATA = "loadData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.famer_item_detail_smartphone);

        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(LOAD_DATA, false) == true) {
            loadData();
        } else {
            if (savedInstanceState == null) {
                // Create the detail fragment and add it to the activity
                // using a fragment transaction.
                Bundle arguments = new Bundle();
                if (getIntent().getStringExtra(ItemDetailFragment.BUSINESS_ITEM_TITLE) != null){
                    arguments.putString(ItemDetailFragment.BUSINESS_ITEM_TITLE,
                            getIntent().getStringExtra(ItemDetailFragment.BUSINESS_ITEM_TITLE));
                    arguments.putString(ItemDetailFragment.BUSINESS_ITEM_DESCRIPTION,
                            getIntent().getStringExtra(ItemDetailFragment.BUSINESS_ITEM_DESCRIPTION));
                    arguments.putString(ItemDetailFragment.BUSINESS_ITEM_ADDRESS,
                            getIntent().getStringExtra(ItemDetailFragment.BUSINESS_ITEM_ADDRESS));
                    arguments.putString(ItemDetailFragment.BUSINESS_ITEM_PHONE_NUMBER,
                            getIntent().getStringExtra(ItemDetailFragment.BUSINESS_ITEM_PHONE_NUMBER));
                    arguments.putString(ItemDetailFragment.BUSINESS_ITEM_HOUSE_NUMBER,
                            getIntent().getStringExtra(ItemDetailFragment.BUSINESS_ITEM_HOUSE_NUMBER));
                    arguments.putFloat(ItemDetailFragment.BUSINESS_ITEM_RATING,
                            Float.parseFloat(getIntent().getStringExtra(ItemDetailFragment.BUSINESS_ITEM_RATING)));
                    arguments.putString(ItemDetailFragment.BUSINESS_ITEM_IMAGE_URL,
                            getIntent().getStringExtra(ItemDetailFragment.BUSINESS_ITEM_IMAGE_URL));
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.item_detail_container, fragment)
                            .commit();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(location).title(farmerMarker));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
    }

    public void openMapsWithCurrentLocation(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:" + latitude + "," + longitude + "?q=Hasselt, elfde-Liniestraat 23")); //lat lng or address query
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void addToFavoritesClick(View view) {
        String title = getIntent().getStringExtra(ItemDetailFragment.BUSINESS_ITEM_TITLE);
        String description = getIntent().getStringExtra(ItemDetailFragment.BUSINESS_ITEM_DESCRIPTION);
        float rating = Float.parseFloat(getIntent().getStringExtra(ItemDetailFragment.BUSINESS_ITEM_RATING));

        Favorite favorite = new Favorite(title, description, rating);
        favoriteViewModel.insert(favorite);

        Toast.makeText(this, "Favorite saved", Toast.LENGTH_SHORT).show();
    }

    public void backButtonClick(View view) {
        SharedPreferences prefs = getSharedPreferences(LAST_PAGE_STACK, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ITEM_LIST_ACTIVITY, true);
        editor.putBoolean(ITEM_DETAIL_ACTIVITY, false);
        editor.apply();

        this.startActivity(new Intent(ItemDetailActivity.this, ItemListActivity.class));
        return;
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (getIntent().getStringExtra(ItemDetailFragment.BUSINESS_ITEM_TITLE) != null) {
            editor.putString(DESCRIPTION_TEXT, getIntent().getStringExtra(ItemDetailFragment.BUSINESS_ITEM_DESCRIPTION));
            editor.putString(TITLE_TEXT, getIntent().getStringExtra(ItemDetailFragment.BUSINESS_ITEM_TITLE));
            editor.putString(PHONE_NUMBER_TEXT, getIntent().getStringExtra(ItemDetailFragment.BUSINESS_ITEM_PHONE_NUMBER));
            editor.putString(ADDRESS_TEXT, getIntent().getStringExtra(ItemDetailFragment.BUSINESS_ITEM_ADDRESS));
            editor.putString(HOUSE_NUMBER, getIntent().getStringExtra(ItemDetailFragment.BUSINESS_ITEM_HOUSE_NUMBER));
            editor.putFloat(RATING_NUMBER, Float.parseFloat(getIntent().getStringExtra(ItemDetailFragment.BUSINESS_ITEM_RATING)));
            editor.putString(FARMER_IMAGE_URL, getIntent().getStringExtra(ItemDetailFragment.BUSINESS_ITEM_IMAGE_URL));
            editor.putBoolean(LOAD_DATA, true);
            //editor.putString(FARMER_IMAGE, farmerImageUrl.toString());
            editor.apply();
            savePage();
        }
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        Bundle arguments = new Bundle();
        arguments.putString(ItemDetailFragment.BUSINESS_ITEM_TITLE,
                sharedPreferences.getString(TITLE_TEXT, "No title available "));
        arguments.putString(ItemDetailFragment.BUSINESS_ITEM_DESCRIPTION,
                sharedPreferences.getString(DESCRIPTION_TEXT, "No description "));
        arguments.putString(ItemDetailFragment.BUSINESS_ITEM_ADDRESS,
                sharedPreferences.getString(ADDRESS_TEXT, "no address "));
        arguments.putString(ItemDetailFragment.BUSINESS_ITEM_PHONE_NUMBER,
                sharedPreferences.getString(PHONE_NUMBER_TEXT, "no phone "));
        arguments.putString(ItemDetailFragment.BUSINESS_ITEM_HOUSE_NUMBER,
                sharedPreferences.getString(HOUSE_NUMBER, "no hn "));
        arguments.putFloat(ItemDetailFragment.BUSINESS_ITEM_RATING,
                sharedPreferences.getFloat(RATING_NUMBER, 1F));
        arguments.putString(ItemDetailFragment.BUSINESS_ITEM_IMAGE_URL,
                sharedPreferences.getString(FARMER_IMAGE_URL, ""));
        ItemDetailFragment fragment = new ItemDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.item_detail_container, fragment)
                .commit();
    }

    public void savePage() {
        SharedPreferences prefs = getSharedPreferences(LAST_PAGE_STACK, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ITEM_LIST_ACTIVITY, false);
        editor.putBoolean(ITEM_DETAIL_ACTIVITY, true);
        editor.apply();
    }
}
