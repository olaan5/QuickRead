package com.miniweam.quickread;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import com.miniweam.quickread.databinding.ActivityExploreNewsBinding;

public class ExploreNewsActivity extends AppCompatActivity {

    ActivityExploreNewsBinding exploreNewsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exploreNewsBinding = ActivityExploreNewsBinding.inflate(getLayoutInflater());
        setContentView(exploreNewsBinding.getRoot());


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.accountFragment, R.id.bookmarkFragment)
                .build();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
       NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(exploreNewsBinding.navView, navController);
    }
}