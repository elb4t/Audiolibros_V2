package tk.elb4t.audiolibros_V2;


/**
 * Created by eloy on 6/2/17.
 */

public class OpenDetailClickAction implements ClickAction {
    private final MainActivity mainActivity;

    public OpenDetailClickAction(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void execute(int position) {
        mainActivity.mostrarDetalle(String.valueOf(position));
    }

}
