package azka.noreen.translateall.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {TextEntity.class},version = 3)
public abstract class appDatabase extends RoomDatabase {
    public static appDatabase dataBase;
    public abstract TextDAO textDAO();

}
