package tk.elb4t.audiolibros_V2;

import android.app.Activity;
import android.os.Bundle;

import tk.elb4t.audiolibros_V2.fragments.PreferenciasFragment;

/**
 * Created by eloy on 9/1/17.
 */

public class PreferenciasActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.
                content, new PreferenciasFragment()).commit();
    }
}
