package com.example.androidmapapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    EditText e1;
    Button b1;
    RadioButton r1;
    RadioButton r2;
    RadioButton r3;
    Button b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPref.registerOnSharedPreferenceChangeListener(this);

        e1 = (EditText)findViewById(R.id.e1);

        b1 = (Button) findViewById(R.id.b1);
        r1 = (RadioButton) findViewById(R.id.r1);
        r2 = (RadioButton) findViewById(R.id.r2);
        r3 = (RadioButton) findViewById(R.id.r3);
        b2 = (Button) findViewById(R.id.b2);
    }

        // TO DO 0.1 - set up by establishing references to the searchLocation widget and the getMapButton

        //** PART 3 - READING FROM SharedPreferences **
        // TO DO 3.1 - [create a Preference Fragment and embed it in SettingsActivity]
        // TO DO 3.2 - read from SharedPreferences
    public void process(View view){
        Intent intent = null;
        Intent chooser = null;
        String location = e1.getText().toString();
        List<Address> addressList=null;
        if (view.getId()==R.id.b1){
            if (location!=null ||!location.equals("")){
                Geocoder geocoder = new Geocoder(this);
                try {
                    addressList = geocoder.getFromLocationName(location,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Address address = addressList.get(0);
                intent =  new Intent(Intent.ACTION_VIEW);
                String s1 = Double.toString(address.getLatitude());
                String s2 = Double.toString(address.getLongitude());
                intent.setData(Uri.parse("geo:"+ s1+","+s2));
                chooser = Intent.createChooser(intent,"Launch Maps");
                startActivity(chooser);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuitem){
        int id = menuitem.getItemId();
        if(id==R.id.settings){
            Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return true;
    }

    public void changeSomeAttribute(boolean isButtonLargeFont) {
        if (isButtonLargeFont){
            b1.setTextSize(50);
        }
        else{
            b1.setTextSize(20);
        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.chkBoxLargeFontKey))){
            //same code as above
            String chkBoxLargeFontKey = getString(R.string.chkBoxLargeFontKey);
            boolean checked = sharedPreferences.getBoolean(chkBoxLargeFontKey,false);
            //REMINDER - write code for this method
            changeSomeAttribute(checked);
        }
        else{
            changeSomeAttribute(false);
        }

    }

// *** PART 2 - CREATING A MENU ***
// TO DO 2.1 - [in res/menu folder], create an xml file defining your menu
// TO DO 2.2 - inflate your menu in onCreateOptionsMenu()
// TO DO 2.3 - [Create a new activity called SettingsActivity]
// TO DO 2.4 - create an intent in onOptionsItemSelected()


// ** PART 3 - READING FROM SharedPreferences **
// TO DO 3.3 onSharedPreferencesListener


}
