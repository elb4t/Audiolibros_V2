package tk.elb4t.audiolibros_V2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;


/**
 * Created by eloy on 6/1/17.
 */

public class AdaptadorLibros
        extends RecyclerView.Adapter<AdaptadorLibros.ViewHolder> implements ChildEventListener {
    private LayoutInflater inflador; //Crea Layouts a partir del XML
    //protected Vector<Libro> vectorLibros; //Vector con libros a visualizar
    protected DatabaseReference booksReference;
    private Context contexto;
    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;
    private ClickAction clickAction = new EmptyClickAction();
    private OpenDetailClickAction longClickAction;
    private ArrayList<String> keys;
    private ArrayList<DataSnapshot> items;

    public AdaptadorLibros(Context contexto, DatabaseReference reference) {
        //super(Libro.class, R.layout.elemento_selector,AdaptadorLibros.ViewHolder.class, reference);
        keys = new ArrayList<String>();
        items = new ArrayList<DataSnapshot>();
        inflador = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.booksReference = reference;
        this.contexto = contexto;
        booksReference.addChildEventListener(this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        items.add(dataSnapshot);
        keys.add(dataSnapshot.getKey());
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        String key = dataSnapshot.getKey();
        int index = keys.indexOf(key);
        if (index != -1) {
            items.set(index, dataSnapshot);
            notifyItemChanged(index, dataSnapshot.getValue(Libro.class));
        }
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        String key = dataSnapshot.getKey();
        int index = keys.indexOf(key);
        if (index != -1) {
            keys.remove(index);
            items.remove(index);
            notifyItemRemoved(index);
        }
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView portada;
        public TextView titulo;

        public ViewHolder(View itemView) {
            super(itemView);
            portada = (ImageView) itemView.findViewById(R.id.portada);
            portada.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            titulo = (TextView) itemView.findViewById(R.id.titulo);
        }
    }

    // Creamos el ViewHolder con las vista de un elemento sin personalizar
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // Inflamos la vista desde el xml
        View v = inflador.inflate(R.layout.elemento_selector, null);
        //v.setOnClickListener(onClickListener);
        //v.setOnLongClickListener(onLongClickListener);
        return new ViewHolder(v);
    }


    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(ViewHolder holder, final int posicion) {
        Libro libro = getItem(posicion);
        holder.portada.setImageBitmap(getBitmapFromURL(libro.getRecursoImagen()));
        holder.titulo.setText(libro.getTitulo());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAction.execute(posicion);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickAction.execute(posicion);
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public DatabaseReference getRef(int pos) {
        return items.get(pos).getRef();
    }

    public Libro getItem(int pos) {
        return items.get(pos).getValue(Libro.class);
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnItemLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public static Bitmap getBitmapFromURL(final String src) {
        final Bitmap[] myBitmap = new Bitmap[1];

        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Log.e("src", src);
                    URL url = new URL(src);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.setRequestMethod("GET");
                    connection.connect();
                    Log.e("-----------", "" + connection.getResponseCode());
                    InputStream input = connection.getInputStream();
                    myBitmap[0] = BitmapFactory.decodeStream(input);
                    Log.e("Bitmap", "returned");

                } catch (IOException e) {
                    e.printStackTrace();
//                    Log.e("Exception", e.getMessage());
                }
            }
        });
        t.start();
        try {
            // Esperamos a que el Thread termine con la conexion para que no de error las variables con los datos
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return myBitmap[0];
    }

    public void setClickAction(ClickAction clickAction) {
        this.clickAction = clickAction;
    }

    public void activaEscuchadorLibros() {
        FirebaseDatabase.getInstance().goOnline();
    }

    public void desactivaEscuchadorLibros() {
        FirebaseDatabase.getInstance().goOffline();
    }

    public String getItemKey(int pos) { return keys.get(pos);
    }
    public Libro getItemByKey(String key) { int index = keys.indexOf(key);
        if (index != -1) {
            return items.get(index).getValue(Libro.class); } else {
            return null; }
    }
}