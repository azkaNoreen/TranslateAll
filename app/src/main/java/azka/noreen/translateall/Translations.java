package azka.noreen.translateall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import azka.noreen.translateall.database.TextEntity;
import azka.noreen.translateall.database.appDatabase;

public class Translations extends AppCompatActivity {
    ListView d;
    appDatabase db=MainActivity.db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translations);
        d=findViewById(R.id.dt);
        List<TextEntity> allStudent=db.textDAO().getAll();
        ArrayAdapter<TextEntity> as=new ArrayAdapter<TextEntity>(this,android.R.layout.simple_list_item_1,allStudent);
        d.setAdapter(as);
    }


}