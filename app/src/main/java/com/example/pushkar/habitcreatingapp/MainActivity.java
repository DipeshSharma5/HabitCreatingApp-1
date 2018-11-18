package com.example.pushkar.habitcreatingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pushkar.habitcreatingapp.Fragments.Fragment_home;
import com.example.pushkar.habitcreatingapp.Fragments.Fragment_journeys;
import com.example.pushkar.habitcreatingapp.Fragments.Fragment_progress;
import com.example.pushkar.habitcreatingapp.Models.RitualData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String username,z;
    List<RitualData> ritualDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        username = user.getEmail();
        z=username.substring(0,username.indexOf('@'));

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        loadFragment(new Fragment_home());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Setting) {
            Intent sett = new Intent(MainActivity.this,setting.class);
            startActivity(sett);
        }
        else
        if (id == R.id.refresh) {
            getData();
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean loadFragment(Fragment fragment){

        if(fragment != null)
        {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

            return true;
        }

        return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;

        switch (menuItem.getItemId()){

            case R.id.navigation_home :
                fragment = new Fragment_home();
                break;

            case R.id.navigation_progress :
                fragment = new Fragment_progress();
                break;

            case R.id.navigation_journeys:
                fragment = new Fragment_journeys();
                break;

        }

        return  loadFragment(fragment);
    }

    public void onBackPressed() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }

    public void getData(){
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase("https://project-60d0d.firebaseio.com/");
        final Firebase userRef = ref.child(z);
        userRef.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                int i =0;
                for (com.firebase.client.DataSnapshot keys : dataSnapshot.getChildren()){
                    i=i+1;
                }

             //   GenericTypeIndicator<List<RitualData>> genericTypeIndicator= new GenericTypeIndicator<List<RitualData>>(){};
             //   ritualDataList = dataSnapshot.getValue(genericTypeIndicator);

                String[] tempKey = new String[i];
                int j =0;
                for (com.firebase.client.DataSnapshot keys :dataSnapshot.getChildren() ){
                    tempKey[j]=keys.getValue().toString();
                    j=j+1;
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,tempKey);
                Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
