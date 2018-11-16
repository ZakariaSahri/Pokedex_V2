package zakaria.avito.pokedex;


import android.app.Application;

import zakaria.avito.pokedex.utils.ConnectionReceiver;

public class PokeDexApplication extends Application {

    private static PokeDexApplication mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized PokeDexApplication getInstance() {
        return mInstance;
    }

    public void setConnectionListener(ConnectionReceiver.ConnectionReceiverListener listener) {
        ConnectionReceiver.connectionReceiverListener = listener;
    }

}
