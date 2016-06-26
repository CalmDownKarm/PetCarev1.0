package teamfirefighters.petcarev10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    boolean DB_READY_FLAG = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SharedPrefHelper.checkDBReady(getApplicationContext())) {//Data has been parsed
            Intent foo = new Intent(getApplicationContext(),Home_Activity.class);
            startActivity(foo);
            finish();

        }else {
            XMLAssetHandler abc = new XMLAssetHandler(getApplicationContext(),MainActivity.this);
            abc.execute();
        }
        setContentView(R.layout.activity_main);

    }
}
