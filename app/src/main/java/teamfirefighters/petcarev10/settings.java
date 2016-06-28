package teamfirefighters.petcarev10;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_settings);

        final ImageButton backButton = (ImageButton) findViewById(R.id.backButton);
        final TextView title = (TextView) findViewById(R.id.title_settings);
        final TextView clearRecent = (TextView) findViewById(R.id.clearRecent);
        final Switch notificationSwitch = (Switch) findViewById(R.id.Notification_switch);
        Typeface font = Typeface.createFromAsset(getAssets(), "raleway.ttf");
        title.setTypeface(font);
        clearRecent.setTypeface(font);
        notificationSwitch.setTypeface(font);

        notificationSwitch.setChecked(true);


        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    Toast.makeText(getApplicationContext(),"on",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"off",Toast.LENGTH_SHORT).show();

                    Intent notificationIntent = new Intent(settings.this, NotificationPublisher.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(settings.this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);
                }
            }
        });

        clearRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefHelper.clearString(getApplicationContext(),SharedPrefHelper.LAST_BREED_SWIPED);
                SharedPrefHelper.clearString(getApplicationContext(),SharedPrefHelper.LAST_CATEGORY_SWIPED);
                Log.i("boobs", SharedPrefHelper.ReturnRecentCategories(getApplicationContext()).toString());
            }
        });


        assert backButton != null;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}
