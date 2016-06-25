package teamfirefighters.petcarev10;

/**
 * Created by hitkul on 20/6/16.
 */
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class breed_list_view_adapter extends ArrayAdapter<String> {

    private Activity context;
    private List<String> list;
    private LayoutInflater mLayoutInflater = null;

    public breed_list_view_adapter(Activity context) {


        super(context,R.layout.breed_list_item);
        this.context = context;

    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View rowView = view;
        CompleteListViewHolder viewHolder;


        if (rowView == null) {

            rowView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.breed_list_item, parent, false);

            viewHolder = new CompleteListViewHolder(rowView);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (CompleteListViewHolder) rowView.getTag();
        }

        Typeface font = Typeface.createFromAsset(context.getAssets(), "raleway.ttf");
        viewHolder.mTVItem.setText(getItem(position));
        viewHolder.mTVItem.setTypeface(font);

        Resources res = context.getResources();
        String mDrawableName = getItem(position).replaceAll(" ","_").toLowerCase();
        int resID = res.getIdentifier(mDrawableName , "drawable", context.getPackageName());

        Picasso.with(context).load(resID).resize(90,90).centerCrop().into(viewHolder.mIMGItem);

       // viewHolder.mIMGItem.setImageResource(resID);

        return rowView;
    }

}

class CompleteListViewHolder {
    public TextView mTVItem;
    public CircularImageView mIMGItem;
    public CompleteListViewHolder(View base) {
        mTVItem = (TextView) base.findViewById(R.id.txt);
        mIMGItem = (CircularImageView)base.findViewById(R.id.img);
    }
}
