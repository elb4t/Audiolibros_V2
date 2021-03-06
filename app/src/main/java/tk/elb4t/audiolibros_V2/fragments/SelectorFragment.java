package tk.elb4t.audiolibros_V2.fragments;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.util.Vector;

import tk.elb4t.audiolibros_V2.AdaptadorLibros;
import tk.elb4t.audiolibros_V2.AdaptadorLibrosFiltro;
import tk.elb4t.audiolibros_V2.Aplicacion;
import tk.elb4t.audiolibros_V2.Libro;
import tk.elb4t.audiolibros_V2.MainActivity;
import tk.elb4t.audiolibros_V2.OpenDetailClickAction;
import tk.elb4t.audiolibros_V2.R;
import tk.elb4t.audiolibros_V2.SearchObservable;

/**
 * Created by eloy on 6/1/17.
 */

public class SelectorFragment extends Fragment implements Animation.AnimationListener, Animator.AnimatorListener {
    private Activity actividad;
    private RecyclerView recyclerView;
    private AdaptadorLibrosFiltro adaptador;
    //private Vector<Libro> vectorLibros;

    @Override
    public void onAttach(Context contexto) {
        super.onAttach(contexto);
        if (contexto instanceof Activity) {
            this.actividad = (Activity) contexto;
            Aplicacion app = (Aplicacion) actividad.getApplication();
            adaptador = app.getAdaptador();
            //vectorLibros = app.getVectorLibros();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {

        View vista = inflador.inflate(R.layout.fragment_selector, contenedor, false);
        recyclerView = (RecyclerView) vista.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(actividad, 2));
        recyclerView.setAdapter(adaptador);
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(2000);
        animator.setMoveDuration(2000);
        recyclerView.setItemAnimator(animator);
        /*adaptador.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) actividad).mostrarDetalle(
                         adaptador.getItemKey(
                                recyclerView.getChildAdapterPosition(v)));
                Toast.makeText(actividad, "Seleccionado el elemento: "
                        + recyclerView.getChildAdapterPosition(v), Toast.LENGTH_SHORT).show();
            }
        });*/
        adaptador.setClickAction(new OpenDetailClickAction((MainActivity)
                getActivity()));
        adaptador.setOnItemLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(final View v) {
                final int id = recyclerView.getChildAdapterPosition(v);
                AlertDialog.Builder menu = new AlertDialog.Builder(actividad);
                CharSequence[] opciones = {"Compartir", "Borrar ", "Insertar"};
                menu.setItems(opciones, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int opcion) {
                        switch (opcion) {
                            case 0: //Compartir
                                Libro libro = ((Aplicacion) getActivity().getApplication()).getAdaptador().getItem(id);
                                Intent i = new Intent(Intent.ACTION_SEND);
                                i.setType("text/plain");
                                i.putExtra(Intent.EXTRA_SUBJECT, libro.getTitulo());
                                i.putExtra(Intent.EXTRA_TEXT, libro.getUrlAudio());
                                Animator anim = AnimatorInflater.loadAnimator(actividad,
                                        R.animator.menguar);
                                anim.addListener(SelectorFragment.this);
                                anim.setTarget(v);
                                anim.start();
                                startActivity(Intent.createChooser(i, "Compartir"));
                                break;
                            case 1: //Borrar
                                Snackbar.make(v, "¿Estás seguro?", Snackbar.LENGTH_LONG)
                                        .setAction("SI", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Animation anim = AnimationUtils.loadAnimation(actividad,
                                                        R.anim.menguar);
                                                anim.setAnimationListener(SelectorFragment.this);
                                                v.startAnimation(anim);
                                                adaptador.borrar(id);
                                                //adaptador.notifyDataSetChanged();
                                            }
                                        })
                                        .show();
                                break;
                            case 2: //Insertar
                                int posicion = recyclerView.getChildLayoutPosition(v);
                                adaptador.insertar((Libro) adaptador.getItem(posicion));
                                //adaptador.notifyDataSetChanged();
                                adaptador.notifyItemInserted(adaptador.getItemCount() + 1);
                                Snackbar.make(v, "Libro insertado", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                }).show();
                                break;
                        }
                    }
                });
                menu.create().show();
                return true;
            }
        });
        setHasOptionsMenu(true);
        return vista;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_selector, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem searchItem = menu.findItem(R.id.menu_buscar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        SearchObservable searchObservable = new SearchObservable();
        searchObservable.addObserver(adaptador);
        searchView.setOnQueryTextListener(searchObservable);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                adaptador.setBusqueda("");
                adaptador.notifyDataSetChanged();
                return true; // Para permitir cierre
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true; // Para permitir expansión }
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_ultimo) {
            ((MainActivity) actividad).irUltimoVisitado();
            return true;
        } else if (id == R.id.menu_buscar) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        ((MainActivity) getActivity()).mostrarElementos(true);
        adaptador.activaEscuchadorLibros();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        adaptador.desactivaEscuchadorLibros();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        adaptador.notifyDataSetChanged();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
