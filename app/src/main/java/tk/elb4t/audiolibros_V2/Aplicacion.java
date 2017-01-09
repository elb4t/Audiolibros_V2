package tk.elb4t.audiolibros_V2;

import android.app.Application;

import java.util.Vector;

/**
 * Created by eloy on 4/1/17.
 */

public class Aplicacion extends Application {
    private Vector<Libro> vectorLibros;
    private AdaptadorLibrosFiltro adaptador;

    @Override
    public void onCreate() {
        vectorLibros = Libro.ejemploLibros();
        adaptador = new AdaptadorLibrosFiltro(this, vectorLibros);
    }

    public AdaptadorLibrosFiltro getAdaptador() {
        return adaptador;
    }

    public Vector<Libro> getVectorLibros() {
        return vectorLibros;
    }
}