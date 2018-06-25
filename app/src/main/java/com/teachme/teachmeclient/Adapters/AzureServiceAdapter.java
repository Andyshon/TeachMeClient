package com.teachme.teachmeclient.Adapters;

import java.net.MalformedURLException;
import android.content.Context;
import com.microsoft.windowsazure.mobileservices.*;
import com.teachme.teachmeclient.Models.GlobalConstants;

/**
 * Created by andyshon on 16.01.2018.
 */

public class AzureServiceAdapter {
    private MobileServiceClient mClient;
    private static AzureServiceAdapter mInstance = null;

    private AzureServiceAdapter(Context context) throws MalformedURLException {
        mClient = new MobileServiceClient(GlobalConstants.BACKEND_URL, context);

    }

    public static void Initialize(Context context) throws MalformedURLException {
        if (mInstance == null) {
            mInstance = new AzureServiceAdapter(context);
        }
    }

    public static AzureServiceAdapter getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("AzureServiceAdapter is not initialized");
        }
        return mInstance;
    }

    public MobileServiceClient getClient() {
        return mClient;
    }
}