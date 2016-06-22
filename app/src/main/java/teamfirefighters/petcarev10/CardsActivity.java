package teamfirefighters.petcarev10;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.wenchao.cardstack.CardStack;

import java.util.ArrayList;
import java.util.List;

public class CardsActivity extends AppCompatActivity {
    private static String Category_name;
    private static String Breed_name;
    private List<Card> getCardsFromDB(){
        List<Card> Cards = new ArrayList<Card>();
        Log.d("HELLO WORLD","IN GET CATEGORIES");
        CardDBHelper cdbhelper=new CardDBHelper(getApplicationContext());
        SQLiteDatabase db = cdbhelper.getReadableDatabase();
        String[] projection={
                CardDBContract.CardTable.COLUMN_NAME_SUBCLASSIFICATION,
                CardDBContract.CardTable.COLUMN_NAME_CLASSIFICATION,
                CardDBContract.CardTable.COLUMN_NAME_SUBSUBCLASSIFICATION,
                CardDBContract.CardTable.COLUMN_NAME_CARD_IMAGE,
                CardDBContract.CardTable.COLUMN_NAME_CARD_TEXT,
                CardDBContract.CardTable.COLUMN_NAME_CARD_LIST,
                CardDBContract.CardTable.COLUMN_NAME_CARD_TITLE,
                CardDBContract.CardTable.COLUMN_NAME_CARD_POSITION
        };
        String selection= CardDBContract.CardTable.COLUMN_NAME_SUBCLASSIFICATION+" =?";
        String[] selectionargs = {
                Breed_name};
        String sortOrder = CardDBContract.CardTable.COLUMN_NAME_CARD_POSITION + " ASC";
        Cursor c = db.query(CardDBContract.CardTable.TABLE_NAME,projection,selection,selectionargs,null,null,sortOrder);
        if(c!=null){
            Log.d("CARDACTIVITYCURSOR",c.toString());
            for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
                Card Temp = new Card(c);
                Cards.add(Temp);
                Log.d("RETREIVE CARD FROM DB",Temp.toString());
                Log.d("CARD TYPE", String.valueOf(Temp.getCardLayoutType()));
            }
        }else
            Log.d("ALL THIS SHIT","CURSOR IS NULL");
        return Cards;
    }


    private CardStack mCardStack;
    private CardDataAdapter mCardAdapter;
    public List<Card> cards = null;
    public int last_card_swiped = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);
        Intent foo = getIntent();
        Category_name = foo.getStringExtra("Category");
        Breed_name = foo.getStringExtra("Breed");
        Log.d("IN CARDS",Category_name);
        Log.d("IN CARDS",Breed_name);
        Log.d("LIST OF CARDS BABE",getCardsFromDB().toString());

        cards = getCardsFromDB();

        mCardStack = (CardStack)findViewById(R.id.container);
        mCardStack.setContentResource(R.layout.card_layout);
        mCardStack.setStackMargin(20);
        mCardAdapter = new CardDataAdapter(CardsActivity.this);

        for (int i=0; i< cards.size();i++)
            mCardAdapter.add(cards.get(i));

        mCardStack.setAdapter(mCardAdapter);




        mCardStack.setListener(new CardStack.CardEventListener() {
            @Override
            public boolean swipeEnd(int section, float distance) {

                if(distance>100.0)
                    return true;


                return false;
            }

            @Override
            public boolean swipeStart(int section, float distance) {

                return false;
            }

            @Override
            public boolean swipeContinue(int section, float distanceX, float distanceY) {

                return true;
            }

            @Override
            public void discarded(int mIndex, int direction) {
                last_card_swiped++;


            }

            @Override
            public void topCardTapped() {

                if(last_card_swiped>0){

                    mCardAdapter.insert(cards.get(last_card_swiped-1),0);



                    mCardStack.setAdapter(mCardAdapter);
                    last_card_swiped--;

                }

            }
        });

    }
}
