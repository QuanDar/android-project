package com.android.quandar.boerzoektklant.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.quandar.boerzoektklant.R;
import com.android.quandar.boerzoektklant.adapters.BusinessRecyclerAdapter;
import com.android.quandar.boerzoektklant.adapters.RecyclerViewClickListener;
import com.android.quandar.boerzoektklant.services.BackendUrl;
import com.android.quandar.boerzoektklant.models.Business;
import com.android.quandar.boerzoektklant.models.Favorite;
import com.android.quandar.boerzoektklant.viewModels.FavoriteViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.android.quandar.boerzoektklant.activities.Dispatcher.ITEM_DETAIL_ACTIVITY;
import static com.android.quandar.boerzoektklant.activities.Dispatcher.ITEM_LIST_ACTIVITY;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView farmerListView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private double latitude = 50.938207;
    private double longitude = 5.348064;
    private FavoriteViewModel favoriteViewModel;

    // this position var is used for get the right farmer out of the businesses array so that it can be saved in the SQLite db
    private int mPosition = 0;



    private Context mContext;
    // Waiting for child thread message and display parsed out json text in text view.
    private Handler updateUIHandler = null;

    // Message type.
    private final int MESSAGE_SHOW_PARSE_RESULT = 1;

    // Save parsed out json string bundle key.
    private final String KEY_RESULT_DATA = "KEY_RESULT_DATA";

    // Used to access json url page and get response data.
    private static OkHttpClient okHttpClient = null;

    // This is the json file url.
    private String jsonFarmerUrl = BackendUrl.url + "api/Businesses";

    // Log debug or error message tag.
    private static final String TAG_JSON_EXAMPLE = "TAG_JSON_EXAMPLE";
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        SharedPreferences sharedPreferences = getSharedPreferences(com.android.quandar.boerzoektklant.activities.ItemDetailActivity.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(com.android.quandar.boerzoektklant.activities.ItemDetailActivity.LOAD_DATA, false);
        editor.putBoolean(ITEM_LIST_ACTIVITY, true);
        editor.putBoolean(ITEM_DETAIL_ACTIVITY, false);
        editor.apply();


        // SQLite DB
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);

        // setup the menu
        menuSetup();

        initControls();

        // mContext give to adapter
        mContext = this;

        // Create a callback object.
        Callback callback = new Callback() {

            // If read json file url failure.
            @Override
            public void onFailure(Call call, IOException e) {
                sendDisplayParseJsonResultMessage(e.getMessage());
                Log.e(TAG_JSON_EXAMPLE, e.getMessage(), e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                // If read json file url success.
                if (response.isSuccessful()) {
                    // Get json string.
                    String jsonString = response.body().string();

                    // Use GSON to parse json string.
                    //String parsedString = parseJSONWithGSON(jsonString);
                    parseJSONWithGSON(jsonString);

                    // Show parsed out string in text view.
                    //sendDisplayParseJsonResultMessage(parsedString);
                }
            }
        };

        // Send http get request to json page url.
        sendHttpGetRequest(jsonFarmerUrl, callback);
    }

    private void initControls() {
        farmerListView = findViewById(R.id.item_list);
        //ratingTextViewFromDb = findViewById(R.id.rating_from_db);
        if (updateUIHandler == null) {
            // This handler wait for child thread message to update UI.
            // This can avoid update UI in child thread which can cause error.
            updateUIHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // If message means show json parse result.
                    if (msg.what == MESSAGE_SHOW_PARSE_RESULT) {
                        // Get message saved data.
                        Bundle bundle = msg.getData();
                        String resultData = bundle.getString(KEY_RESULT_DATA);

                        // Show parsed result.
                        //descriptionTextView.setText(resultData);
                    }
                }
            };
        }

        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }

    }

    /* Send http get request to url.
     *  The callback input parameter is used to receive server response data.
     * */
    public void sendHttpGetRequest(String url, okhttp3.Callback callback) {
        // Create a OkHttpClient request builder.
        Request.Builder builder = new Request.Builder();

        // Set xml file url.
        builder = builder.url(url);

        // Build http request.
        Request request = builder.build();

        // Create a OkHttp3 Call object.
        Call call = okHttpClient.newCall(request);

        // Execute the get xml file request asynchronously in an automate child thread.
        call.enqueue(callback);
    }

    /* Use GSON to parse json string, return formatted string. */
    public void parseJSONWithGSON(String jsonString) {
        // Create a Gson object.
        Gson gson = new Gson();

        // Create a TypeToken. Because our jason file contains multiple json object, so the TypeToken is a list of UserInfoDTO.
        TypeToken<List<Business>> typeToken = new TypeToken<List<Business>>() {
        };

        // Get the type list from json string.
        List<Business> businesses = gson.fromJson(jsonString, typeToken.getType());

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        runOnUiThread(() -> {

            RecyclerViewClickListener listener = (view, position) -> {
                mPosition = position;
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ItemDetailFragment.BUSINESS_ITEM_TITLE, businesses.get(position).getTitle());
                    arguments.putString(ItemDetailFragment.BUSINESS_ITEM_DESCRIPTION, businesses.get(position).getDescription());
                    arguments.putString(ItemDetailFragment.BUSINESS_ITEM_PHONE_NUMBER, businesses.get(position).getPhoneNumber());
                    arguments.putString(ItemDetailFragment.BUSINESS_ITEM_ADDRESS, businesses.get(position).getAddress());
                    arguments.putString(ItemDetailFragment.BUSINESS_ITEM_HOUSE_NUMBER, businesses.get(position).getPhoneNumber());
                    arguments.putFloat(ItemDetailFragment.BUSINESS_ITEM_RATING, businesses.get(position).getRating());
                    arguments.putString(ItemDetailFragment.BUSINESS_ITEM_IMAGE_URL, businesses.get(position).getImageUrl());

                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    this.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.BUSINESS_ITEM_TITLE, businesses.get(position).getTitle());
                    intent.putExtra(ItemDetailFragment.BUSINESS_ITEM_DESCRIPTION, businesses.get(position).getDescription());
                    intent.putExtra(ItemDetailFragment.BUSINESS_ITEM_PHONE_NUMBER, businesses.get(position).getPhoneNumber());
                    intent.putExtra(ItemDetailFragment.BUSINESS_ITEM_ADDRESS, businesses.get(position).getAddress());
                    intent.putExtra(ItemDetailFragment.BUSINESS_ITEM_HOUSE_NUMBER, businesses.get(position).getHouseNumber());
                    intent.putExtra(ItemDetailFragment.BUSINESS_ITEM_RATING, businesses.get(position).getRating() + "");
                    intent.putExtra(ItemDetailFragment.BUSINESS_ITEM_IMAGE_URL, businesses.get(position).getImageUrl());

                    context.startActivity(intent);
                }
            };

            final android.support.design.widget.FloatingActionButton button = findViewById(R.id.addFarmerToFavoriteButton);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String title = businesses.get(mPosition).getTitle();
                    String description = businesses.get(mPosition).getDescription();
                    float rating = businesses.get(mPosition).getRating();

                    if (mPosition != 0){
                        Favorite favorite = new Favorite(title, description, rating);
                        favoriteViewModel.insert(favorite);
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(ItemListActivity.this).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("Selecteer eerst een item voordat u deze toevoegt aan uw favorieten.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                    }


                    Toast.makeText(mContext, "Favorite saved", Toast.LENGTH_SHORT).show();
                }
            });


            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(this);
            farmerListView.setLayoutManager(mLayoutManager);

            // specify an adapter (see also next example)
            mAdapter = new BusinessRecyclerAdapter( businesses, listener);
            farmerListView.setAdapter(mAdapter);
        });

        // Use this on old android versions. Before version 4.0 API 14
       /* runOnUiThread(() -> {
            BusinessAdapter adapter = new BusinessAdapter(mContext, 0, businesses);
            farmerListView.setAdapter(adapter);

            farmerListView.setOnItemClickListener((parent, view, position, id) -> {
                // get the selected item position and with that id, get the correct bussiness and give it to the next activity.
                Intent myIntent = new Intent(FarmerListActivity.this, FarmerActivity.class);
                myIntent.putExtra("Business", businesses.get(position)); //give model to new activity
                FarmerListActivity.this.startActivity(myIntent);
            });
        });*/
    }

    /* Send display text message to activity main thread Handler to show text in text view.*/
    public void sendDisplayParseJsonResultMessage(String text) {
        // Create a message.
        Message message = new Message();

        // Set message type.
        message.what = MESSAGE_SHOW_PARSE_RESULT;

        // Create a bundle and set data.
        Bundle bundle = new Bundle();
        bundle.putString(KEY_RESULT_DATA, text);
        message.setData(bundle);

        // Send the message to main thread handler.
        updateUIHandler.sendMessage(message);
    }

    public void openMapsWithCurrentLocation(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:" + latitude + "," + longitude + "?q=Hasselt, elfde-Liniestraat 23")); //lat lng or address query
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void menuSetup(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addFarmerToFavoriteButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.favorites_menu_item) {
            Intent myIntent = new Intent(ItemListActivity.this,
                    FavoritesActivity.class);
            startActivity(myIntent);
        } else if (id == R.id.home_menu_item) {
            Intent myIntent = new Intent(ItemListActivity.this,
                    ItemListActivity.class);
            startActivity(myIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
