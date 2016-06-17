package teamfirefighters.petcarev10;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREFERENCES_KEY = "petcareflags";
    boolean DB_READY_FLAG = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO Add Flag and intent to tutorial activity. Store Flag into Shared Preferences
        SharedPreferences Flag = getApplication().getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        /*SharedPreferences.Editor editor = Flag.edit();
        editor.putBoolean("DB_READY",true);
        editor.commit();*/

        DB_READY_FLAG=Flag.getBoolean("DB_READY",false);
        if(DB_READY_FLAG)//Data has been parsed
            Log.d("DB READY","True");
        else {
            //Start AsyncThread to Parse.
            Log.d("DB_READY", "False");
        }

    }
}
