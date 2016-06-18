package teamfirefighters.petcarev10;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.CursorJoiner;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

/**
 * Created by karm on 17/6/16.
 */
public class XMLAssetHandler extends AsyncTask<Void, Void, Void> {
    Context context;
    CardDBHelper cardbhelper;
    HashSet<String> classifications;
    HashSet<String> subclassifications;
    HashSet<String> subsubclassifications;
    ProgressDialog pd;
    public static final String SHARED_PREFERENCES_KEY = "petcareflags";

    public XMLAssetHandler(Context foo, MainActivity activity){//constructor to pass it an application context
        context = foo;
        pd=new ProgressDialog(activity);
    }
    @Override
    protected Void doInBackground(Void... params) {
        //this.context = params[0];

        this.cardbhelper= new CardDBHelper(context);
        List<Card> Cards = null;
        try {
            XMLPullParserHandler parser = new XMLPullParserHandler();
            //Populate Cards and call XML Handler
            Cards = parser.parse(context.getAssets().open("dogdataset.xml"));
            classifications = parser.getClassifications();
            Log.d("CLASSIFICATIONS",classifications.toString());
            subclassifications = parser.getSubclassifications();
            Log.d("SUBCLASSIFICATIONS",subclassifications.toString());

            subsubclassifications = parser.getSubsubclassifications();
            Log.d("SUBSUBCLASSIFICATIONS",subsubclassifications.toString());

            //TODO WRITE ALL OF THE 3 to DB
            //Write all cards to database
            for(int i=0;i<Cards.size();i++){
                Card temp = Cards.get(i);
                if(temp!=null)
                    writetodb(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;//why does this function need a return?
    }

    private void writetodb(Card temp) {
        SQLiteDatabase db = cardbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        if(temp!=null) {
            if (temp.getTitle()!=null)
                values.put(CardDBContract.CardTable.COLUMN_NAME_CARD_TITLE, temp.getTitle());
            if (temp.getText()!=null)
                values.put(CardDBContract.CardTable.COLUMN_NAME_CARD_TEXT, temp.getText());
            if (temp.getImage()!=null)
                values.put(CardDBContract.CardTable.COLUMN_NAME_CARD_IMAGE, temp.getImage());
            if (temp.getList()!=null)
                values.put(CardDBContract.CardTable.COLUMN_NAME_CARD_LIST, temp.getList().toString());
            if (temp.getClassification()!=null)
                values.put(CardDBContract.CardTable.COLUMN_NAME_CLASSIFICATION, temp.getClassification());
            if (temp.getSubclassification()!=null)
                values.put(CardDBContract.CardTable.COLUMN_NAME_SUBCLASSIFICATION, temp.getSubclassification());
            if (temp.getSubsubclassification()!=null)
                values.put(CardDBContract.CardTable.COLUMN_NAME_SUBSUBCLASSIFICATION, temp.getSubsubclassification());
            long newRowId = db.insert(CardDBContract.CardTable.TABLE_NAME,
                    CardDBContract.CardTable.COLUMN_NAME_CARD_TEXT,
                    values);
        }
        else{
            Log.d("CARD","CARD IS NULL");
        }
    }
    @Override
    protected void onPreExecute(){
        pd.setMessage("Parsing");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setIndeterminate(true);
        pd.show();

    }
    @Override
    protected void  onPostExecute(Void res){
        if(pd.isShowing())
            pd.dismiss();
        Log.d("TAG","DB PARSING DONE");
        SharedPreferences Flag = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Flag.edit();
        editor.putBoolean("DB_READY",true); //set flag to true in shared preferences so I don't reparse everytime
        editor.commit();
    }

}
