package com.example.chathuranga_pamba.sonitcabs_passenger;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Chathuranga - Pamba on 8/20/2015.
 */

public final class CommonUtilities {

    // give your server registration url here
    //static final String SERVER_URL = "http://192.168.1.2/FinalAndroidConnector/";
    static final String SERVER_URL = "http://clivekumara.esy.es/FinalAndroidConnector/";
    //static final String SERVER_URL = "http://clivekumara.esy.es/FinalAndroidConnector/";
    //static final String SERVER_URL = "http://sonitcabstaxilocator.comoj.com/FinalAndroidConnector/";

    // Google project id
    static final String SENDER_ID = "243589607829";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "Chathuranga";

    static final String DISPLAY_MESSAGE_ACTION =
            "com.example.chathuranga_pamba.gcm.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}
