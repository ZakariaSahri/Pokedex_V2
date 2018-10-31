package zakaria.avito.pokedex.connectioncheck;


import android.app.Application;

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
