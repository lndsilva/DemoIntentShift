package br.com.local.demointent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.local.demointent.utils.Constantes;

public class LoginActivity extends AppCompatActivity {

    private EditText etLogin;
    private EditText etSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLogin = (EditText) findViewById(R.id.etLogin);
        etSenha = (EditText) findViewById(R.id.etSenha);
    }

    public void conectar(View view) {

        Intent validaLogin = new Intent(LoginActivity.this, ValidaLoginActivity.class);

        validaLogin.putExtra(Constantes.KEY_LOGIN, etLogin.getText().toString());

        validaLogin.putExtra(Constantes.KEY_SENHA, etSenha.getText().toString());

        startActivityForResult(validaLogin, Constantes.REQUEST_CODE_VALIDA_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constantes.REQUEST_CODE_VALIDA_LOGIN:
                switch (resultCode) {
                    case RESULT_OK:
                        boolean isLoginValido = data.getBooleanExtra(Constantes.KEY_RESULT_LOGIN, false);
                        if (isLoginValido) {
                            Intent i = new Intent(this, MainActivity.class);
                            i.putExtra(Constantes.KEY_LOGIN, etLogin.getText().toString());
                            startActivity(i);
                            finish();

                            Toast.makeText(this, "Login valido", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Login invalido", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case RESULT_CANCELED:
                        break;
                }
                break;
        }
    }

    public void testeBroadcast(View v) {
        Intent i = new Intent();
        i.setAction("android.app.action.LOGIN_SUCESSO");
        sendBroadcast(i);
    }
}
