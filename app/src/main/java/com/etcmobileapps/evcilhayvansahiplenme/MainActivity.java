package com.etcmobileapps.evcilhayvansahiplenme;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etcmobileapps.evcilhayvansahiplenme.Fragments.AdsFragment;
import com.etcmobileapps.evcilhayvansahiplenme.Fragments.InboxFragment;
import com.etcmobileapps.evcilhayvansahiplenme.Fragments.NewAdd_Fragment;
import com.etcmobileapps.evcilhayvansahiplenme.Fragments.ProfileFragment;
import com.etcmobileapps.evcilhayvansahiplenme.Fragments.RegisterFragment;
import com.etcmobileapps.evcilhayvansahiplenme.Fragments.SearchFragment;
import com.etcmobileapps.ucretsizevcilhayvansahiplenme.R;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FrameLayout fragmentLayout;
    LinearLayout action;
    View menu;
    ImageButton drawerMenuButton;
   Boolean mSlideState=false;
    TextView newAddButton;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    BottomNavigationView navigation;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



      //  menu.setVisibility(View.GONE);
      //  action.setVisibility(View.GONE);
        fragmentLayout = findViewById(R.id.fragmentLayout);
        drawerMenuButton = findViewById(R.id.drawerMenuButton);
        newAddButton = findViewById(R.id.newAddCursor);



         navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        replaceFragments(RegisterFragment.class);

        setNavigationViewListener();


        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);

        newAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                replaceFragments(NewAdd_Fragment.class);

            }
        });

        drawerMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(Gravity.START);

            }
        });




        // to make the Navigation drawer icon always appear on the action bar

    }

    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   replaceFragments(AdsFragment.class);
                   navigation.setClickable(false);

                    break;
                case R.id.navigation_dashboard:
                    replaceFragments(ProfileFragment.class);
                    navigation.setClickable(false);
                    break;
                case R.id.navigation_notifications:
                    replaceFragments(InboxFragment.class);
                    navigation.setClickable(false);
                    break;
            }
          //  loadFragment(selectedFragment);
            return true;
        }
    };

    public void replaceFragments(Class fragmentClass) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentLayout, fragment)
                .commit();
    }


    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.NavigationView);
       navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

                     case R.id.nav_kopek: {
                         replaceFragmentsSearch("köpek", SearchFragment.class);
                         break;
                     }
                    case R.id.nav_kedi: {
                        replaceFragmentsSearch("kedi", SearchFragment.class);
                        break;
                    }
                    case R.id.nav_kus: {
                    replaceFragmentsSearch("kuş", SearchFragment.class);
                    break;
                    }
            case R.id.nav_balik: {
                replaceFragmentsSearch("balık", SearchFragment.class);
                break;
            }
            case R.id.nav_tavsan: {
                replaceFragmentsSearch("tavsan", SearchFragment.class);
                break;
            }
            case R.id.nav_hamster: {
                replaceFragmentsSearch("hamster", SearchFragment.class);
                break;
            }

            case R.id.nav_tavuk: {
                replaceFragmentsSearch("tavuk", SearchFragment.class);
                break;
            }
            case R.id.nav_horoz: {
                replaceFragmentsSearch("horoz", SearchFragment.class);
                break;
            }
            case R.id.nav_kaz: {
                replaceFragmentsSearch("kaz", SearchFragment.class);
                break;
            }
            case R.id.nav_ordek: {
                replaceFragmentsSearch("ordek", SearchFragment.class);
                break;
            }
            case R.id.nav_surungenler: {
                replaceFragmentsSearch("surungenler", SearchFragment.class);
                break;
            }



        }
        //close navigation drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragmentsSearch(String value,Class fragmentClass) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        Bundle args = new Bundle();
        args.putString("searchValue",value);
        args.putString("city",null);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragmentLayout, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }


}