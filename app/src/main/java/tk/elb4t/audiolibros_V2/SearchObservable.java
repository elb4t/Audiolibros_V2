package tk.elb4t.audiolibros_V2;

import android.widget.SearchView;

import java.util.Observable;

/**
 * Created by eloy on 6/2/17.
 */

public class SearchObservable extends Observable
        implements SearchView.OnQueryTextListener, android.support.v7.widget.SearchView.OnQueryTextListener {
    @Override
    public boolean onQueryTextChange(String query) { setChanged();
        notifyObservers(query);
        return true; }
    @Override
    public boolean onQueryTextSubmit(String query) { return false;
    }
}
