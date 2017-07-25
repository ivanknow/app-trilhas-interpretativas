package br.com.ufrpe.isantos.trilhainterpretativa;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.Vibrator;

import java.util.Random;

import br.com.ufrpe.isantos.trilhainterpretativa.entity.Trail;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GeoService extends IntentService {

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    // Random number generator
    private final Random mGenerator = new Random();

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        GeoService getService() {
            // Return this instance of LocalService so clients can call public methods
            return GeoService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /** method for clients */
    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }

    Vibrator v;
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "br.com.ufrpe.isantos.trilhainterpretativa.action.FOO";


    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "br.com.ufrpe.isantos.trilhainterpretativa.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "br.com.ufrpe.isantos.trilhainterpretativa.extra.PARAM2";

    public GeoService() {
        super("GeoService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, Trail param1) {
        Intent intent = new Intent(context, GeoService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);

        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final Trail param1 =(Trail) intent.getSerializableExtra(EXTRA_PARAM1);

                handleActionFoo(param1);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(Trail param1) {
        try {
            Thread.sleep(5000);
            this.v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
