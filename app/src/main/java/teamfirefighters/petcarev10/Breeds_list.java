package teamfirefighters.petcarev10;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Breeds_list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeds_list);

        String data = getIntent().getExtras().getString("Category");
        String Category_name = getIntent().getExtras().getString("Category");
        final TextView foo = (TextView) findViewById(R.id.textView);

        foo.setText(data);
    }
}
