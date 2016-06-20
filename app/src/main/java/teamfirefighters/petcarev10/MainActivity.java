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
    public boolean checkDBFLAG(){
        SharedPreferences Flag = getApplication().getSharedPreferences(CardDBContract.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        return Flag.getBoolean("DB_READY",false);
    }

    boolean DB_READY_FLAG = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO Add Flag and intent to tutorial activity. Store Flag into Shared Preferences
        if(checkDBFLAG()) {//Data has been parsed
            Log.d("DB READY", "True");
            Intent foo = new Intent(getApplicationContext(),Home_Activity.class);
            startActivity(foo);

        }else {
            XMLAssetHandler abc = new XMLAssetHandler(getApplicationContext(),MainActivity.this);
            abc.execute();
            Log.d("DB_READY", "Called ASYNC THREAD");
        }
    }
}
