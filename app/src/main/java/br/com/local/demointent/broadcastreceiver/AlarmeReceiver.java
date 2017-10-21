package br.com.local.demointent.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import br.com.local.demointent.R;

/**
 * Created by logonrm on 21/10/2017.
 */

public class AlarmeReceiver extends BroadcastReceiver {

    private MediaPlayer mp = null;


    @Override
    public void onReceive(Context context, Intent intent) {

        mp = MediaPlayer.create(context, R.raw.despertar);
        mp.start();
        Toast.makeText(context, "Alarm...",Toast.LENGTH_LONG).show();

    }
}
