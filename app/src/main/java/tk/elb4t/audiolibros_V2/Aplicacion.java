package tk.elb4t.audiolibros_V2;

import android.app.Application;

import com.android.volley.toolbox.ImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Vector;

/**
 * Created by eloy on 4/1/17.
 */

public class Aplicacion extends Application {
    //private Vector<Libro> vectorLibros;
    private AdaptadorLibrosFiltro adaptador;
    private FirebaseAuth auth;
    private ImageLoader lectorImagenes = new ImageLoader(null, null);
    private final static String BOOKS_CHILD = "libros";
    private final static String USERS_CHILD = "usuarios";
    private DatabaseReference usersReference;

    @Override
    public void onCreate() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("libros");

        auth = FirebaseAuth.getInstance();

        //database.setPersistenceEnabled(true);
        DatabaseReference booksReference =
                database.getReference().child(BOOKS_CHILD);
        usersReference = database.getReference().child(USERS_CHILD);
        adaptador = new AdaptadorLibrosFiltro(this,booksReference);
    }

    public AdaptadorLibrosFiltro getAdaptador() {
        return adaptador;
    }


    public FirebaseAuth getAuth() {
        return auth;
    }

    public ImageLoader getLectorImagenes() {
        return lectorImagenes;
    }

    public DatabaseReference getUsersReference() {
        return usersReference;
    }
}