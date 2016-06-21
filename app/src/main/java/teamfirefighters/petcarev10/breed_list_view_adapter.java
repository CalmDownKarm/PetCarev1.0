package teamfirefighters.petcarev10;

/**
 * Created by hitkul on 20/6/16.
 */
import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class breed_list_view_adapter extends ArrayAdapter<String> {

    private final Activity context;

    public breed_list_view_adapter(Activity context) {
        super(context, R.layout.breed_list_item);
        this.context = context;


    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {


        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.breed_list_item, null, true);


        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

        txtTitle.setText(getItem(position));
        txtTitle.setTextSize((float)30.0);


        Resources res = context.getResources();
        String mDrawableName = "foo";
        int resID = res.getIdentifier(mDrawableName , "drawable", context.getPackageName());
        imageView.setImageResource(resID);

        return rowView;
    }

}
