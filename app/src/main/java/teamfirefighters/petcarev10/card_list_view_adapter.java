package teamfirefighters.petcarev10;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by hitkul on 22/6/16.
 */
public class card_list_view_adapter extends ArrayAdapter<String> {


    private final Activity context;


    public card_list_view_adapter(Activity context) {
        super(context, R.layout.cat_list_item_layout);
        this.context = context;

    }


    @Override
    public View getView(int position, View rowview, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        rowview= inflater.inflate(R.layout.card_list_item_layout, null, true);

        TextView list_item = (TextView) rowview.findViewById(R.id.card_list_item);
       // Typeface font = Typeface.createFromAsset(context.getAssets(), "raleway.ttf");
        list_item.setText(getItem(position));
       // list_item.setTypeface(font);

        return rowview;
    }


}
