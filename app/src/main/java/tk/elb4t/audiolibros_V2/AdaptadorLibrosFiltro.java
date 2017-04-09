package tk.elb4t.audiolibros_V2;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * Created by eloy on 9/1/17.
 */

public class AdaptadorLibrosFiltro extends AdaptadorLibros implements Observer {
    //private Vector<Libro> vectorSinFiltro;// Vector con todos los libros
    private Vector<Integer> indiceFiltro;
    private String busqueda = "";
    private String genero = "";
    private boolean novedad = false;
    private boolean leido = false;
    private int librosUltimoFiltro;

    public AdaptadorLibrosFiltro(Context contexto,
                                 DatabaseReference reference) {
        super(contexto, reference);
        recalculaFiltro();
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda.toLowerCase();
        recalculaFiltro();
    }

    public void setGenero(String genero) {
        this.genero = genero;
        recalculaFiltro();
    }

    public void setNovedad(boolean novedad) {
        this.novedad = novedad;
        recalculaFiltro();
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
        recalculaFiltro();
    }

    public void recalculaFiltro() {
        librosUltimoFiltro = super.getItemCount();
        indiceFiltro = new Vector<Integer>();
        for (int i = 0; i < librosUltimoFiltro; i++) {
            Libro libro = super.getItem(i);
            if ((libro.getTitulo().toLowerCase().contains(busqueda) ||
                    libro.getAutor().toLowerCase().contains(busqueda)) && (libro.getGenero().startsWith(genero))
                    && (!novedad || (novedad && libro.getNovedad()))
                    && (!leido || (leido /*&& libro.leido*/))) {
//                vectorLibros.add(libro);
                indiceFiltro.add(i);
            }
        }
    }

    public Libro getItem(int posicion) {
        if (librosUltimoFiltro != super.getItemCount()) {
            recalculaFiltro();
        }
        return super.getItem(indiceFiltro.elementAt(posicion));
    }

    public Libro getItemById(int id) {
        return super.getItem(id);
    }

    public int getItemCount() {
        if (librosUltimoFiltro != super.getItemCount()) {
            recalculaFiltro();
        }
        return indiceFiltro.size();
    }

    public long getItemId(int posicion) { //Este no cambia
        return indiceFiltro.elementAt(posicion); //Se incluye por claridad
    }

    public void borrar(int posicion) {
        DatabaseReference referencia=getRef(indiceFiltro.elementAt(posicion)); referencia.removeValue();
        recalculaFiltro();
    }

    public void insertar(Libro libro) {
        booksReference.push().setValue(libro);
        recalculaFiltro();
    }

    @Override
    public void update(Observable observable, Object data) {
        setBusqueda((String) data);
        notifyDataSetChanged();
    }
    public String getItemKey(int posicion) {
        if (librosUltimoFiltro != super.getItemCount()) {
            recalculaFiltro();
        }
        int id = indiceFiltro.elementAt(posicion);
        return super.getItemKey(id); }
}
