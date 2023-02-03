package azka.noreen.translateall.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TextEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name="input_word")
    public String input_word;

    @ColumnInfo(name="output_word")
    public String output_word;

    @ColumnInfo(name="input_language")
    public String input_language;

    @ColumnInfo(name="input_language_code")
    public String input_language_code;

    @ColumnInfo(name="translation_language")
    public String translation_language;

    @ColumnInfo(name="translation_language_code")
    public String translation_language_code;

    @ColumnInfo(name="is_favorite")
    public Boolean is_favorite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInput_word() {
        return input_word;
    }

    public void setInput_word(String input_word) {
        this.input_word = input_word;
    }

    public String getOutput_word() {
        return output_word;
    }

    public void setOutput_word(String output_word) {
        this.output_word = output_word;
    }

    public String getInput_language() {
        return input_language;
    }

    public void setInput_language(String input_language) {
        this.input_language = input_language;
    }

    public String getInput_language_code() {
        return input_language_code;
    }

    public void setInput_language_code(String input_language_code) {
        this.input_language_code = input_language_code;
    }

    public String getTranslation_language() {
        return translation_language;
    }

    public void setTranslation_language(String translation_language) {
        this.translation_language = translation_language;
    }

    public String getTranslation_language_code() {
        return translation_language_code;
    }

    public void setTranslation_language_code(String translation_language_code) {
        this.translation_language_code = translation_language_code;
    }

    public Boolean getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(Boolean is_favorite) {
        this.is_favorite = is_favorite;
    }

    public TextEntity() {
    }

    public TextEntity(int id, String input_word, String output_word, String input_language, String input_language_code, String translation_language, String translation_language_code, Boolean is_favorite) {
        this.id = id;
        this.input_word = input_word;
        this.output_word = output_word;
        this.input_language = input_language;
        this.input_language_code = input_language_code;
        this.translation_language = translation_language;
        this.translation_language_code = translation_language_code;
        this.is_favorite = is_favorite;
    }
}
