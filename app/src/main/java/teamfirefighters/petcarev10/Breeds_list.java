package teamfirefighters.petcarev10;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Breeds_list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeds_list);

        String data = getIntent().getExtras().getString("Category");
        String Category_name = getIntent().getExtras().getString("Category");
        getBreedsFromDb(Category_name);
    }
    private List<String> getBreedsFromDb(String foo){
        List<String> Breeds = new ArrayList<String>();
        //Check Flag Again to ensure that the database is ready
            Log.d("HELLO WORLD","IN GET BREEDS");
            CardDBHelper cdbhelper=new CardDBHelper(getApplicationContext());
            SQLiteDatabase db = cdbhelper.getReadableDatabase();
            String[] projection={
                    CardDBContract.CardTable.COLUMN_NAME_SUBCLASSIFICATION
            };
            String selection = CardDBContract.CardTable.COLUMN_NAME_CLASSIFICATION+"=?";
            String[] selectionArgs = {foo};
            String sortOrder = CardDBContract.CardTable.COLUMN_NAME_SUBCLASSIFICATION + " DESC";
            Cursor c = db.query(true, CardDBContract.CardTable.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder,null);
            Log.d("TITS Cursor","SHIT IN BREEDS");
            if(c!=null){
                Log.d("TITS Cursor BREEDS",c.toString());
                for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
                    String temp = c.getString(c.getColumnIndexOrThrow(CardDBContract.CardTable.COLUMN_NAME_SUBCLASSIFICATION));
                    Breeds.add(temp);
                }
        }
        Log.d("ALL BREEDS",Breeds.toString());
        return Breeds;
    }


}

