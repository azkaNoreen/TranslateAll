package azka.noreen.translateall;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class Conversation extends AppCompatActivity {

    ImageButton leftMic,rightMic;
    RecyclerView recycleView;
    ArrayList<ConversationModel> studentNameCourseArrayList;
    ConversationAdapter fra;
    private static final int Right_REQUEST_CODE = 0;
    private static final int Left_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        leftMic=findViewById(R.id.firstMic);
        rightMic=findViewById(R.id.secondMic);
        recycleView=findViewById(R.id.conversationRV);

        InitRecycleView();

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
        if (requestCode == Left_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            ConversationModel lm=new ConversationModel(spokenText,ConversationModel.LeftConversation);
            studentNameCourseArrayList.add(lm);

            // Do something with spokenText.
        }
        if (requestCode == Right_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            ConversationModel lm=new ConversationModel(spokenText,ConversationModel.RightConversation);
            studentNameCourseArrayList.add(lm);

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
}