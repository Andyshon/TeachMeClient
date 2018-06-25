package com.teachme.teachmeclient.Views.simple_views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceActivityResult;
import com.microsoft.windowsazure.mobileservices.UserAuthenticationCallback;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceUser;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.teachme.teachmeclient.Models.GlobalConstants;
import com.teachme.teachmeclient.R;


import java.net.MalformedURLException;
import java.util.HashMap;

public class AzureTestLoginActivity extends Activity {

    public static final int LOGIN_REQUEST_CODE_GOOGLE = 1;
    public static final int LOGIN_REQUEST_CODE_FACEBOOK = 2;
    public static final int LOGIN_REQUEST_CODE_MSA = 3;
    public static final int LOGIN_REQUEST_CODE_AAD = 4;
    public static final int LOGIN_REQUEST_CODE_TWITTER = 5;

    private MobileServiceClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_azure_test_login);
        // Create the client instance, using the provided mobile app URL.
        try {
            mClient = new MobileServiceClient(GlobalConstants.BACKEND_URL, this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Button googleLoginButton = (Button) findViewById(R.id.buttonGoogleLogin);
        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Login with Google with offline permission. Offline permission is required by refresh tokens.
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("access_type", "offline");
                mClient.login("Google", GlobalConstants.URL_SCHEME, LOGIN_REQUEST_CODE_GOOGLE, parameters);
            }
        });

        Button facebookLoginButton = (Button) findViewById(R.id.buttonFacebookLogin);
        facebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClient.login("Facebook", GlobalConstants.URL_SCHEME, LOGIN_REQUEST_CODE_FACEBOOK);
            }
        });

        Button msaLoginButton = (Button) findViewById(R.id.buttonMSALogin);
        msaLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Login with Microsoft. Configure offline permission on azure portal is required by refresh tokens.
                mClient.login("microsoftaccount", GlobalConstants.URL_SCHEME, LOGIN_REQUEST_CODE_MSA);
            }
        });

        Button aadLoginButton = (Button) findViewById(R.id.buttonAADLogin);
        aadLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Login with AAD with response_type=code id_token. It is required by refresh tokens.
                HashMap<String, String> parameters = new HashMap<String, String>();
                parameters.put("response_type", "code id_token");
                mClient.login("AAD", GlobalConstants.URL_SCHEME, LOGIN_REQUEST_CODE_AAD, parameters);
            }
        });

        Button twitterLoginButton = (Button) findViewById(R.id.buttonTwitterLogin);
        twitterLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClient.login("Twitter", GlobalConstants.URL_SCHEME, LOGIN_REQUEST_CODE_TWITTER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        final String provider = findProviderFromLoginRequestCode(requestCode);
        if (resultCode == RESULT_OK) {

            MobileServiceActivityResult result = mClient.onActivityResult(data);
            if (result.isLoggedIn()) {

                // login succeeded
                final String text = String.format("%s Login succeeded.\nUserId: %s, authenticationToken: %s\n", provider,
                        mClient.getCurrentUser().getUserId(),
                        mClient.getCurrentUser().getAuthenticationToken());

                // If authentication provider supports refresh token, run refreshUser tests
                if (providerSupportsRefreshToken(provider)) {

                    mClient.refreshUser(new UserAuthenticationCallback() {
                        @Override
                        public void onCompleted(MobileServiceUser user, Exception exception, ServiceFilterResponse response) {
                            String text2;
                            if (user != null && exception == null) {
                                // refreshUser succeeded
                                text2 = String.format("%s RefreshUser succeeded.\nUserId: %s, authenticationToken: %s", provider, user.getUserId(), user.getAuthenticationToken());
                            } else {
                                // refreshUser failed
                                text2 = String.format("%s RefreshUser failed.\nError: %s", provider, exception.getMessage());
                            }
                            displayText(text + text2, provider);
                        }
                    });
                } else {
                    displayText(text, provider);
                }

            } else {
                // login failed
                displayText(String.format("%s Login failed.\nError: %s", provider, result.getErrorMessage()), provider);
            }
        }
    }

    private boolean providerSupportsRefreshToken(String provider) {
        return provider.equals("Google") || provider.equals("MSA") || provider.equals("AAD");
    }

    private void displayText(String text, String provider) {
        int textViewId = findTextViewIdFromProvider(provider);
        TextView textView = (TextView) findViewById(textViewId);
        textView.setText(text);
    }

    private String findProviderFromLoginRequestCode(int requestCode) {
        String provider;
        switch (requestCode) {
            case LOGIN_REQUEST_CODE_GOOGLE:
                provider = "Google";
                break;
            case LOGIN_REQUEST_CODE_FACEBOOK:
                provider = "Facebook";
                break;
            case LOGIN_REQUEST_CODE_MSA:
                provider = "MSA";
                break;
            case LOGIN_REQUEST_CODE_AAD:
                provider = "AAD";
                break;
            case LOGIN_REQUEST_CODE_TWITTER:
                provider = "Twitter";
                break;
            default:
                throw new IllegalArgumentException("request code does not match any provider");
        }
        return provider;
    }

    private int findTextViewIdFromProvider(String provider) {
        int textViewId;
        switch (provider) {
            case "Google":
                textViewId = R.id.textViewGoogleLogin;
                break;
            case "Facebook":
                textViewId = R.id.textViewFacebookLogin;
                break;
            case "MSA":
                textViewId = R.id.textViewMSALogin;
                break;
            case "AAD":
                textViewId = R.id.textViewAADLogin;
                break;
            case "Twitter":
                textViewId = R.id.textViewTwitterLogin;
                break;
            default:
                throw new IllegalArgumentException("provider is not supported");
        }
        return textViewId;
    }
}