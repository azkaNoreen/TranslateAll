package azka.noreen.translateall;

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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.List;

public class Conversation extends AppCompatActivity {

    ImageButton leftMic,rightMic;
    RecyclerView recycleView;
    ArrayList<ConversationModel> studentNameCourseArrayList;
    ConversationAdapter fra;
    Toolbar conversationTolbar;
    Button clear,yes,no;
    TextView rightLanguage,leftLanguage;
    ImageButton rightdown,leftdown;
    SharedPreferences sharedPreferences;
    Dialog dialog2;
    SharedPreferences.Editor sharedPreferencesEditor;
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
        leftdown=findViewById(R.id.leftdownArrow);
        rightLanguage=findViewById(R.id.rightCOnveration);
        rightdown=findViewById(R.id.rightdown);


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
                displaySpeechRecognizer(Left_REQUEST_CODE);
            }
        });
        rightMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySpeechRecognizer(Right_REQUEST_CODE);
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
            ConversationModel lm=new ConversationModel(spokenText,ConversationModel.LeftConversation);
            studentNameCourseArrayList.add(lm);
            clear.setVisibility(View.VISIBLE);
            fra.notifyItemInserted(studentNameCourseArrayList.size());

            // Do something with spokenText.
        }
        if (requestCode == Right_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            ConversationModel lm=new ConversationModel(spokenText,ConversationModel.RightConversation);
            studentNameCourseArrayList.add(lm);
            clear.setVisibility(View.VISIBLE);
            fra.notifyItemInserted(studentNameCourseArrayList.size());

            // Do something with spokenText.
        }
    }

    private void displaySpeechRecognizer( int requestCode) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
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
}