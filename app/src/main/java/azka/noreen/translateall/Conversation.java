package azka.noreen.translateall;

import static azka.noreen.translateall.Languages.initLanguages;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

import azka.noreen.translateall.API.RetrofitClient;
import azka.noreen.translateall.database.TextEntity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Conversation extends AppCompatActivity {

    ImageButton leftMic,rightMic;
    RecyclerView recycleView;
    ArrayList<ConversationModel> studentNameCourseArrayList;
    ConversationAdapter fra;
    Toolbar conversationTolbar;
    Button clear,yes,no;
    TextView rightLanguage,leftLanguage;
    SharedPreferences sharedPreferences;
    Dialog dialog2;
    SharedPreferences.Editor sharedPreferencesEditor;
    ArrayList<LanguageModel> LanguageAndCode;

    private static final int Right_REQUEST_CODE = 0;
    private static final int Left_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        leftMic=findViewById(R.id.firstMic);
        rightMic=findViewById(R.id.secondMic);
        recycleView=findViewById(R.id.conversationRV);
        conversationTolbar=findViewById(R.id.toolbarConversation);
        clear=findViewById(R.id.clearAll);
        leftLanguage=findViewById(R.id.left);
        rightLanguage=findViewById(R.id.rightCOnveration);
        LanguageAndCode=initLanguages();


        initSharedPref();
        leftLanguage.setText(getPrefernceValues("Left"));
        rightLanguage.setText(getPrefernceValues2("Right"));

        studentNameCourseArrayList=new ArrayList<>();
        InitRecycleView();


        setSupportActionBar(conversationTolbar);
        conversationTolbar.getNavigationIcon().setTint(ContextCompat.getColor(Conversation.this,R.color.white));

        conversationTolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        leftLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Conversation.this, Languages.class);
                startActivityForResult(in, 20);
            }
        });
        rightLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Conversation.this, Languages.class);
                startActivityForResult(in, 200);            }
        });
        leftMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySpeechRecognizer(Left_REQUEST_CODE,findCode(leftLanguage.getText().toString()));
            }
        });
        rightMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySpeechRecognizer(Right_REQUEST_CODE,findCode(rightLanguage.getText().toString()));
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2 = new Dialog(Conversation.this);
                dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog2.setCancelable(false);
                dialog2.setContentView(R.layout.cleardialog);
                dialog2.show();
                yes=dialog2.findViewById(R.id.yes_btn);
                no=dialog2.findViewById(R.id.no_btn);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        studentNameCourseArrayList.clear();
                        clear.setVisibility(View.INVISIBLE);
                        fra.notifyDataSetChanged();
                        dialog2.dismiss();  }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog2.dismiss();        }
                });
            }
        });


    }
    public void InitRecycleView(){
        fra=new ConversationAdapter();
        recycleView.setAdapter(fra);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        fra.setData(studentNameCourseArrayList);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20 && resultCode==RESULT_OK) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    leftLanguage.setText(data.getStringExtra("Name"));
                    putPrefernceValues("Left",data.getStringExtra("Name"));
                }
            }
        }
        else if(requestCode == 200 && resultCode==RESULT_OK) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    rightLanguage.setText(data.getStringExtra("Name"));
                    putPrefernceValues("Right",data.getStringExtra("Name"));
                }
            }
        }
        if (requestCode == Left_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            getRetrofitTranslation(spokenText,findCode(leftLanguage.getText().toString()),findCode(rightLanguage.getText().toString()),ConversationModel.LeftConversation);
//            Toast.makeText(this, tranlation+"", Toast.LENGTH_SHORT).show();
//            ConversationModel lm=new ConversationModel(spokenText,tranlation,ConversationModel.LeftConversation);
//            studentNameCourseArrayList.add(lm);
//            clear.setVisibility(View.VISIBLE);
//            fra.notifyItemInserted(studentNameCourseArrayList.size());

            // Do something with spokenText.
        }
        if (requestCode == Right_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            getRetrofitTranslation(spokenText,findCode(rightLanguage.getText().toString()),findCode(leftLanguage.getText().toString()),ConversationModel.RightConversation);
//            Toast.makeText(this, tranlation+"", Toast.LENGTH_SHORT).show();
//            ConversationModel lm=new ConversationModel(spokenText,tranlation,ConversationModel.RightConversation);
//            studentNameCourseArrayList.add(lm);
//            clear.setVisibility(View.VISIBLE);
//            fra.notifyItemInserted(studentNameCourseArrayList.size());

            // Do something with spokenText.
        }
    }

    private void displaySpeechRecognizer( int requestCode,String languageCode) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,languageCode);

// This starts the activity and populates the intent with the speech text.
        startActivityForResult(intent, requestCode);
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
    public void getRetrofitTranslation(String inputWord,String InputLanguage,String TargetLanguage,int type){

        RetrofitClient retrofitClient= new RetrofitClient();
        Call<JsonArray> userDetail= retrofitClient.getUserService().getTrans(InputLanguage,TargetLanguage,inputWord);
        userDetail.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

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
//                        translation.setText(word.toString());
                        word.toString();
                        ConversationModel lm=new ConversationModel(inputWord, word.toString(),type);
                        Toast.makeText(Conversation.this, inputWord+word.toString()+type, Toast.LENGTH_SHORT).show();
                        studentNameCourseArrayList.add(lm);
//                        clear.setVisibility(View.VISIBLE);
                        fra.notifyItemInserted(studentNameCourseArrayList.size());
                    }
                    catch (Exception e){

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Toast.makeText(Conversation.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}