package teamfirefighters.petcarev10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    XMLAssetHandler abc;
    boolean DB_READY_FLAG = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();

        if(SharedPrefHelper.checkDBReady(getApplicationContext())) {//Data has been parsed
            Intent foo = new Intent(getApplicationContext(),Home_Activity.class);
            startActivity(foo);
            finish();

        }else {
            abc = new XMLAssetHandler(getApplicationContext(),MainActivity.this);
            abc.execute();
        }
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d("CANCELLED THREAD","stop");
        if(abc!=null){
            abc.cancel(true);
        }
    }



    @Override
    protected void onRestart(){
        super.onRestart();
        CardDBHelper cardDBHelper = new CardDBHelper(getApplicationContext());
        SQLiteDatabase db = cardDBHelper.getWritableDatabase();
        cardDBHelper.onUpgrade(db,1,1);
        if(SharedPrefHelper.checkDBReady(getApplicationContext())) {

            Intent foo = new Intent(getApplicationContext(),Home_Activity.class);
            startActivity(foo);
            finish();

        }else {
            abc = new XMLAssetHandler(getApplicationContext(),MainActivity.this);
            abc.execute();
        }

    }

}
