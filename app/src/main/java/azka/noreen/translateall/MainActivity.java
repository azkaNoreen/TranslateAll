package azka.noreen.translateall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    EditText text;
    ImageButton cancel,camera,mic;
    RelativeLayout bottombg;
    TextView language1,language2,translation;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    String spokenText;
    private static final int SPEECH_REQUEST_CODE = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 50 && resultCode==RESULT_OK) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    language1.setText(data.getStringExtra("Name"));
                    putPrefernceValues("Name",data.getStringExtra("Name"));
                }
            }
        }
        else if(requestCode == 500 && resultCode==RESULT_OK) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    language2.setText(data.getStringExtra("Name"));
                    putPrefernceValues("Second",data.getStringExtra("Name"));
                }
            }
        }
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            spokenText = results.get(0);
            text.setText(spokenText);
            // Do something with spokenText.
        }
    }

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
// This starts the activity and populates the intent with the speech text.
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolba);
        drawerLayout=findViewById(R.id.drawerLay);
        navigationView=findViewById(R.id.nv);
        text=findViewById(R.id.text);
        cancel=findViewById(R.id.cancel);
        camera=findViewById(R.id.camera);
        mic=findViewById(R.id.mic);
        bottombg=findViewById(R.id.bottombox);
        language1=findViewById(R.id.eng);
        language2=findViewById(R.id.ur);
        translation=findViewById(R.id.translation);

        initSharedPref();
        language1.setText(getPrefernceValues("Name"));
        language2.setText(getPrefernceValues2("Second"));

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySpeechRecognizer();
            }
        });
        language1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Languages.class);
                startActivityForResult(in, 50);
            }
        });
        language2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Languages.class);
                startActivityForResult(in, 500);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("");
                camera.setTag("Camera");
                camera.setImageResource(R.drawable.ic_baseline_photo_camera_24);
                mic.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.INVISIBLE);
                bottombg.setVisibility(View.GONE);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(camera.getTag().equals("Arrow")){
                    translation.setText(text.getText().toString());
                    mic.setVisibility(View.VISIBLE);
                    mic.setImageResource(R.drawable.ic_baseline_volume_up_24);
                    bottombg.setVisibility(View.VISIBLE);
                }
            }
        });
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            cancel.setVisibility(View.VISIBLE);
            camera.setTag("Arrow");
            camera.setImageResource(R.drawable.ic_baseline_arrow_forward_24);
            mic.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        setSupportActionBar(toolbar); //set toolbar to act as action bar
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //add a back arrow button
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);  //set navigation icon if you don't want back arrow
        //on pressing arrow ie navigation Icon
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int menuItemId=item.getItemId();
                if(menuItemId==R.id.share){
                    Toast.makeText(MainActivity.this, "Share Clicked", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else if(menuItemId==R.id.bookmarkedList){
                    Toast.makeText(MainActivity.this, "Bookmark Clicked", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else{
                    Toast.makeText(MainActivity.this, "Remove Clicked", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }

                return false;
            }
        });
    }
    public void initSharedPref(){
        sharedPreferences=getSharedPreferences("myPref",MODE_PRIVATE);
        sharedPreferencesEditor=sharedPreferences.edit();

    }
    public void putPrefernceValues(String n,String s){
        sharedPreferencesEditor.putString(n,s);
        sharedPreferencesEditor.apply();
    }
    public String getPrefernceValues(String n){
        String ng=sharedPreferences.getString(n,"English");
return ng;
    }
    public String getPrefernceValues2(String n){
        String ng=sharedPreferences.getString(n,"Urdu");
        return ng;
    }
}