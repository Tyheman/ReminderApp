package net.cit368.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //AccountHandler accountHandler = new AccountHandler();
    private static final int SIGNIN_CODE = 1;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSignin = findViewById(R.id.btn_signin);
        Button btnRegister = findViewById(R.id.btn_register);
        btnSignin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                createSigninIntent();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            }
        });

    }

    public void createSigninIntent(){
        List<AuthUI.IdpConfig> signinMethods = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build());
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(signinMethods).build(), SIGNIN_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGNIN_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                //TODO redirect to tasks page
                System.out.println("LOG IN SUCCESSFUL");
            } else {
                //TODO handle failed signin
                System.out.println("LOG IN FAILURE");
            }
        }
    }

    public void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        //TODO rediriect user to login activity
                        System.out.println("LOGGED OUT");
                    }
                });
    }

}
