package br.com.local.demointent;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import br.com.local.demointent.broadcastreceiver.AlarmeReceiver;
import br.com.local.demointent.service.BoundService;
import br.com.local.demointent.utils.Constantes;

public class MainActivity extends AppCompatActivity {

    private TextView tvLogin;
    private TextView tvPlacarVisitante;
    private TextView tvPlacarHome;

    private TextView timeStampText;

    private int placarHome = 0;
    private int placarVisitante = 0;

    BoundService mBoundService;
    boolean mServiceBound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLogin = (TextView) findViewById(R.id.tvLogin);
        tvPlacarHome = (TextView) findViewById(R.id.tvPlacaHome);
        tvPlacarVisitante = (TextView) findViewById(R.id.tvPlacaVisitante);

        if (savedInstanceState != null) {
            placarHome = savedInstanceState.getInt(Constantes.KEY_PLACAR_CASA);
            placarVisitante = savedInstanceState.getInt(Constantes.KEY_PLACAR_VISITANTE);
        }

        tvPlacarHome.setText(String.valueOf(placarHome));
        tvPlacarVisitante.setText(String.valueOf(placarVisitante));

        if (getIntent() != null) {
            tvLogin.setText(getIntent().getStringExtra(Constantes.KEY_LOGIN));
        }

        timeStampText = (TextView) findViewById(R.id.tvTimeStampText);

        Button printTimeStampButton = (Button) findViewById(R.id.btLigarAlarme);
        final Button stopServiceButton = (Button) findViewById(R.id.btDesligaAlarme);

        printTimeStampButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mServiceBound) {
                    timeStampText.setText(mBoundService.getTimeStamp());
                }
            }
        });
        stopServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mServiceBound) {
                    unbindService(mServiceConnection);
                    mServiceBound = false;
                }
                Intent intent = new Intent(MainActivity.this, BoundService.class);
                stopService(intent);
            }
        });


    }
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BoundService.MyBinder myBinder = (BoundService.MyBinder) service;
            mBoundService = myBinder.getService();
            mServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
        }
    };

    public void golCasa(View v) {

        placarHome++;
        tvPlacarHome.setText(String.valueOf(placarHome));

    }

    public void golVisitante(View v) {

        placarVisitante++;
        tvPlacarVisitante.setText(String.valueOf(placarVisitante));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(Constantes.KEY_PLACAR_CASA, placarHome);
        outState.putInt(Constantes.KEY_PLACAR_VISITANTE, placarVisitante);
    }

    public void programarAlarme(View v) {

        EditText text = (EditText) findViewById(R.id.tvTempoJogo);

        int i = Integer.parseInt(text.getText().toString());
        Intent intent = new Intent(this, AlarmeReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + (i * 1000),
                pendingIntent);
        Toast.makeText(this, "Alarm set in " + i + "seconds", Toast.LENGTH_LONG).show();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }




}
