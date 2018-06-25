package com.teachme.teachmeclient.Entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teachme.teachmeclient.Interfaces.BackendService;
import com.teachme.teachmeclient.Models.GlobalConstants;
import com.teachme.teachmeclient.Models.AuthenticationInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by andyshon on 04.12.2017.
 */

public class RestClient {

    private static BackendService backendService;
    private static retrofit2.Retrofit restAdapter;
    private static OkHttpClient.Builder okHttpClient;
    private static Gson gson;

    private RestClient() {}

    public static void initService(final String authToken) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor);


        if (backendService == null) {
            if (authToken != null) {
                // add authorization token header to every Http request
                AuthenticationInterceptor authenticationInterceptor =
                        new AuthenticationInterceptor(authToken);

                if (!okHttpClient.interceptors().contains(authenticationInterceptor)) {
                    okHttpClient.addInterceptor(authenticationInterceptor);
                }
            }

            gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            restAdapter = new retrofit2.Retrofit.Builder()
                    .baseUrl(GlobalConstants.BACKEND_URL)
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            backendService = restAdapter.create(BackendService.class);
        }
    }

    public static BackendService getService() {
        return backendService;
    }
}

