package teamfirefighters.petcarev10;


/**
 * Created by hitkul on 14/6/16.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CardDataAdapter extends ArrayAdapter<Card> {


    Activity context;

    public CardDataAdapter(Context context) {

            super(context, R.layout.card_layout_1);
            this.context = (Activity) context;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();
        Typeface font = Typeface.createFromAsset(context.getAssets(), "raleway.ttf");
    //TODO Delete list adapter
        switch (getItem(position).getCardLayoutType()){
            case 1:{

                contentView = inflater.inflate(R.layout.card_layout, null, true);
                ImageView dog_img = (ImageView) contentView.findViewById(R.id.dog_image);
                TextView dog_name = (TextView) contentView.findViewById(R.id.card_title);
                TextView dog_text = (TextView) contentView.findViewById(R.id.card_text);
                TextView card_list = (TextView) contentView.findViewById(R.id.card_list);

                if(getItem(position).getSubclassification() != null){
                dog_name.setText(getItem(position).getSubclassification());
                dog_name.setTypeface(font);
                    if(getItem(position).getSubclassification().equals("American Cocker Spaniel"))
                        dog_name.setTextSize((float) 27.0);
                }
                if(getItem(position).getText() != null){
                dog_text.setText(getItem(position).getText());
                dog_text.setTypeface(font);
                }



                if(getItem(position).getList_string() != null) {
                     card_list.setText(getItem(position).getList_string());
                    card_list.setTypeface(font);

                }

                if(getItem(position).getImage() !=null){

                    Resources res = context.getResources();
                    String mDrawableName = getItem(position).getImage().replaceAll(" ","_").toLowerCase();
                    int resID = res.getIdentifier(mDrawableName , "drawable", context.getPackageName());

                   //Picasso.with(context).load(resID).into(dog_img);

                    dog_img.setImageResource(resID);
                }





                break;

            }
            case 2:{

                contentView = inflater.inflate(R.layout.card_layout_1, null, true);
                TextView card_title = (TextView) contentView.findViewById(R.id.card_title);
                TextView card_list = (TextView) contentView.findViewById(R.id.card_list);

               if(getItem(position).getTitle()!= null) {
                   card_title.setText(getItem(position).getTitle());
                    card_title.setTypeface(font);
               }

                if(getItem(position).getList_string() != null) {

                    card_list.setText(getItem(position).getList_string());
                    card_list.setTypeface(font);


                }

                break;
            }
            case 3:{

                contentView = inflater.inflate(R.layout.card_layout_2, null, true);

                TextView dog_name = (TextView) contentView.findViewById(R.id.card_title);
                TextView dog_text = (TextView) contentView.findViewById(R.id.card_text);

                if(getItem(position).getTitle() != null){
                    dog_name.setText(getItem(position).getTitle());
                    dog_name.setTypeface(font);
                }
                if(getItem(position).getText() != null){
                    dog_text.setText(getItem(position).getText());
                    dog_text.setTypeface(font);
                }

                break;
            }
            case 4:{

                contentView = inflater.inflate(R.layout.card_layout_3, null, true);

                ImageView disease_img= (ImageView) contentView.findViewById(R.id.disease_image);
                TextView disease_name = (TextView) contentView.findViewById(R.id.card_title);
                TextView disease_text = (TextView) contentView.findViewById(R.id.card_text);

                if(getItem(position).getTitle() != null){
                    disease_name.setText(getItem(position).getTitle());
                    disease_name.setTypeface(font);
                }

                if(getItem(position).getText() != null){
                    disease_text.setText(getItem(position).getText());
                    disease_text.setTypeface(font);
                }

                if(getItem(position).getImage() !=null){

                    Resources res = context.getResources();
                    String mDrawableName = getItem(position).getImage().replaceAll(" ","_").toLowerCase();
                    int resID = res.getIdentifier(mDrawableName , "drawable", context.getPackageName());

                    Picasso.with(context)
                            .load(resID)
                            .into(disease_img);

                    //disease_img.setImageResource(resID);
                }

                break;
            }

            case 5:{

                contentView = inflater.inflate(R.layout.card_layout_4, null, true);

                ImageView card_img= (ImageView) contentView.findViewById(R.id.card_image);
                TextView card_text = (TextView) contentView.findViewById(R.id.card_text);


                if(getItem(position).getText() != null){
                    card_text.setText(getItem(position).getText());
                    card_text.setTypeface(font);
                }

                if(getItem(position).getImage() !=null){

                    Resources res = context.getResources();
                    String mDrawableName = getItem(position).getImage().replaceAll(" ","_").toLowerCase();
                    int resID = res.getIdentifier(mDrawableName , "drawable", context.getPackageName());

                    card_img.setImageResource(R.drawable.german_shepherd);

                }

                break;
            }
        }


        return contentView;
    }

}
