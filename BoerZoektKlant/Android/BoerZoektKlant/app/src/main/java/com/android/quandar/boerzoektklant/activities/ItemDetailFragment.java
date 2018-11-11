package com.android.quandar.boerzoektklant.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.quandar.boerzoektklant.R;
import com.android.quandar.boerzoektklant.services.BackendUrl;
import com.android.quandar.boerzoektklant.services.DownloadImageTask;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ItemDetailFragment extends Fragment {
    private TextView addressTextView;
    private RatingBar ratingBar;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private ImageView farmerImageUrl;
    private TextView phoneNumberTextView;
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String BUSINESS_ITEM_TITLE = "business_item_title";
    public static final String BUSINESS_ITEM_DESCRIPTION = "business_item_description";
    public static final String BUSINESS_ITEM_PHONE_NUMBER = "business_item_phone_number";
    public static final String BUSINESS_ITEM_ADDRESS = "business_item_address";
    public static final String BUSINESS_ITEM_HOUSE_NUMBER = "business_item_house_number";
    public static final String BUSINESS_ITEM_RATING = "business_item_rating";
    public static final String BUSINESS_ITEM_IMAGE_URL = "business_item_image_url";

    /**
     * The dummy content this fragment is presenting.
     */
    private String mTitle;
    private String mDescription;
    private String mPhone;
    private String mAddress;
    private String mHouseNr;
    private float mRating;
    private String mImageUrl;

    /**
     * For now hardcoded, but should be coming from DB in future.
     */
    MapView mMapView;
    private GoogleMap googleMap;
    private double latitude = 50.938207;
    private double longitude = 5.348064;
    private String farmerMarker = "De ultimate Vleesboer";

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(com.android.quandar.boerzoektklant.activities.ItemDetailActivity.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(com.android.quandar.boerzoektklant.activities.ItemDetailActivity.LOAD_DATA, false);
        editor.putBoolean("itemListActivity", true);
        editor.putBoolean("itemDetailActivity", false);
        editor.apply();

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(BUSINESS_ITEM_TITLE)) {
            if (getArguments().containsKey(BUSINESS_ITEM_TITLE)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                mTitle = getArguments().getString(BUSINESS_ITEM_TITLE);
                mDescription = getArguments().getString(BUSINESS_ITEM_DESCRIPTION);
                mPhone = getArguments().getString(BUSINESS_ITEM_PHONE_NUMBER);
                mAddress = getArguments().getString(BUSINESS_ITEM_ADDRESS);
                mHouseNr = getArguments().getString(BUSINESS_ITEM_HOUSE_NUMBER);
                mRating = getArguments().getFloat(BUSINESS_ITEM_RATING);
                mImageUrl = getArguments().getString(BUSINESS_ITEM_IMAGE_URL);
                setToolBarTitle(mTitle);
            }
        }
    }

    private void setToolBarTitle(String title){
        // sets the toolbar header title
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(title);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.farmer_item_detail_fragment, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(sydney).title(farmerMarker).snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        titleTextView = rootView.findViewById(R.id.titleTextView);
        descriptionTextView = rootView.findViewById(R.id.descriptionTextView);
        phoneNumberTextView = rootView.findViewById(R.id.phoneNumberTextView);
        addressTextView = rootView.findViewById(R.id.addressTextView);
        ratingBar = rootView.findViewById(R.id.ratingBar);
        farmerImageUrl = rootView.findViewById(R.id.farmer_header_image);

        descriptionTextView.setText(mDescription);
        phoneNumberTextView.setText("Telefoonnummer: " + mPhone);
        titleTextView.setText(mTitle);
        addressTextView.setText("Address: " + mAddress + " " + mHouseNr + "\nOpen in Maps");
        ratingBar.setRating(mRating);

        new DownloadImageTask((ImageView) farmerImageUrl)
                .execute(BackendUrl.url + mImageUrl);
        return rootView;
    }


}
