package myproject.searchfilm.activity.base;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by Aliaksandr on 8/13/2017.
 */

public class BaseActivity extends AppCompatActivity {

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
