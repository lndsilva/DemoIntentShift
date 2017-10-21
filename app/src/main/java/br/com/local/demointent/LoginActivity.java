package br.com.local.demointent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText etLogin;
    private EditText etSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLogin = (EditText)findViewById(R.id.etLogin);
        etLogin = (EditText)findViewById(R.id.etSenha);
    }

    public void conectar(View view) {
        Intent validaLogin = new Intent(LoginActivity.this, ValidaLoginActivity.class);
        validaLogin.putExtra("","");
        startActivity(validaLogin);
    }
}
