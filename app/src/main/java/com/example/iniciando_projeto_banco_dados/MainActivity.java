package com.example.iniciando_projeto_banco_dados;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    Button btcriarbanco, btcadastrardados, btcadastrardados2, btconsultardados, btalterardados, btexcluirdados;
    SQLiteDatabase db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btexcluirdados = findViewById(R.id.btexcluirdados);
        btconsultardados = findViewById(R.id.btconsultardados);
        btcadastrardados = findViewById(R.id.btcadastrardados);
        btcriarbanco = findViewById(R.id.btcriarbanco);
        btcadastrardados2 = findViewById(R.id.btcadastrardados2);
        btalterardados = findViewById(R.id.btalterardados);

        btcriarbanco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    db = openOrCreateDatabase("banco_dados", Context.MODE_PRIVATE, null);
                    db.execSQL("create table if not exists usuarios(numreg integer primary key autoincrement , nome text not null, telefone text not null," +
                            "email text not null)");
                    AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);
                    dialogo.setTitle("Aviso!").setMessage("Banco de dados criado com sucesso!").setNeutralButton("ok", null).show();
                }catch (Exception e) {

                }
            }
        });
        btcadastrardados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ctivity_grava_registros = new Intent(MainActivity.this, activity_grava_registros.class);
                MainActivity.this.startActivities(new Intent[]{ctivity_grava_registros});
            }
        });

        btcadastrardados2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gravaRegistrosActivity2 = new Intent(MainActivity.this, activity_grava_registros.class);
                MainActivity.this.startActivities(new Intent[]{gravaRegistrosActivity2});
            }
        });
        btconsultardados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent consultarDadosActivity = new Intent(MainActivity.this, activity_consulta_dados.class);
                MainActivity.this.startActivities(new Intent[]{consultarDadosActivity});
            }
        });
        btalterardados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent alterarDadosActivity = new Intent(MainActivity.this,AlterarDadosActivity.class);
                MainActivity.this.startActivity(alterarDadosActivity);
            }
        });
        btexcluirdados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent excluirDadosActivity = new Intent(MainActivity.this,
                        ExcluirDadosActivity.class);
                MainActivity.this.startActivities(new Intent[]{excluirDadosActivity});
            }
        });

    }

}