package com.excellencesoftware.designtestforfirebase;


import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Marcia on 3/14/2017.
 */

public class FirebaseWrapper implements DatabaseWrapper {

    //Firebase objects
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;

    private String userDisplayName = "";
    private boolean isLoggedIn = false;
    private String userId = "";
    private RegistrationResultListener mRegistrationResultListener = null;

    public FirebaseWrapper() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = mFirebaseAuth.getCurrentUser();
                if (mUser != null) {
                    isLoggedIn = true;
                    userDisplayName = mUser.getDisplayName();
                    userId = mUser.getUid();


                } else {
                    isLoggedIn = false;
                    userDisplayName = "";
                }

            }
        };

    }

    public void connect() {
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    public void disconnect() {
        if (mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void setRegistrationResultListener(RegistrationResultListener listener) {
        this.mRegistrationResultListener = listener;
    }

    public void removeRegistrationResultListener() {
        this.mRegistrationResultListener = null;
    }

    public void createAccount (String email, String password) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (mRegistrationResultListener != null) {
                                mRegistrationResultListener.onComplete("Registration successful.");
                            } else {
                                mRegistrationResultListener.onComplete("Registration failed.");
                            }
                        }
                    }
                });
    }


}
