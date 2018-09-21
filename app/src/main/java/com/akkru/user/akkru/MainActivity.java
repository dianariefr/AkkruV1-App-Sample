package com.akkru.user.akkru;

import android.graphics.Color;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.support.annotation.NonNull;

import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a21buttons.bottomnavigationview.widget.BottomNavigationView;
import com.akkru.user.akkru.mainfragment.CreditFragment;
import com.akkru.user.akkru.mainfragment.FavouriteFragment;
import com.akkru.user.akkru.mainfragment.HomeFragment;
import com.akkru.user.akkru.mainfragment.ProfileFragment;
import com.akkru.user.akkru.mainfragment.SearchFragment;

public class MainActivity extends AppCompatActivity implements CallbackListener, SearchFragment.OnFragmentInteractionListener{
    Button button;
    Toolbar toolbar, toolbar2;
    BottomNavigationView bottomNavigationView;
    ConstraintLayout userandpoint;
    SearchView searchView;
    RelativeLayout searchBar;
    TextView findCafe;
    ConstraintLayout toolbar4;
    TextView tvFavCred;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        userandpoint = findViewById(R.id.user_and_point);
        searchBar = findViewById(R.id.search_layout);
        tvFavCred = findViewById(R.id.TV_FavCred);

        toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        setSupportActionBar(toolbar);
        toolbar4 = findViewById(R.id.toolbar4);
        toolbar2 = findViewById(R.id.toolbar3);
        toolbar2.setTitle("");
        setSupportActionBar(toolbar);


        findCafe = findViewById(R.id.find_cafe);
        searchView = findViewById(R.id.search_view);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });


        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new SearchFragment());
            }
        });


        centerHintText(searchView);
        loadFragment(new HomeFragment());

        initUiComponents();
    }

    public static void centerHintText(SearchView searchView){
        EditText searchEditText = (EditText) searchView.findViewById(R.id.search_src_text);
        if (searchEditText != null) {
            searchEditText.setGravity(Gravity.CENTER);
        }
    }

    //Method load fragment
    private boolean loadFragment (Fragment fragment){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
    }

    @Override
    public void onCallBack(String a) {

        if(a=="Home"){
            userandpoint.setVisibility(View.VISIBLE);
            searchView.onActionViewCollapsed();
            searchBar.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.VISIBLE);
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            findCafe.setVisibility(View.VISIBLE);
            toolbar2.setVisibility(View.GONE);
        } else{
            userandpoint.setVisibility(View.GONE);
        }

        if (a=="Search"){
            findCafe.setVisibility(View.GONE);
            findCafe.setText("Find Cafe");
            findCafe.setTextColor(Color.WHITE);
            searchView.onActionViewExpanded(); //new Added line
            searchBar.setVisibility(View.VISIBLE);
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            toolbar.setVisibility(View.VISIBLE);
            toolbar2.setVisibility(View.GONE);
            //bottomNavigationView.setSelectedItemId(R.id.search_navigation);

        } else{
            searchView.onActionViewCollapsed();
        }

        if ((a=="Fav")||(a=="Credit")){
            toolbar.setVisibility(View.GONE);
            searchBar.setVisibility(View.GONE);
            toolbar2.setVisibility(View.VISIBLE);
        }

        if (a=="Fav"){
            tvFavCred.setText("Your Favorite Place");
        }
        if (a=="Credit"){
            tvFavCred.setText("Credit Exchange");
        }
    }

    @Override
    public void onTest() {

    }

    private void initUiComponents() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_navigation:
                        loadFragment(new HomeFragment());
                        break;
                    case R.id.search_navigation:
                        loadFragment(new SearchFragment());
                        break;
                    case R.id.favorite_navigation:
                        loadFragment(new FavouriteFragment());
                        break;
                    case R.id.credit_exchange_navigation:
                        loadFragment(new CreditFragment());
                        break;
                    case R.id.profile_navigation:
                        loadFragment(new ProfileFragment());
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
