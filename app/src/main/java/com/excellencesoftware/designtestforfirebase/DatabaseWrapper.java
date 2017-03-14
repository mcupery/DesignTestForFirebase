package com.excellencesoftware.designtestforfirebase;

/**
 * Created by Marcia on 3/14/2017.
 */

public interface DatabaseWrapper {
    public void connect();

    public void disconnect();

    public void setRegistrationResultListener(RegistrationResultListener listener);

    public void removeRegistrationResultListener();

    public void createAccount(String email, String password);
}
