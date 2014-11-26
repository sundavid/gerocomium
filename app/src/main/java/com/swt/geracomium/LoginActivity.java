package com.swt.geracomium;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.Volley;
import com.swt.geracomium.entity.User;
import com.swt.geracomium.entity.Utils;
import com.swt.geracomium.util.CookieStringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;

import static android.util.Log.v;


/**
 * A login screen that offers login via email/password and via Google+ sign in.
 * <p/>
 * ************ IMPORTANT SETUP NOTES: ************
 * In order for Google+ sign in to work with your app, you must first go to:
 * https://developers.google.com/+/mobile/android/getting-started#step_1_enable_the_google_api
 * and follow the steps in "Step 1" to create an OAuth 2.0 client for your package.
 */
public class LoginActivity extends Activity {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private RequestQueue mVolleyQueue;
    HttpClientStack s;
    private void init() {
        WindowManager wm = this.getWindowManager();
        Utils.window_height = wm.getDefaultDisplay().getHeight();
        Utils.window_width = wm.getDefaultDisplay().getWidth();
        Utils.server_address = getString(R.string.server_address);
        mVolleyQueue = Volley.newRequestQueue(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);
        /*
        setup global settings
         */

        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        String username = settings.getString("username", "");
        String password = settings.getString("password", "");
        if (!username.isEmpty() && !password.isEmpty()) {
            this.loginWithCsrf(username, password);
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            this.loginWithCsrf(email, password);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    public void showProgress(final boolean show) {

    }

    public void onError(final String error, EditText view) {
        view.setError(error);
        view.requestFocus();
    }

    public void onError(final String error) {
        this.onError(error, mEmailView);
        this.hideProgress();
    }

    public void loginWithCsrf(final String username, final String password) {
        String login_url = Utils.server_address + "/api-auth/login/";
        final LoginActivity self = this;
        CookieStringRequest r = new CookieStringRequest(login_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        /*
                        Document document = Jsoup.parse(response);
                        for (Element element : document.body().getElementsByAttributeValue("name", "csrfmiddlewaretoken")) {
                            Utils.csrf_token = element.attr("value");
                        }
                        v("connect", "csrf token: " + Utils.csrf_token);
                        if (Utils.csrf_token.isEmpty())
                            self.onError(getString(R.string.connect_error));
                        else self.login(username, password);
                        */
                        self.login(username, password);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                self.onError(getString(R.string.connect_error));
            }
        }
        );
        mVolleyQueue.add(r);
        this.showProgress();
    }

    public void login(final String username, final String password) {
        showProgress(true);
        final User user = User.getUser();
        final LoginActivity self = this;

        this.mEmailView.setText(username);
        this.mPasswordView.setText(password);
        final SharedPreferences settings = getPreferences(MODE_PRIVATE);
        CookieStringRequest r = new CookieStringRequest(Request.Method.POST, Utils.server_address + "/api-auth/login/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            self.setCurrentUser(username);
                        } catch (Exception e) {
                            e.printStackTrace();
                            self.onError(getString(R.string.connect_error));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                self.onError(getString(R.string.error_incorrect_password));
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("csrfmiddlewaretoken", Utils.csrf_token);
                params.put("username", username);
                params.put("password", password);
                // params.put("next", "/account/profile?username=" + username);
                Log.v("params", params.toString());
                return params;
            }
        };
        mVolleyQueue.add(r);
    }

    public void setCurrentUser(final String username) {
        showProgress(true);
        final User user = User.getUser();
        final LoginActivity self = this;

        final SharedPreferences settings = getPreferences(MODE_PRIVATE);
        CookieStringRequest r = new CookieStringRequest(Utils.server_address + "/account/profile?username=" + username,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            user.parse(json);
                            Intent mIntent = new Intent(self, MainActivity.class);
                            startActivity(mIntent);
                        } catch (Exception e) {
                            e.printStackTrace();
                            self.onError(getString(R.string.connect_error));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                self.onError(getString(R.string.error_incorrect_password));
            }
        }
        );
        mVolleyQueue.add(r);
    }
}