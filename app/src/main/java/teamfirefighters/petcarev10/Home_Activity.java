package teamfirefighters.petcarev10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.nikoyuwono.toolbarpanel.ToolbarPanelLayout;
import com.nikoyuwono.toolbarpanel.ToolbarPanelListener;

import java.util.ArrayList;
import java.util.List;

public class Home_Activity extends AppCompatActivity {


    public boolean isPanelOpen = false;
    public ToolbarPanelLayout toolbarPanelLayout;
    public boolean menu_button_state = true; //true: menu    false: arrow
    public TextView cat_tag;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();

        setContentView(R.layout.sliding_down_toolbar_layout);

        toolbarPanelLayout = (ToolbarPanelLayout) findViewById(R.id.sliding_down_toolbar_layout);

        final ImageButton menu_button = (ImageButton) findViewById(R.id.imageButton);
        final Toolbar toolbarView = (Toolbar) findViewById(R.id.toolbar);
        findViewById(R.id.panel).setY((float) getResources().getDimensionPixelSize(R.dimen.abc_action_bar_default_height_material));


        Typeface font = Typeface.createFromAsset(getAssets(), "raleway.ttf");
        cat_tag = (TextView) findViewById(R.id.cat_tag);
        cat_tag.setText("Categories");
        cat_tag.setTypeface(font);
        cat_tag.setAlpha((float)0.0);

        final cat_list_view_adapter adapter = new cat_list_view_adapter(Home_Activity.this);

        ListView listView = (ListView) findViewById(R.id.cat_list);

        final List<String> categories = getCategoriesFromDb();//Categories from DB

        for (int i=0; i< categories.size();i++)
            adapter.add(categories.get(i));

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(adapter.getItem(position).equals("Diet")){
                    Intent i = new Intent(Home_Activity.this, CardsActivity.class);
                    i.putExtra("Category", adapter.getItem(position));
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

                isPanelOpen = true;
            }

            @Override
            public void onPanelSlide(Toolbar toolbar, View panelView, float slideOffset) {


                toolbar.setY((float) 0.0);

                findViewById(R.id.cat_list).setAlpha(slideOffset);
                cat_tag.setAlpha(slideOffset);

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


    private List<String> getCategoriesFromDb(){
        List<String> Categories = new ArrayList<String>();
        if(checkDBFLAG()){//Check Flag Again to ensure that the database is ready
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
    public boolean checkDBFLAG(){
        SharedPreferences Flag = getApplication().getSharedPreferences(CardDBContract.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        return Flag.getBoolean("DB_READY",false);
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
}
