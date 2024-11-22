package com.example.iniciando_projeto_banco_dados;

import android.content.ContentValues;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activity_grava_registros extends Activity {

    Button btcadastra;
    EditText ednome, edtelefone, edemail;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grava_registros);
        btcadastra = (Button) findViewById(R.id.btcadastrar);
        ednome = (EditText) findViewById(R.id.ednome);
        edtelefone =  (EditText)  findViewById(R.id.edtelefone);
        edemail = (EditText)  findViewById(R.id.edemail);

        try {
            db = openOrCreateDatabase("banco_dados",
                    Context.MODE_PRIVATE, null);
        }
        catch (Exception e){
            MostraMensagem("erro:" + e.toString());
        }
        btcadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View argO) {
                String nome = ednome.getText().toString();
                String telefone = edtelefone.getText().toString();
                String email = edemail.getText().toString();


                try {
                    db.execSQL("insert into usuarios(nome, telefone, email) values('"+nome+"','"+ telefone+"','"+ email + "')");
                    MostraMensagem("dados cadastrados com sucesso");
                }
                catch (Exception e){
                    MostraMensagem("Erro:" + e.toString());
                }

            }
        });


    };
    public void  MostraMensagem(String str)
    {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(activity_grava_registros.this);
        dialogo.setTitle("Aviso de Erro!").setMessage(str).setNeutralButton("ok ", null).show();
    }

}