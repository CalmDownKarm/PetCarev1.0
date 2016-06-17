package teamfirefighters.petcarev10;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

/**
 * Created by karm on 17/6/16.
 */
public class XMLAssetHandler extends AsyncTask<Void, Void, Void> {
    Context context;
    CardDBHelper cardbhelper;

    public XMLAssetHandler(Context foo){//constructor to pass it an application context
        context = foo;
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

            //Write all cards to database
            for(int i=0;i<Cards.size();i++){
                Card temp = Cards.get(i);
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
        values.put(CardDBContract.CardTable.COLUMN_NAME_CARD_TITLE,temp.getTitle());
        values.put(CardDBContract.CardTable.COLUMN_NAME_CARD_TEXT,temp.getText());
        values.put(CardDBContract.CardTable.COLUMN_NAME_CARD_IMAGE,temp.getImage());
        values.put(CardDBContract.CardTable.COLUMN_NAME_CARD_LIST,temp.getList().toString());
        values.put(CardDBContract.CardTable.COLUMN_NAME_CLASSIFICATION,temp.getClassification());
        values.put(CardDBContract.CardTable.COLUMN_NAME_SUBCLASSIFICATION,temp.getSubclassification());

        long newRowId = db.insert(CardDBContract.CardTable.TABLE_NAME,
                CardDBContract.CardTable.COLUMN_NAME_CARD_TEXT,
                values);
    }
}
