package azka.noreen.translateall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import azka.noreen.translateall.database.TextEntity;
import azka.noreen.translateall.database.appDatabase;

public class Translations extends AppCompatActivity {
    RecyclerView d;
    appDatabase db=MainActivity.db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translations);
        d=findViewById(R.id.dt);
        List<TextEntity> allStudent=db.textDAO().getAll();
        HistoryRecyclerAdapter historyRecyclerAdapter=new HistoryRecyclerAdapter();
        d.setAdapter(historyRecyclerAdapter);
        d.setLayoutManager(new LinearLayoutManager(this));
        d.addItemDecoration(new SimpleDividerItemDecoration(this));

        historyRecyclerAdapter.setData(allStudent);

    }


}