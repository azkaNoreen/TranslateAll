package azka.noreen.translateall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class Languages extends AppCompatActivity {
    RecyclerView recycleView;
    ArrayList<LanguageModel> studentNameCourseArrayList;
    EditText languageToBeSearched;
    Toolbar searchToolbar;
    LanguageRecycler fra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languages);
        recycleView=findViewById(R.id.languages);
        languageToBeSearched=findViewById(R.id.searchLanguage);
        searchToolbar=findViewById(R.id.toolbars);


        studentNameCourseArrayList=initLanguages();
        InitRecycleView();
        setSupportActionBar(searchToolbar);
        searchToolbar.setTitle("Languages");
        searchToolbar.getNavigationIcon().setTint(ContextCompat.getColor(Languages.this,R.color.white));

        searchToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        languageToBeSearched.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchToolbar.getMenu().findItem(R.id.clears).getIcon().setTint(ContextCompat.getColor(Languages.this,R.color.white));

                searchToolbar.getMenu().findItem(R.id.clears).setVisible(true);
                ArrayList<LanguageModel> searched=new ArrayList<>();
                for(int i=0;i<studentNameCourseArrayList.size();i++){
                    if(studentNameCourseArrayList.get(i).getName().toLowerCase().startsWith(languageToBeSearched.getText().toString().toLowerCase())){
                        searched.add(studentNameCourseArrayList.get(i));
                    }
                }
                fra.setData(searched);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toobar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuItem=item.getItemId();
        if(menuItem==R.id.searchLanguage){
            languageToBeSearched.setVisibility(View.VISIBLE);
//            searchToolbar.setTitle("");
            item.setVisible(false);
        }
        else if(menuItem==R.id.clears){
            languageToBeSearched.setText("");
//            searchToolbar.setTitle("");
            item.setVisible(false);
        }
        return super.onOptionsItemSelected(item);
    }

    public void InitRecycleView(){
        fra=new LanguageRecycler();
        fra.setMyInterface(new MyInterface() {
            @Override
            public void onLanguageClick(LanguageModel language) {
                Intent intent=new Intent();
                intent.putExtra("Name",language.getName());
                intent.putExtra("Code",language.getCode());

                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
        recycleView.setAdapter(fra);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        fra.setData(studentNameCourseArrayList);

    }
    static ArrayList<LanguageModel> initLanguages() {
        ArrayList<LanguageModel> langList=new ArrayList<>();
        langList.add(new LanguageModel("af", "Afrikaans", "Afrikaans", "ZA"));
        langList.add(new LanguageModel("sq", "Albanian", "shqiptar", "AL"));
        langList.add(new LanguageModel("am", "Amharic", "አማርኛ", "ET"));
        langList.add(new LanguageModel("ar", "Arabic", "العربية", "SA"));
        langList.add(new LanguageModel("hy", "Armenian", "հայերեն", "AM"));
        langList.add(new LanguageModel("az", "Azerbaijani", "Azərbaycan", "AZ"));
        langList.add(new LanguageModel("eu", "Basque", "Euskal", "ES"));
        langList.add(new LanguageModel("be", "Belarusian", "Беларус", "BY"));
        langList.add(new LanguageModel("bn", "Bengali", "বাংলা", "BD"));
        langList.add(new LanguageModel("bs", "Bosnian", "bosanski", "BA"));
        langList.add(new LanguageModel("bg", "Bulgarian", "Български", "BG"));
        langList.add(new LanguageModel("ca", "Catalan", "Català", "ES"));
        langList.add(new LanguageModel("ceb", "Cebuano", "Cebuano", "PH"));
        langList.add(new LanguageModel("zh", "Chinese", "中文", "CN"));
        langList.add(new LanguageModel("co", "Corsican", "Corsu", "FR"));
        langList.add(new LanguageModel("hr", "Croatian", "Hrvatski", "HR"));
        langList.add(new LanguageModel("cs", "Czech", "Čeština", "CZ"));
        langList.add(new LanguageModel("da", "Danish", "Dansk", "DK"));
        langList.add(new LanguageModel("nl", "Dutch", "Nederlands", "NL"));
        langList.add(new LanguageModel("en", "English", "English", "US"));
        langList.add(new LanguageModel("eo", "Esperanto", "Esperanto", "AAE")); // espranto flag
        langList.add(new LanguageModel("et", "Estonian", "Eesti", "EE"));
        langList.add(new LanguageModel("fi", "Finnish", "Suomi", "FI"));
        langList.add(new LanguageModel("fr", "French", "Français", "FR"));
        langList.add(new LanguageModel("fy", "Frisian", "Frysk", "DE"));
        langList.add(new LanguageModel("tl", "Filipino", "Pilipino", "PH"));
        langList.add(new LanguageModel("gl", "Galician", "Galego", "ES"));
        langList.add(new LanguageModel("ka", "Georgian", "ქართული", "GE"));
        langList.add(new LanguageModel("de", "German", "Deutsch", "DE"));
        langList.add(new LanguageModel("el", "Greek", "Ελληνικά", "GR"));
        langList.add(new LanguageModel("gu", "Gujarati", "ગુજરાતી", "IN"));
        langList.add(new LanguageModel("ht", "Haitian Creole", "Haitian Creole Version", "HT"));
        langList.add(new LanguageModel("ha", "Hausa", "Hausa", "NG"));
        langList.add(new LanguageModel("haw", "Hawaiian", "ʻ .lelo Hawaiʻi", "AAH"));// hawai flag
        langList.add(new LanguageModel("he", "Hebrew", "עברית", "IL"));
        langList.add(new LanguageModel("hi", "Hindi", "हिंदी", "IN"));
        langList.add(new LanguageModel("hmn", "Hmong", "Hmong", "CN"));
        langList.add(new LanguageModel("hu", "Hungarian", "Magyar", "HU"));
        langList.add(new LanguageModel("is", "Icelandic", "Íslensku", "IS"));
        langList.add(new LanguageModel("ig", "Igbo", "Ndi Igbo", "NG"));
        langList.add(new LanguageModel("id", "Indonesian", "Indonesia", "ID"));
        langList.add(new LanguageModel("ga", "Irish", "Gaeilge", "IE"));
        langList.add(new LanguageModel("it", "Italian", "Italiano", "IT"));
        langList.add(new LanguageModel("ja", "Japanese", "日本語", "JP"));
        langList.add(new LanguageModel("jw", "Javanese", "Basa jawa", "ID"));
        langList.add(new LanguageModel("kn", "Kannada", "ಕನ್ನಡ", "IN"));
        langList.add(new LanguageModel("kk", "Kazakh", "Қазақ", "KZ"));
        langList.add(new LanguageModel("km", "Khmer", "ខខ្មែរ។", "TH"));
        langList.add(new LanguageModel("ko", "Korean", "한국어", "KR"));
        langList.add(new LanguageModel("ku", "Kurdish", "Kurdî", "IQ"));
        langList.add(new LanguageModel("ky", "Kyrgyz", "Кыргызча", "IQ"));
        langList.add(new LanguageModel("lo", "Lao", "ລາວ", "TH"));
        langList.add(new LanguageModel("la", "Latin", "Latine", "IT"));
        langList.add(new LanguageModel("lv", "Latvian", "Latviešu valoda", "LV"));
        langList.add(new LanguageModel("lt", "Lithuanian", "Lietuvių", "LT"));
        langList.add(new LanguageModel("lb", "Luxembourgish", "Lëtzebuergesch", "LU"));
        langList.add(new LanguageModel("mk", "Macedonian", "Македонски", "MK"));
        langList.add(new LanguageModel("mg", "Malagasy", "Malagasy", "MG"));
        langList.add(new LanguageModel("ms", "Malay", "Bahasa Melayu", "MY"));
        langList.add(new LanguageModel("ml", "Malayalam", "മലയാളം", "IN"));
        langList.add(new LanguageModel("mt", "Maltese", "Il-Malti", "MT"));
        langList.add(new LanguageModel("mi", "Maori", "Maori", "NZ"));
        langList.add(new LanguageModel("mr", "Marathi", "मराठी", "IN"));
        langList.add(new LanguageModel("mn", "Mongolian", "Монгол", "MN"));
        langList.add(new LanguageModel("my", "Myanmar", "မြန်မာ", "MM"));
        langList.add(new LanguageModel("ne", "Nepali", "नेपाली", "NP"));
        langList.add(new LanguageModel("nb", "Norwegian", "Norsk", "NO"));
        langList.add(new LanguageModel("ny", "Nyanja", "Nyanja", "MW"));
        langList.add(new LanguageModel("ps", "Pashto", "پښتو", "PK"));
        langList.add(new LanguageModel("fa", "Persian", "فارسی", "IR"));
        langList.add(new LanguageModel("pl", "Polish", "Polski", "PL"));
        langList.add(new LanguageModel("pt", "Portuguese", "Português", "PT"));
        langList.add(new LanguageModel("pa", "Punjabi", "ਪੰਜਾਬੀ", "IN"));
        langList.add(new LanguageModel("ro", "Romanian", "Română", "RO"));
        langList.add(new LanguageModel("ru", "Russian", "Pусский", "RU"));
        langList.add(new LanguageModel("gd", "Scots Gaelic", "Gàidhlig na h-Alba", "GB"));
        langList.add(new LanguageModel("sr", "Serbian", "Српски", "RS"));
        langList.add(new LanguageModel("sn", "Shona", "Shona", "ZW"));
        langList.add(new LanguageModel("sd", "Sindhi", "سنڌي", "PK"));
        langList.add(new LanguageModel("sk", "Slovak", "Slovenský", "SK"));
        langList.add(new LanguageModel("sl", "Slovenian", "Slovenščina", "SL"));
        langList.add(new LanguageModel("so", "Somali", "Soomaali", "SO"));
        langList.add(new LanguageModel("es", "Spanish", "Español", "ES"));
        langList.add(new LanguageModel("su", "Sundanese", "Urang Sunda", "ID"));
        langList.add(new LanguageModel("sw", "Swahili", "Kiswahili", "CD"));
        langList.add(new LanguageModel("sv", "Swedish", "Svenska", "SE"));
        langList.add(new LanguageModel("tg", "Tajik", "Точик", "TJ"));
        langList.add(new LanguageModel("ta", "Tamil", "தமிழ்", "IN"));
        langList.add(new LanguageModel("te", "Telugu", "తెలుగు", "IN"));
        langList.add(new LanguageModel("th", "Thai", "ไทย", "TH"));
        langList.add(new LanguageModel("tr", "Turkish", "Türk", "TR"));
        langList.add(new LanguageModel("uk", "Ukrainian", "Українська", "UA"));
        langList.add(new LanguageModel("ur", "Urdu", "اردو", "PK"));
        langList.add(new LanguageModel("uz", "Uzbek", "O'zbek", "UZ"));
        langList.add(new LanguageModel("vi", "Vietnamese", "Tiếng Việt", "VN"));
        langList.add(new LanguageModel("cy", "Welsh", "Cymraeg", "GB"));
        langList.add(new LanguageModel("xh", "Xhosa", "isiXhosa", "ZA"));
        langList.add(new LanguageModel("yi", "Yiddish", "יידיש", "DE"));
        langList.add(new LanguageModel("yo", "Yoruba", "Yoruba", "NG"));
        langList.add(new LanguageModel("zu", "Zulu", "IsiZulu", "ZA"));

        return langList;
    }
}