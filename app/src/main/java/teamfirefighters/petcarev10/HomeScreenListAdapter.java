package teamfirefighters.petcarev10;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hitkul on 26/6/16.
 */
public class HomeScreenListAdapter extends ArrayAdapter<Card> {

    private Activity context;
    private List<String> list;
    private LayoutInflater mLayoutInflater = null;

    public HomeScreenListAdapter(Activity context) {

        super(context,R.layout.card_layout_4);
        this.context = context;

    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {


            LayoutInflater inflater = context.getLayoutInflater();


            Typeface font_text = Typeface.createFromAsset(context.getAssets(), "robotoregular.ttf");
            view = inflater.inflate(R.layout.card_layout_4, null, true);

             TextView mTVItem = (TextView) view.findViewById(R.id.txt);
            CircularImageView mIMGItem = (CircularImageView) view.findViewById(R.id.img);


            if (getItem(position).getText() != null) {
               /* SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("     ").append(getItem(position).getText());
                builder.setSpan(new ImageSpan(context, R.drawable.test),
                        0, 1, 0);

                mTVItem.setText(builder);
*/

                mTVItem.setText(getItem(position).getText());
                mTVItem.setTypeface(font_text);
            }


           if (getItem(position).getImage() != null) {

               Resources res = context.getResources();
               String mDrawableName = getItem(position).getImage().replaceAll(" ", "_").toLowerCase();
               int resID = res.getIdentifier(mDrawableName, "drawable", context.getPackageName());

               Picasso.with(context).load(R.drawable.test).resize(100, 100).centerCrop().into(mIMGItem);


           }
            // viewHolder.mIMGItem.setImageResource(resID);

            return view;

    }

}


