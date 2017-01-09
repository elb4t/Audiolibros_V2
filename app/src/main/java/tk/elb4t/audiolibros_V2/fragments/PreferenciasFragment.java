package tk.elb4t.audiolibros_V2.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import tk.elb4t.audiolibros_V2.R;

/**
 * Created by eloy on 9/1/17.
 */

public class PreferenciasFragment extends PreferenceFragment{
        @Override
        public void onCreate (Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
}
