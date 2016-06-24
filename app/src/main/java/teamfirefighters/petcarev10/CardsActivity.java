package teamfirefighters.petcarev10;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wenchao.cardstack.CardStack;

import java.util.ArrayList;
import java.util.List;

public class CardsActivity extends AppCompatActivity {
    private  String Category_name;
    private  String Breed_name;
    private List<Card> getCardsFromDB(){
        List<Card> Cards = new ArrayList<Card>();
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
        String selection= CardDBContract.CardTable.COLUMN_NAME_SUBCLASSIFICATION+" =? AND "+ CardDBContract.CardTable.COLUMN_NAME_CLASSIFICATION+" =?";
        String[] selectionargs = {
                Breed_name,
                Category_name
        };
        String sortOrder = CardDBContract.CardTable.COLUMN_NAME_CARD_POSITION + " ASC";
        Cursor c = db.query(CardDBContract.CardTable.TABLE_NAME,projection,selection,selectionargs,null,null,sortOrder);
        if(c!=null){
            for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
                Card Temp = new Card(c);
                Cards.add(Temp);

            }
        }
        db.close();
        return Cards;
    }


    private CardStack mCardStack;
    private CardDataAdapter mCardAdapter;
    public List<Card> cards = null;
    public int last_card_swiped = 0;
    public TextView cardCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_cards);
        Intent foo = getIntent();
        Category_name = foo.getStringExtra("Category");
        Breed_name = foo.getStringExtra("Breed");

        cards = getCardsFromDB();

        mCardStack = (CardStack)findViewById(R.id.container);
        mCardStack.setContentResource(R.layout.card_layout);
        mCardStack.setStackMargin(20);
        mCardAdapter = new CardDataAdapter(CardsActivity.this);

        for (int i=0; i< cards.size();i++)
            mCardAdapter.add(cards.get(i));

        mCardStack.setAdapter(mCardAdapter);


        ImageButton backButton = (ImageButton) findViewById(R.id.backButton);
        ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton);
        TextView breedName =(TextView) findViewById(R.id.breedName);
        cardCount =(TextView) findViewById(R.id.cardCount);
        Typeface font = Typeface.createFromAsset(getAssets(), "raleway.ttf");
        breedName.setText(Breed_name);
        breedName.setTypeface(font);
        cardCount.setText(last_card_swiped+1 +"/"+cards.size());
        cardCount.setTypeface(font);



        assert  backButton !=null;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        assert homeButton != null;
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CardsActivity.this ,Home_Activity.class);
                startActivity(i);
            }
        });



        mCardStack.setListener(new CardStack.CardEventListener() {
            @Override
            public boolean swipeEnd(int section, float distance) {

                if(last_card_swiped+1 == cards.size() )
                    return false;

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
                cardCount.setText(last_card_swiped+1 +"/"+cards.size());
                //TODO ADD SHARED FLAG For first time app use
                if(last_card_swiped == 1)
                    Toast.makeText(getApplicationContext(), "Tap to get previous card", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void topCardTapped() {

                if(last_card_swiped>0){

                    mCardAdapter.insert(cards.get(last_card_swiped-1),0);
                    mCardStack.setAdapter(mCardAdapter);
                    last_card_swiped--;
                    cardCount.setText(last_card_swiped+1 +"/"+cards.size());
                }

            }
        });

    }
}
