package com.teachme.teachmeclient.Views.simple_views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.app.LoaderManager.LoaderCallbacks;


import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.net.MalformedURLException;

import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceAuthenticationProvider;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceActivityResult;
import com.teachme.teachmeclient.Entities.AuthTokenCacheManager;
import com.teachme.teachmeclient.Adapters.AzureServiceAdapter;
import com.teachme.teachmeclient.BaseActivity.BaseActivity;
import com.teachme.teachmeclient.Entities.CachedUser;
import com.teachme.teachmeclient.Entities.LoginResult;
import com.teachme.teachmeclient.Entities.LoginViewModel;
import com.teachme.teachmeclient.Entities.User;
import com.teachme.teachmeclient.Models.GlobalConstants;
import com.teachme.teachmeclient.R;
import com.teachme.teachmeclient.Entities.RestClient;
import com.teachme.teachmeclient.Views.CoursesActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements LoaderCallbacks<Cursor> {

    //private BackendService restService;

    /**
     * Mobile Service Client reference
     */
    private MobileServiceClient mClient;

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    // You can choose any unique number here to differentiate auth providers from each other. Note this is the same code at login() and onActivityResult().
    public static final int FACEBOOK_LOGIN_REQUEST_CODE = 1;
    public static final int GOOGLE_LOGIN_REQUEST_CODE = 2;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        try {
            AzureServiceAdapter.Initialize(this);
        } catch (MalformedURLException e) {
            createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
        } catch (Exception e) {
            createAndShowDialog(e, "Error");
        }

        isTokenPresent();

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mEmailView.setText("teachme@gmail.com");
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setText("password");
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button facebookSignInButton = (Button) findViewById(R.id.facebook_sign_in_button);
        facebookSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateFacebook();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    //Creates a dialog and shows it
    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if (exception.getCause() != null) {
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }

    //Creates a dialog and shows it
    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }

    private void authenticateFacebook() {
        // Login using the Facebook provider.
        AzureServiceAdapter.getInstance().getClient().login(MobileServiceAuthenticationProvider.Facebook, GlobalConstants.URL_SCHEME, FACEBOOK_LOGIN_REQUEST_CODE);
    }

    private String findProviderFromLoginRequestCode(int requestCode) {
        String provider;
        switch (requestCode) {
            case GOOGLE_LOGIN_REQUEST_CODE:
                provider = "Google";
                break;
            case FACEBOOK_LOGIN_REQUEST_CODE:
                provider = "Facebook";
                break;
            default:
                throw new IllegalArgumentException("request code does not match any provider");
        }
        return provider;
    }

    private void isTokenPresent(){
        //AuthTokenCacheManager.cleanUserToken(this);
        CachedUser cachedUser = AuthTokenCacheManager.loadUserTokenCache(this);
        if (cachedUser.getAuthToken() != null && !cachedUser.getAuthToken().isEmpty()) {
            RestClient.initService(cachedUser.getAuthToken());
            Toast.makeText(this, "Hello " + cachedUser.getUserName() + "!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        LoginViewModel loginViewModel = new LoginViewModel();
        loginViewModel.setEmail(mEmailView.getText().toString());
        loginViewModel.setPassword(mPasswordView.getText().toString());

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(loginViewModel.getPassword()) && !isPasswordValid(loginViewModel.getPassword())) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(loginViewModel.getEmail())) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(loginViewModel.getEmail())) {
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
            showProgress(true);

            restClient.login(loginViewModel).enqueue(new Callback<LoginResult>() {
                @Override
                public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                    showProgress(false);
                    if (response.code() == 200) {
                        User user = response.body().getUser();
                        Toast.makeText(LoginActivity.this, "Hello, " + user.getFullName(), Toast.LENGTH_LONG).show();

                        AuthTokenCacheManager.cacheUserToken(getApplicationContext(), response.body());
                        CachedUser cachedUser = AuthTokenCacheManager.loadUserTokenCache(LoginActivity.this);
                        RestClient.initService(cachedUser.getAuthToken());

                        if (user.getUserRole() == GlobalConstants.UserRole.TEACHER) {
                            Intent intent = new Intent(getApplicationContext(), CoursesActivity.class);
                            intent.putExtra("teacher_Id", user.getId());
                            startActivity(intent);
                        } else if (user.getUserRole() == GlobalConstants.UserRole.STUDENT) {
                            //TODO: Start student activity
                        }

                        finish();

                    } else {
                        createAndShowDialog(response.errorBody().toString(), "Failed to log in");
                    }
                }

                @Override
                public void onFailure(Call<LoginResult> call, Throwable t) {
                    showProgress(false);
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with real logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with real logic
        return password.length() > 6;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        final String provider = findProviderFromLoginRequestCode(requestCode);
        if (resultCode == RESULT_OK) {
            // Check the request code matches the one we send in the login request
            if (requestCode == FACEBOOK_LOGIN_REQUEST_CODE) {
                MobileServiceActivityResult result = AzureServiceAdapter.getInstance().getClient().onActivityResult(data);
                if (result.isLoggedIn()) {
                    // login succeeded
                    String userId = AzureServiceAdapter.getInstance().getClient().getCurrentUser().getUserId();
                    String token = AzureServiceAdapter.getInstance().getClient().getCurrentUser().getAuthenticationToken();
                    createAndShowDialog(String.format("You are now logged in.\n %s,\n AuthenticationToken: %s", userId, token), "Success");
                    // Get the table instance to use.
                    // mToDoTable =  AzureServiceAdapter.getInstance().getClient().getTable(Course.class);
                } else {
                    // login failed, check the error message
                    String errorMessage = result.getErrorMessage();
                    createAndShowDialog(errorMessage, "Login failed");
                }
            }
        } else {
            // login failed
            createAndShowDialog(String.format("%Result code: %i", resultCode), "Failed ");
        }
    }

}

