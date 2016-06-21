package teamfirefighters.petcarev10;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.nikoyuwono.toolbarpanel.ToolbarPanelLayout;
import com.nikoyuwono.toolbarpanel.ToolbarPanelListener;
import com.nirhart.parallaxscroll.views.ParallaxListView;

import java.util.ArrayList;
import java.util.List;

public class Breeds_list extends AppCompatActivity {


    public boolean isPanelOpen = false;
    public ToolbarPanelLayout toolbarPanelLayout;
    public boolean menu_button_state = true; //true: menu    false: arrow
    public cat_list_view_adapter cat_adapter;
    private List<String> breeds;
    private ParallaxListView breeds_list;
    private breed_list_view_adapter breeds_adapter;
    public String Category_name;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();


        setContentView(R.layout.activity_breeds_list);



        toolbarPanelLayout = (ToolbarPanelLayout) findViewById(R.id.sliding_down_toolbar_layout);

        final ImageButton menu_button = (ImageButton) findViewById(R.id.imageButton);
        final Toolbar toolbarView = (Toolbar) findViewById(R.id.toolbar);
        findViewById(R.id.panel).setY((float) getResources().getDimensionPixelSize(R.dimen.abc_action_bar_default_height_material));



        Category_name = getIntent().getExtras().getString("Category");
        final List<String> categories = getIntent().getStringArrayListExtra("Category_list");
        ListView Cat_list = (ListView) findViewById(R.id.cat_list);






        breeds = getBreedsFromDb(Category_name);
        breeds_list = (ParallaxListView) findViewById(R.id.breed_list);
        breeds_adapter = new breed_list_view_adapter(Breeds_list.this);

        set_breeds_list(breeds);

       // for (int i=0; i< breeds.size();i++)
         //   breeds_adapter.add(breeds.get(i));

//        breeds_list.setAdapter(breeds_adapter);

        set_category_list(categories, Cat_list);


        assert Cat_list != null;
        Cat_list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                breeds = getBreedsFromDb(cat_adapter.getItem(position));
                set_breeds_list(breeds);
                //breeds_adapter.clear();
                //for (int i=0; i< breeds.size();i++)
                  //  breeds_adapter.add(breeds.get(i));

               // breeds_list.setAdapter(breeds_adapter);
                //recreate();
                //breeds_list.refreshDrawableState();


               // ViewGroup foo = (ViewGroup) findViewById(R.id.sliding_down_toolbar_layout);
                //foo.invalidate();
               // Log.i("##########","##############");
                //breeds_list.requestLayout();
                // breeds_list.setVisibility(view.VISIBLE);
                toolbarPanelLayout.closePanel();
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


                isPanelOpen = true;

            }

            @Override
            public void onPanelSlide(Toolbar toolbar, View panelView, float slideOffset) {


                toolbar.setY((float) 0.0);

                findViewById(R.id.cat_list).setAlpha(slideOffset);

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

                isPanelOpen = false;

            }
        });



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

    private void set_breeds_list(List<String> list){
        breeds_adapter.clear();
        for (int i=0; i< list.size();i++)
            breeds_adapter.add(list.get(i));

        breeds_list.setAdapter(breeds_adapter);
    }

    private void set_category_list(List<String> categories, ListView listView){

        cat_adapter = new cat_list_view_adapter(Breeds_list.this);

        for (int i=0; i< categories.size();i++)
            cat_adapter.add(categories.get(i));

        listView.setAdapter(cat_adapter);

    }


    @Override
    public void onBackPressed(){
        if (isPanelOpen) {

            toolbarPanelLayout.closePanel();;
            return;
        } else {
            finish();
        }
        super.onBackPressed();
    }


}

