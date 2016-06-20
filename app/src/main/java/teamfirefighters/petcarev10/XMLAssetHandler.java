package teamfirefighters.petcarev10;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.CursorJoiner;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by karm on 17/6/16.
 */
public class XMLAssetHandler extends AsyncTask<Void, Void, Void> {
    Context context;
    MainActivity activity;
    CardDBHelper cardbhelper;
    //HashSet<String> classifications;
    //HashSet<String> subclassifications;
    //HashSet<String> subsubclassifications;
    //HashSet<String> images;
    /*This is probably a stupid way to implement this, since it means that Image Names need to be Identical to SubClassificationNames
     * But it's computationally better than running through my list of cards to find the associated image
     * and easier than changing my parser to pick up the image or to use a String Array HashSet */
    ProgressDialog pd;
    public static final String SHARED_PREFERENCES_KEY = "petcareflags";

    public XMLAssetHandler(Context foo, MainActivity activity){//constructor to pass it an application context
        context = foo;
        this.activity = activity;
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
            writetodb(parser,Cards);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;//why does this function need a return?
    }

    private void writetodb(XMLPullParserHandler parser, List<Card> Cards) {
        //TODO Take a Parser Handler and Write Cards, Images, Classifications, SubClassifications and SubSubClassifications to db
        for(int i=0;i<Cards.size();i++){
            Card newb = Cards.get(i);
            Log.i("BOOBS CARDS",newb.toString());
            writetodb(newb);

        }
            //writetodb(card_iterator.next());

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
        Intent foo = new Intent(context,Home_Activity.class);
        foo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(foo);
    }

}
