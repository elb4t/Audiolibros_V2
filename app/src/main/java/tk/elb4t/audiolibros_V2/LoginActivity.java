package tk.elb4t.audiolibros_V2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by eloy on 8/2/17.
 */

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = ((Aplicacion) getApplicationContext()).getAuth();
        doLogin();
    }

    private void doLogin() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            guardarUsuario(currentUser);
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            String provider = currentUser.getProviders().get(0);
            SharedPreferences pref = getSharedPreferences(
                    "com.example.audiolibros_internal", MODE_PRIVATE);
            pref.edit().putString("provider", provider).commit();
            pref.edit().putString("name", name).commit();
            if (email != null) {
                pref.edit().putString("email", email).commit();
            }
            //if (currentUser.isEmailVerified()) {
                Intent i = new Intent(this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            /*} else {
                currentUser.sendEmailVerification();
                Toast.makeText(this, "Se ha mandado un email para verificar la cuenta", Toast.LENGTH_LONG);
            }*/

        } else {
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder().setProviders(Arrays.asList(
                            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(), new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(), new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build()))
                    .setIsSmartLockEnabled(true).build(), RC_SIGN_IN);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == ResultCodes.OK) {
                doLogin();
                finish();
            }
        }
    }
    void guardarUsuario(final FirebaseUser user) {
        DatabaseReference userReference = ((Aplicacion) getApplicationContext())
                .getUsersReference().child(user.getUid());
        userReference.setValue(new User(user.getDisplayName(), user.getEmail()));
    }
}
