package tk.elb4t.audiolibros_V2;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by eloy on 6/2/17.
 */

public class LibroSharedPreferenceStorage implements LibroStorage{
    public static final String PREF_AUDIOLIBROS =
            "com.example.audiolibros_internal";
    public static final String KEY_ULTIMO_LIBRO = "ultimo";
    private final Context context;

    public LibroSharedPreferenceStorage(Context context) {
        this.context = context;
    }

    @Override
    public boolean hasLastBook() {
        return getPreference().contains(KEY_ULTIMO_LIBRO);
    }

    private SharedPreferences getPreference() {
        return context.getSharedPreferences(
                PREF_AUDIOLIBROS, Context.MODE_PRIVATE);
    }

    @Override
    public String getLastBook() {
        return getPreference().getString(KEY_ULTIMO_LIBRO, "-1");
    }
}
