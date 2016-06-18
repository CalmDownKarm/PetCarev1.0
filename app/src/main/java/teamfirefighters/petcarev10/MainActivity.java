package teamfirefighters.petcarev10;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        if(DB_READY_FLAG) {//Data has been parsed
            Log.d("DB READY", "True");

        }else {
            XMLAssetHandler abc = new XMLAssetHandler(getApplicationContext(),MainActivity.this);
            abc.execute();
            Log.d("DB_READY", "Called ASYNC THREAD");
        }
        DB_READY_FLAG=Flag.getBoolean("DB_READY",false);
        if(DB_READY_FLAG){//Check Flag Again to ensure that the data
            CardDBHelper cdbhelper=new CardDBHelper(getApplicationContext());
            SQLiteDatabase db = cdbhelper.getReadableDatabase();
            String[] projection={
                    CardDBContract.CardTable.COLUMN_NAME_CARD_ID,
                    CardDBContract.CardTable.COLUMN_NAME_CLASSIFICATION,
                    CardDBContract.CardTable.COLUMN_NAME_SUBCLASSIFICATION,
                    CardDBContract.CardTable.COLUMN_NAME_SUBSUBCLASSIFICATION
            };
            String sortOrder = CardDBContract.CardTable.COLUMN_NAME_SUBSUBCLASSIFICATION + " DESC";
            Cursor c = db.query(CardDBContract.CardTable.TABLE_NAME,projection,null,null,null,null,sortOrder);
            if(c!=null){
                c.moveToFirst();
                int foo = c.getInt(c.getColumnIndexOrThrow(CardDBContract.CardTable.COLUMN_NAME_CARD_ID));
                String nerdy = c.getString(c.getColumnIndexOrThrow(CardDBContract.CardTable.COLUMN_NAME_CLASSIFICATION));
                Log.d("ALL THIS SHIT","CLASSIFICATION"+nerdy+"ID: "+foo);
            }else
                Log.d("ALL THIS SHIT","CURSOR IS NULL");


        }


    }
}
