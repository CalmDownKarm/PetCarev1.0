package teamfirefighters.petcarev10;

/**
 * Created by hitkul on 20/6/16.
 */
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class breed_list_view_adapter extends BaseAdapter {

    private Activity context;
    private List<String> list;
    private LayoutInflater mLayoutInflater = null;

    public breed_list_view_adapter(Activity context, List<String> list) {
        this.context = context;
        this.list = list;
        mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void refresh(List<String> newlist)
    {
        list.clear();
        list.addAll(newlist);
        this.notifyDataSetChanged();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {


        View rowView = view;
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = li.inflate(R.layout.breed_list_item, null);



        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

        txtTitle.setText((String) getItem(position));
        txtTitle.setTextSize((float)15.0);


        Resources res = context.getResources();
        String mDrawableName = "foo";
        int resID = res.getIdentifier(mDrawableName , "drawable", context.getPackageName());
        imageView.setImageResource(resID);

        return rowView;
    }

}
