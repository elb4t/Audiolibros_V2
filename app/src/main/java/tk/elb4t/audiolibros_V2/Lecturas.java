package tk.elb4t.audiolibros_V2;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by eloy on 9/2/17.
 */

public class Lecturas {
    private String UIDActual;
    private DatabaseReference referenciaMisLecturas;

    public Lecturas(String UIDActual, DatabaseReference referenciaMisLecturas) {
        this.UIDActual = UIDActual;
        this.referenciaMisLecturas = referenciaMisLecturas;
        UIDActual = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        referenciaMisLecturas = database.getReference().child("lecturas").child(UIDActual);
    }
}
