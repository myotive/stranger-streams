package com.example.myotive.strangerstreamsdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.myotive.strangerstreamsdemo.ui.CodeMashFragment;
import com.example.myotive.strangerstreamsdemo.ui.DnDFragment;
import com.example.myotive.strangerstreamsdemo.ui.RxJavaBasicsFragment;
import com.example.myotive.strangerstreamsdemo.ui.RxJavaFormsFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ActionBarDrawerToggle mDrawerToggle;

    private NavigationView navigationView;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        SetupToolbar();
    }

    private void SetupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.navigation_open,  /* "open drawer" description */
                R.string.navigation_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("Stranger Streams");
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("");
            }
        };


        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //Closing drawer on item click
                mDrawerLayout.closeDrawers();

                switch (item.getItemId()){
                    case R.id.navdrawer_item_rxjava:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_content, RxJavaBasicsFragment.newInstance())
                                .commit();

                        return true;
                    case R.id.navdrawer_item_rxjava_form:

                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_content, RxJavaFormsFragment.newInstance())
                                .commit();

                        return true;
                    case R.id.navdrawer_item_dnd:

                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_content, DnDFragment.newInstance())
                                .commit();

                        return true;
                    case R.id.navdrawer_item_codemash:

                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_content, CodeMashFragment.newInstance())
                                .commit();

                        return true;
                }

                return false;
            }
        });
    }
}
