package com.example.marcarhora;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.database.Cursor;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityConsultar extends AppCompatActivity {

    TextView txtcorte, txtquimico, txtbarba, txtsombrancelha, txtlimpesa, txtdia, txthora, txtstatus_registro;
    SQLiteDatabase db;
    ImageView imgprimeiro, imganterior, imgproximo, imgultimo;
    int indice;
    Cursor c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consultar);

        txtcorte = (TextView) findViewById(R.id.txtcorte);
        txtbarba = (TextView)  findViewById(R.id.txtbarba);
        txtsombrancelha = (TextView) findViewById(R.id.txtsombrancelha);
        txtlimpesa = (TextView) findViewById(R.id.txtlimpesa);
        txtquimico = (TextView) findViewById(R.id.txtquimico);
        txtdia = (TextView) findViewById(R.id.txtdia);
        txthora = (TextView) findViewById(R.id.txthora);
        txtstatus_registro = (TextView) findViewById(R.id.txtstatus_registro);

        txtcorte.setText("");
        txtbarba.setText("");
        txtsombrancelha.setText("");
        txtlimpesa.setText("");
        txtquimico.setText("");
        txtdia.setText("");
        txthora.setText("");

        imgprimeiro = (ImageView) findViewById(R.id.imgprimeiro);
        imganterior  = (ImageView) findViewById(R.id.imganterior);
        imgproximo = (ImageView) findViewById(R.id.imgproximo);
        imgultimo = (ImageView) findViewById(R.id.imgultimo);

        try {
            db = openOrCreateDatabase("banco_Reserva2", Context.MODE_PRIVATE, null);

            c = db.query("usuarios", new String[]{"corte","barba","sombrancelha","limpesa","quimico","dia","hora"},
                    null,null,null,null,null,null);

            if (c.getCount() > 0) {
                c.moveToFirst();
                indice = 1;

                txtcorte.setText(c.getString(0));
                txtbarba.setText(c.getString(1));
                txtsombrancelha.setText(c.getString(2));
                txtlimpesa.setText(c.getString(3));
                txtquimico.setText(c.getString(4));
                txtdia.setText(c.getString(5));
                txthora.setText(c.getString(6));
                txtstatus_registro.setText(indice+"/"+c.getCount());

            }
            else {
                txtstatus_registro.setText("Nenhum Registro");
            }
            imgprimeiro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(c.getCount() > 0 );
                    {
                        c.moveToFirst();
                        indice = 1;
                        txtcorte.setText(c.getString(0));
                        txtbarba.setText(c.getString(1));
                        txtsombrancelha.setText(c.getString(2));
                        txtlimpesa.setText(c.getString(3));
                        txtquimico.setText(c.getString(4));
                        txtdia.setText(c.getString(5));
                        txthora.setText(c.getString(6));
                        txtstatus_registro.setText(indice+"/"+c.getCount());
                    }

                }
            });
            imganterior.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (c.getCount() > 0 );
                    {
                        if (indice > 1) {

                            indice--;
                            c.moveToPrevious();

                            txtcorte.setText(c.getString(0));
                            txtbarba.setText(c.getString(1));
                            txtsombrancelha.setText(c.getString(2));
                            txtlimpesa.setText(c.getString(3));
                            txtquimico.setText(c.getString(4));
                            txtdia.setText(c.getString(5));
                            txthora.setText(c.getString(6));
                            txtstatus_registro.setText(indice+"/"+c.getCount());
                        }

                    }
                }
            });
            imgproximo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (c.getCount() > 0 );
                    {
                        if (indice != c.getCount()) {

                            indice++;
                            c.moveToNext();

                            txtcorte.setText(c.getString(0));
                            txtbarba.setText(c.getString(1));
                            txtsombrancelha.setText(c.getString(2));
                            txtlimpesa.setText(c.getString(3));
                            txtquimico.setText(c.getString(4));
                            txtdia.setText(c.getString(5));
                            txthora.setText(c.getString(6));
                            txtstatus_registro.setText(indice+"/"+c.getCount());
                        }
                    }
                }
            });
            imgultimo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (c.getCount() > 0){
                        c.moveToLast();
                        indice = c.getCount();

                        txtcorte.setText(c.getString(0));
                        txtbarba.setText(c.getString(1));
                        txtsombrancelha.setText(c.getString(2));
                        txtlimpesa.setText(c.getString(3));
                        txtquimico.setText(c.getString(4));
                        txtdia.setText(c.getString(5));
                        txthora.setText(c.getString(6));
                        txtstatus_registro.setText(indice+"/"+c.getCount());

                    }
                }
            });
        }catch (Exception e){
            MostraMensagem("Erro:" + e.toString());
        }

    }

    public void  MostraMensagem(String str)
    {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(ActivityConsultar.this);
        dialogo.setTitle("Aviso de Erro!").setMessage(str).setNeutralButton("ok ", null).show();
    }
}


