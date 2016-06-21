package teamfirefighters.petcarev10;


/**
 * Created by hitkul on 14/6/16.
 */

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CardDataAdapter extends ArrayAdapter<Card> {


    Activity context;
    public CardDataAdapter(Context context) {

            super(context, R.layout.card_layout_1);
            this.context = (Activity) context;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();
        Log.d("sheep","####"+getItem(position).getCardLayoutType()+"    "+getItem(position).getSubsubclassification());
        switch (getItem(position).getCardLayoutType()){
            case 1:{
                contentView = inflater.inflate(R.layout.card_layout, null, true);
                ImageView dog_img = (ImageView) contentView.findViewById(R.id.dog_image);
                TextView dog_name = (TextView) contentView.findViewById(R.id.card_title);
                TextView dog_text = (TextView) contentView.findViewById(R.id.card_text);
                ListView card_list = (ListView) contentView.findViewById(R.id.card_list);

                if(getItem(position).getSubclassification() != null)
                dog_name.setText(getItem(position).getSubclassification());
                if(getItem(position).getText() != null)
                dog_text.setText(getItem(position).getText());

                if(getItem(position).getList() != null) {
                    ArrayAdapter adapter = new ArrayAdapter<String>(context, R.layout.card_list_item_layout, getItem(position).getList());
                    card_list.setAdapter(adapter);
                }

                dog_img.setImageResource(R.drawable.foo);

                break;

            }
            case 2:{
                contentView = inflater.inflate(R.layout.card_layout_1, null, true);
                TextView card_title = (TextView) contentView.findViewById(R.id.card_title);
                ListView card_list = (ListView) contentView.findViewById(R.id.card_list);

               if(getItem(position).getSubsubclassification()!= null)
                   card_title.setText(getItem(position).getSubsubclassification());


                if(getItem(position).getList() != null) {
                    ArrayAdapter adapter = new ArrayAdapter<String>(context, R.layout.card_list_item_layout, getItem(position).getList());
                    card_list.setAdapter(adapter);
                }

                break;
            }
            case 3:{
                contentView = inflater.inflate(R.layout.card_layout_2, null, true);

                TextView dog_name = (TextView) contentView.findViewById(R.id.card_title);
                TextView dog_text = (TextView) contentView.findViewById(R.id.card_text);

                if(getItem(position).getSubsubclassification() != null)
                    dog_name.setText(getItem(position).getSubsubclassification());
                if(getItem(position).getText() != null)
                    dog_text.setText(getItem(position).getText());

                break;
            }
            case 4:{
                contentView = inflater.inflate(R.layout.card_layout_3, null, true);

                ImageView disease_img= (ImageView) contentView.findViewById(R.id.disease_image);
                TextView disease_name = (TextView) contentView.findViewById(R.id.card_title);
                TextView disease_text = (TextView) contentView.findViewById(R.id.card_text);

                if(getItem(position).getSubsubclassification() != null)
                    disease_name.setText(getItem(position).getSubsubclassification());

                if(getItem(position).getText() != null)
                    disease_text.setText(getItem(position).getText());

                disease_img.setImageResource(R.drawable.foo);

                break;
            }
        }


        return contentView;
    }

}
