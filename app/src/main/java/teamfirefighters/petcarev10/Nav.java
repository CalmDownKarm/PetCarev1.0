package teamfirefighters.petcarev10;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Nav extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Log.d("ACTIVITY_NAV","BLABLA");
        Log.d("HELLO WORLD","ARE YOU LISTENING");
        List<String> foo = getCategoriesFromDb();//Categories from DB
        Log.d("HELLO WORLD",foo.toString());
    }







    private List<String> getCategoriesFromDb(){
        List<String> Categories = new ArrayList<String>();
        if(checkDBFLAG()){//Check Flag Again to ensure that the database is ready
            Log.d("HELLO WORLD","IN GET CATEGORIES");
            CardDBHelper cdbhelper=new CardDBHelper(getApplicationContext());
            SQLiteDatabase db = cdbhelper.getReadableDatabase();
            String[] projection={
                    CardDBContract.CardTable.COLUMN_NAME_CLASSIFICATION
            };
            String sortOrder = CardDBContract.CardTable.COLUMN_NAME_CLASSIFICATION + " DESC";
            Cursor c = db.query(true, CardDBContract.CardTable.TABLE_NAME,projection,null,null,null,null,sortOrder,null);
            Log.d("TITS Cursor","SHIT");
            if(c!=null){
                Log.d("TITS Cursor",c.toString());
                for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
                    String temp = c.getString(c.getColumnIndexOrThrow(CardDBContract.CardTable.COLUMN_NAME_CLASSIFICATION));
                    Categories.add(temp);
                }
            }else
                Log.d("ALL THIS SHIT","CURSOR IS NULL");
        }
        Log.d("TITS",Categories.toString());
        return Categories;
    };
    public boolean checkDBFLAG(){
        SharedPreferences Flag = getApplication().getSharedPreferences(CardDBContract.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        return Flag.getBoolean("DB_READY",false);
    }

}
