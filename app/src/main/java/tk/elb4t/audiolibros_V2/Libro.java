package tk.elb4t.audiolibros_V2;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;



/**
 * Created by eloy on 4/1/17.
 */

public class Libro {
    private String titulo;
    private String autor;
    private String recursoImagen;
    private String urlAudio;
    private String genero; // Género literario
    private Boolean novedad; // Es una novedad
    private Map<String, Boolean> leido; // Leído por el usuario
    private final static String G_TODOS = "Todos los géneros";
    private final static String G_EPICO = "Poema épico";
    private final static String G_S_XIX = "Literatura siglo XIX";
    private final static String G_SUSPENSE = "Suspense";
    private final static String[] G_ARRAY = new String[]{G_TODOS, G_EPICO, G_S_XIX, G_SUSPENSE};
    private final static Libro LIBRO_EMPTY = new Libro("", "anónimo", "http://www.dcomg.upv.es/~jtomas/android/audiolibros/sin_portada.jpg", "", G_TODOS, true, null);


    public Libro(){}

    public Libro(String titulo, String autor, String recursoImagen,
                 String urlAudio, String genero, Boolean novedad, Map<String, Boolean> leido) {
        this.titulo = titulo;
        this.autor = autor;
        this.recursoImagen = recursoImagen;
        this.urlAudio = urlAudio;
        this.genero = genero;
        this.novedad = novedad;
        this.leido = new HashMap<String, Boolean>();
    }

    public static Vector<Libro> ejemploLibros(DatabaseReference myRef) {
        final String SERVIDOR =
                "http://www.dcomg.upv.es/~jtomas/android/audiolibros/";
        Vector<Libro> libros = new Vector<Libro>();


        libros.add(new Libro("Kappa", "Akutagawa",
                SERVIDOR + "kappa.jpg", SERVIDOR + "kappa.mp3",
                Libro.G_S_XIX, false, null));
        libros.add(new Libro("Avecilla", "Alas Clarín, Leopoldo",
                SERVIDOR + "avecilla.jpg", SERVIDOR + "avecilla.mp3",
                Libro.G_S_XIX, true, null));
        libros.add(new Libro("Divina Comedia", "Dante",
                SERVIDOR + "divina_comedia.jpg", SERVIDOR + "divina_comedia.mp3",
                Libro.G_EPICO, true, null));
        libros.add(new Libro("Viejo Pancho, El", "Alonso y Trelles, José",
                SERVIDOR + "viejo_pancho.jpg", SERVIDOR + "viejo_pancho.mp3",
                Libro.G_S_XIX, true, null));
        libros.add(new Libro("Canción de Rolando", "Anónimo",
                SERVIDOR + "cancion_rolando.jpg", SERVIDOR + "cancion_rolando.mp3",
                Libro.G_EPICO, false, null));
        libros.add(new Libro("Matrimonio de sabuesos", "Agata Christie",
                SERVIDOR + "matrim_sabuesos.jpg", SERVIDOR + "matrim_sabuesos.mp3",
                Libro.G_SUSPENSE, false, null));
        libros.add(new Libro("La iliada", "Homero",
                SERVIDOR + "la_iliada.jpg", SERVIDOR + "la_iliada.mp3",
                Libro.G_EPICO, true, null));
        return libros;
    }

    public boolean leidoPor(String userID) {
        if (this.leido != null) {
            return this.leido.keySet().contains(userID);
        } else {
            return false;
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getRecursoImagen() {
        return recursoImagen;
    }

    public void setRecursoImagen(String recursoImagen) {
        this.recursoImagen = recursoImagen;
    }

    public String getUrlAudio() {
        return urlAudio;
    }

    public void setUrlAudio(String urlAudio) {
        this.urlAudio = urlAudio;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Boolean getNovedad() {
        return novedad;
    }

    public void setNovedad(Boolean novedad) {
        this.novedad = novedad;
    }

    public Map<String, Boolean> getLeido() {
        return leido;
    }

    public void setLeido(Map<String, Boolean> leido) {
        this.leido = leido;
    }

    public static String getgTodos() {
        return G_TODOS;
    }

    public static String getgEpico() {
        return G_EPICO;
    }

    public static String getgSXix() {
        return G_S_XIX;
    }

    public static String getgSuspense() {
        return G_SUSPENSE;
    }

    public static String[] getgArray() {
        return G_ARRAY;
    }

    public static Libro getLibroEmpty() {
        return LIBRO_EMPTY;
    }

    public static class LibroBuilder {
        private String titulo = "";
        private String autor = "anónimo";
        private String urlImagen =
                "http://www.dcomg.upv.es/~jtomas/android/audiolibros/sin_portada.jpg";
        private String urlAudio = "";
        private String genero = G_TODOS;
        private boolean nuevo = true;
        private Map<String, Boolean> leido = null;

        public Libro build() {
            return new Libro(titulo, autor, urlImagen, urlAudio, genero, nuevo, leido);
        }

        public LibroBuilder withTitulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public LibroBuilder withAutor(String autor) {
            this.autor = autor;
            return this;
        }

        public LibroBuilder withUrlImagen(String urlImagen) {
            this.urlImagen = urlImagen;
            return this;
        }

        public LibroBuilder withUrlAudio(String urlAudio) {
            this.urlAudio = urlAudio;
            return this;
        }

        public LibroBuilder withGenero(String genero) {
            this.genero = genero;
            return this;
        }

        public LibroBuilder withNuevo(boolean nuevo) {
            this.nuevo = nuevo;
            return this;
        }

        public LibroBuilder withLeido(Map<String, Boolean> leido) {
            this.leido = leido;
            return this;
        }

    }
}

