package teamfirefighters.petcarev10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nikoyuwono.toolbarpanel.ToolbarPanelLayout;
import com.nikoyuwono.toolbarpanel.ToolbarPanelListener;
import com.wenchao.cardstack.CardStack;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Home_Activity extends AppCompatActivity {


    public boolean isPanelOpen = false;
    public ToolbarPanelLayout toolbarPanelLayout;
    public boolean menu_button_state = true; //true: menu    false: arrow
    public TextView cat_tag;
    private CardStack mCardStack;
    private CardDataAdapter mCardAdapter;
    public List<Card> cards = null;
    private  String Category_name = "Famous Dogs";
    private  String Breed_name = "Famous Dogs";


    //TODO CHECK TO SEE IF CATEGORY NAME AND BREED NAME HAVE BEEN STORED
    public int last_card_swiped = 0;
    public TextView cardCount;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();

        setContentView(R.layout.sliding_down_toolbar_layout);

        toolbarPanelLayout = (ToolbarPanelLayout) findViewById(R.id.sliding_down_toolbar_layout);

        final ImageButton menu_button = (ImageButton) findViewById(R.id.imageButton);
        ImageButton searchButton = (ImageButton) findViewById(R.id.searchButton);
        final Toolbar toolbarView = (Toolbar) findViewById(R.id.toolbar);
        findViewById(R.id.panel).setY((float) getResources().getDimensionPixelSize(R.dimen.abc_action_bar_default_height_material));
        Typeface font = Typeface.createFromAsset(getAssets(), "raleway.ttf");
        cat_tag = (TextView) findViewById(R.id.cat_tag);
        cat_tag.setText("Home");
        cat_tag.setTypeface(font);
        if(false/*SharedPrefHelper.Checkif5Recent(getApplicationContext())*/){
            //TODO LOAD RECENT 5
        }
        else{
            /*TODO SET CATEGORY AND BREED NAME TO FAMOUS DOGS, ALREADY INITIALISED SO THIS NEED NOT DO ANYTHING*/}

        cardCount =(TextView) findViewById(R.id.cardCount);
        final cat_list_view_adapter adapter = new cat_list_view_adapter(Home_Activity.this);

        ListView listView = (ListView) findViewById(R.id.cat_list);

        final List<String> categories = getCategoriesFromDb();//Categories from DB

        for (int i=0; i< categories.size();i++){
            if(!categories.get(i).equalsIgnoreCase("famous dogs"))
            adapter.add(categories.get(i));
        }

        listView.setAdapter(adapter);
        //check_for_last_Swiped();//Checks Shared Preferences
        cards = getCardsFromDB();
        mCardStack = (CardStack)findViewById(R.id.container);
        mCardStack.setContentResource(R.layout.card_layout);
        mCardStack.setStackMargin(10);
        mCardAdapter = new CardDataAdapter(Home_Activity.this);

        for (int i=0; i< cards.size();i++)
            mCardAdapter.add(cards.get(i));

        mCardStack.setAdapter(mCardAdapter);

        cardCount.setText(last_card_swiped+1 +"/"+cards.size());
        cardCount.setTypeface(font);

        assert searchButton!=null;
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSearch = new Intent(Home_Activity.this, search.class);
                startActivity(toSearch);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(adapter.getItem(position).equals("Diet")){
                    Intent i = new Intent(Home_Activity.this, CardsActivity.class);
                    i.putExtra("Category", adapter.getItem(position));
                    i.putExtra("Breed", adapter.getItem(position));
                    startActivity(i);
                }else{
                    Intent i = new Intent(Home_Activity.this, Breeds_list.class);
                    i.putExtra("Category", adapter.getItem(position));
                    i.putStringArrayListExtra("Category_list", (ArrayList<String>) categories);
                    startActivity(i);
                }

            }
        });




        assert toolbarView != null;
        toolbarView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (isPanelOpen)
                            toolbarPanelLayout.closePanel();
                        if (!isPanelOpen)
                            toolbarPanelLayout.openPanel();
                    }
                }
        );


        assert menu_button!=null;
        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPanelOpen)
                    toolbarPanelLayout.closePanel();
                if (!isPanelOpen)
                    toolbarPanelLayout.openPanel();

            }
        });



        assert toolbarPanelLayout != null;
        toolbarPanelLayout.setToolbarPanelListener(new ToolbarPanelListener() {
            @Override
            public void onPanelOpened(Toolbar toolbar, View panelView) {
                cat_tag.setText("Categories");
                isPanelOpen = true;
            }

            @Override
            public void onPanelSlide(Toolbar toolbar, View panelView, float slideOffset) {


                toolbar.setY((float) 0.0);

                findViewById(R.id.cat_list).setAlpha(slideOffset);
                if(slideOffset<0.5){
                    cat_tag.setText("Home");
                    cat_tag.setAlpha(1-slideOffset);}



                if(slideOffset>=0.5){
                    cat_tag.setText("Categories");
                    cat_tag.setAlpha(slideOffset);}

                if(slideOffset>0.3 && !isPanelOpen){
                    menu_button_state = false;
                    menu_button.setBackgroundResource(R.drawable.menu_to_arrow);
                    ((AnimationDrawable) menu_button.getBackground()).start();

                }

                if(slideOffset<0.7 && isPanelOpen){
                    menu_button_state = true;
                    menu_button.setBackgroundResource(R.drawable.arrow_to_menu);
                    ((AnimationDrawable) menu_button.getBackground()).start();

                }

                if (slideOffset == 0.0)
                    onPanelClosed(toolbar, panelView);

                if (slideOffset == 1.0)
                    onPanelOpened(toolbar, panelView);


            }

            @Override
            public void onPanelClosed(Toolbar toolbar, View panelView) {

                if(!menu_button_state){
                    menu_button.setBackgroundResource(R.drawable.arrow_to_menu);
                    ((AnimationDrawable) menu_button.getBackground()).start();
                    menu_button_state = true;
                }


                panelView.setY((float) -(panelView.getHeight()-toolbarView.getHeight()));

                cat_tag.setText("Home");
                cat_tag.setAlpha((float) 1.0);

                isPanelOpen = false;

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
                int num_used=SharedPrefHelper.getNumUsed(getApplicationContext());
                cardCount.setText(last_card_swiped+1 +"/"+cards.size());

                if((last_card_swiped) == 1&&(num_used<5)) {
                    Toast.makeText(getApplicationContext(), "Tap to get previous card", Toast.LENGTH_SHORT).show();
                    SharedPrefHelper.incrementNumUsed(getApplicationContext());
                }

            }

            @Override
            public void topCardTapped() {

                if(last_card_swiped>0){

                    mCardAdapter.insert(cards.get(last_card_swiped-1),0);
                    mCardStack.setAdapter(mCardAdapter);
                    last_card_swiped--;
                    cardCount.setText(last_card_swiped+1 +"/"+cards.size());

                }
                mCardStack.setStackMargin(10);
            }
        });



    }


    private List<String> getCategoriesFromDb(){
        List<String> Categories = new ArrayList<String>();
        if(SharedPrefHelper.checkDBReady(getApplicationContext())){//Check Flag Again to ensure that the database is ready
            CardDBHelper cdbhelper=new CardDBHelper(getApplicationContext());
            SQLiteDatabase db = cdbhelper.getReadableDatabase();
            String[] projection={
                    CardDBContract.CardTable.COLUMN_NAME_CLASSIFICATION
            };
            String sortOrder = CardDBContract.CardTable.COLUMN_NAME_CLASSIFICATION + " DESC";
            Cursor c = db.query(true, CardDBContract.CardTable.TABLE_NAME,projection,null,null,null,null,sortOrder,null);

            if(c!=null){

                for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
                    String temp = c.getString(c.getColumnIndexOrThrow(CardDBContract.CardTable.COLUMN_NAME_CLASSIFICATION));
                    Categories.add(temp);
                }
            }
            db.close();
        }


        return Categories;
    }




    @Override
    public void onBackPressed(){
        if (isPanelOpen) {

            toolbarPanelLayout.closePanel();;
            return;
        } else {
            finish();
        }
    }

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
}
