package azka.noreen.translateall.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TextDAO {
    @Insert
    void insertText(TextEntity student);

//    @Delete
//    void deleteStudent(StudentEntity student);

    @Query("SELECT * FROM TextEntity")
    List<TextEntity> getAll();



}
