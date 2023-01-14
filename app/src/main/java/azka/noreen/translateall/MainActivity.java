package azka.noreen.translateall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolba);
        drawerLayout=findViewById(R.id.drawerLay);
        navigationView=findViewById(R.id.nv);

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
                    Toast.makeText(MainActivity.this, "Share Clicked", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else if(menuItemId==R.id.bookmarkedList){
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
}