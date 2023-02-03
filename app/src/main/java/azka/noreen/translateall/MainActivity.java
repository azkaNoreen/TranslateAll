package azka.noreen.translateall;

import static azka.noreen.translateall.Languages.initLanguages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import azka.noreen.translateall.API.RetrofitClient;
import azka.noreen.translateall.database.TextEntity;
import azka.noreen.translateall.database.appDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    EditText text;
    ImageButton cancel,camera,mic;
    RelativeLayout bottombg;
    TextView language1,language2,translation,translationLanguage;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    String spokenText;
    ProgressBar Loading;
    ArrayList<LanguageModel> LanguageAndCode;
    String Language,TranslateLanguage;
    static appDatabase db;
    ImageButton swap;

    private static final int SPEECH_REQUEST_CODE = 0;

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
        Loading=findViewById(R.id.loading);
        LanguageAndCode=initLanguages();
        swap=findViewById(R.id.swap);
        translationLanguage=findViewById(R.id.translationLanguage);


        initSharedPref();
        MakeDB();

        language1.setText(getPrefernceValues("Language"));
        language2.setText(getPrefernceValues2("TranslateLanguage"));
        mic.setImageResource(R.drawable.ic_baseline_mic_24);

        Intent intent=getIntent();
        if(intent.getAction().equals("History")){
        String word=intent.getStringExtra("word");
        String outputWord=intent.getStringExtra("outputWord");
        String inputLang=intent.getStringExtra("inputLang");
        String translationIntent=intent.getStringExtra("translation");

            text.setText(word);
            mic.setVisibility(View.VISIBLE);
            mic.setImageResource(R.drawable.ic_baseline_volume_up_24);
            cancel.setVisibility(View.VISIBLE);
            camera.setTag("Arrow");
            camera.setImageResource(R.drawable.ic_baseline_arrow_forward_24);
            translation.setText(outputWord);
            translationLanguage.setText(translationIntent);
            bottombg.setVisibility(View.VISIBLE);
            language1.setText(inputLang);
            language2.setText(translationIntent);
        }


        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySpeechRecognizer(findCode(language1.getText().toString()));
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
                mic.setImageResource(R.drawable.ic_baseline_mic_24);
                cancel.setVisibility(View.INVISIBLE);
                bottombg.setVisibility(View.GONE);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(camera.getTag().equals("Arrow")){
                    Loading.setVisibility(View.VISIBLE);
                    getRetrofitTranslation(text.getText().toString(),findCode(language1.getText().toString()),findCode(language2.getText().toString()));
                    mic.setVisibility(View.VISIBLE);
                    mic.setImageResource(R.drawable.ic_baseline_volume_up_24);
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
        swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Lang= language2.getText().toString();
                String lang2=language1.getText().toString();
                String input=translation.getText().toString();
                language2.setText(language1.getText().toString());
                language1.setText(Lang);
                String output=text.getText().toString();

                if((!text.getText().toString().equals("") && !translation.getText().toString().equals("")))
                {
                    text.setText(translation.getText().toString());
                    translation.setText(output);
                    translationLanguage.setText(lang2);
                }
                TextEntity textEntity=new TextEntity();
                textEntity.setInput_word(input);
                textEntity.setOutput_word(output);
                textEntity.setInput_language(Lang);
                textEntity.setTranslation_language(lang2);
                textEntity.setInput_language_code(findCode(Lang));
                textEntity.setTranslation_language_code(findCode(lang2));
                db.textDAO().insertText(textEntity);

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
                    Intent in = new Intent(MainActivity.this, Conversation.class);
                    startActivity(in);
                    Toast.makeText(MainActivity.this, "Share Clicked", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else if(menuItemId==R.id.bookmarkedList){
                    Intent in = new Intent(MainActivity.this, Translations.class);
                    startActivity(in);
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
    public void getRetrofitTranslation(String inputWord,String InputLanguage,String TargetLanguage){

        RetrofitClient retrofitClient= new RetrofitClient();
        Call<JsonArray> userDetail= retrofitClient.getUserService().getTrans(InputLanguage,TargetLanguage,inputWord);
        userDetail.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                Loading.setVisibility(View.GONE);
                bottombg.setVisibility(View.VISIBLE);
                translationLanguage.setText(language2.getText().toString());

                if(response!=null) {
                    try{
                    JsonArray dataList = response.body();
                        Log.d("Rez", dataList+"");

                        StringBuilder word=new StringBuilder();
                    JsonArray inner = (JsonArray) dataList.get(0);
                    for (int i = 0; i < inner.size(); i++) {
                        JsonArray parseResult = (JsonArray) inner.get(i);
                        String result = (String)parseResult.get(0).getAsString();
                        if (!result.contains("null")) {
                            word.append(result);
                        }

                    }
                        translation.setText(word.toString());
                    TextEntity textEntity=new TextEntity();
                    textEntity.setInput_word(inputWord);
                    textEntity.setOutput_word(word.toString());
                    textEntity.setInput_language(language1.getText().toString());
                    textEntity.setTranslation_language(language2.getText().toString());
                    textEntity.setInput_language_code(InputLanguage);
                    textEntity.setTranslation_language_code(TargetLanguage);
                    db.textDAO().insertText(textEntity);
                    }
                    catch (Exception e){

                    }

                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 50 && resultCode==RESULT_OK) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    language1.setText(data.getStringExtra("Name"));
                    putPrefernceValues("Language",data.getStringExtra("Name"));
                }
            }
        }
        else if(requestCode == 500 && resultCode==RESULT_OK) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    language2.setText(data.getStringExtra("Name"));
                    putPrefernceValues("TranslateLanguage",data.getStringExtra("Name"));
                    if(!text.getText().toString().equals("")){
                        camera.performClick();
                    }

                }
            }
        }
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            spokenText = results.get(0);
            text.setText(spokenText);
            // Do something with spokenText.
            camera.performClick();

        }
    }

    private String findCode(String name) {
        String code="";
        for(int i=0;i<LanguageAndCode.size();i++){
            if(LanguageAndCode.get(i).getName().equals(name))
            {
                code=LanguageAndCode.get(i).getCode();
            }
        }
        return code;
    }
    private void MakeDB() {
        db= Room.databaseBuilder(getApplicationContext(),
                appDatabase.class,
                "student_database").allowMainThreadQueries().build();
    }


    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer(String languageCode) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,languageCode);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");
// This starts the activity and populates the intent with the speech text.
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
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