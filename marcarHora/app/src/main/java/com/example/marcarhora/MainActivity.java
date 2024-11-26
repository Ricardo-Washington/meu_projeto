package com.example.marcarhora;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.opengl.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.content.CursorLoader;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import kotlin.reflect.KFunction;

public class MainActivity extends AppCompatActivity {

    private TextView txtdia, txthora, txtstatus_registro;
    private ImageView imgprimeiro, imganterior, imgproximo, imgultimo;
    private Cursor c;
    private int indice;


    ImageButton btconsulta;
    RadioGroup rgcorte, rgquimico, rgdia, rghora;
    Double valor;
    Button btreseva;
    SQLiteDatabase db;
    RadioButton rgcorte1, rgcorte2, rgcorte3, rgcorte4;
    RadioButton rdbarba, rdsombrancelha, rdlimpesa, rdprogreciva;
    RadioButton rdtintura, rdgracha, rdplatinado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        btreseva = (Button) findViewById(R.id.btflReserva);
        rdprogreciva = (RadioButton) findViewById(R.id.rdprogreciva);
        rdtintura = (RadioButton) findViewById(R.id.rdtintura);
        rdlimpesa = (RadioButton) findViewById(R.id.rdlimpesa);
        rdsombrancelha = (RadioButton) findViewById(R.id.rdsombrancelha);
        rdplatinado = (RadioButton) findViewById(R.id.rdplatinado);
        rdgracha = (RadioButton) findViewById(R.id.rdgracha);
        rdbarba = (RadioButton) findViewById(R.id.rdbarba);
        btconsulta = (ImageButton) findViewById(R.id.btconsulta);
        valor = (double) Double.valueOf(0);

        rgcorte1 = (RadioButton) findViewById(R.id.rdcorte1);
        rgcorte2 = (RadioButton) findViewById(R.id.rdcorte2);
        rgcorte3 = (RadioButton) findViewById(R.id.rdcorte3);
        rgcorte4 = (RadioButton) findViewById(R.id.rdcorte4);


        rgcorte = (RadioGroup) findViewById(R.id.radioGroup);
        rgquimico = (RadioGroup) findViewById(R.id.rgquimicos);
        rghora = (RadioGroup) findViewById(R.id.rghora);
        rgdia = (RadioGroup) findViewById(R.id.data_da_reserva);


        try {
            db = openOrCreateDatabase("banco_Reserva2",
                    Context.MODE_PRIVATE, null);
        } catch (Exception e) {
            MostraMensagem("erro:" + e.toString());
        }

        inicializarComponentes();

        btreseva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Abrirbanco();


                int selectedId = rgcorte.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);
                String corte = selectedRadioButton.getText().toString();

                int selecioneId = rghora.getCheckedRadioButtonId();
                RadioButton selecionadoRadioButton = findViewById(selecioneId);
                String hora = selecionadoRadioButton.getText().toString();

                int selectedId1 = rgdia.getCheckedRadioButtonId();
                RadioButton selectedRadioButton1 = findViewById(selectedId1);
                String dia = selectedRadioButton1.getText().toString();

                int selectedId2 = rgquimico.getCheckedRadioButtonId();
                RadioButton selectedRadioButton2 = findViewById(selectedId1);
                String quimico = selectedRadioButton2.getText().toString();

                String barba = rdbarba.getText().toString();
                String sombrancelha = rdsombrancelha.getText().toString();
                String limpesa = rdlimpesa.getText().toString();

                try {
                    db.execSQL("insert into usuarios(corte, quimico, barba, sombrancelha, limpesa, dia, hora) values('" + corte + "','" + quimico + "','" + barba + "','" + sombrancelha + "','" + limpesa + "','" + dia + "','" + hora + "')");

                } catch (Exception e) {
                    MostraMensagem("Erro:" + e.toString());
                }

            }

        });

        btconsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent consultarActivity = new Intent(MainActivity.this, ActivityConsultar.class);
                MainActivity.this.startActivities(new Intent[]{consultarActivity});
            }
        });

    }

    public void Abrirbanco() {
        try {
            db = openOrCreateDatabase("banco_Reserva2", Context.MODE_PRIVATE, null);
            db.execSQL("create table if not exists usuarios(numreg integer primary key autoincrement, corte text  , barba text , sombrancelha text ," +
                    "limpesa text , quimico text , dia text ,hora text )");

            AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);
            dialogo.setTitle("Aviso!").setMessage("Reserva efetuada com sucesso!").setNeutralButton("ok", null).show();

        } catch (Exception e) {

        }
    }

    public void MostraMensagem(String str) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);
        dialogo.setTitle("Aviso de Erro!").setMessage(str).setNeutralButton("ok ", null).show();
    }

    private void inicializarComponentes() {
        // Inicializar os TextViews
        txtdia = findViewById(R.id.txtdia);
        txthora = findViewById(R.id.txthora);
        txtstatus_registro = findViewById(R.id.txtstatus_registro);
        // Limpar os TextViews
        txtdia.setText(""); txthora.setText("");
        // Inicializar os ImageViews
         imgprimeiro = findViewById(R.id.imgprimeiro);
         imganterior = findViewById(R.id.imganterior);
         imgproximo = findViewById(R.id.imgproximo);
         imgultimo = findViewById(R.id.imgultimo);

         // Configurar o banco de dados e cursores
        try { db = openOrCreateDatabase("banco_Reserva2", Context.MODE_PRIVATE, null);
            c = db.query("usuarios", new String[]{"dia", "hora"},
                    null, null, null, null, null, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                indice = 1;
            atualizarTextViews();
        } else {
            txtstatus_registro.setText("Nenhum Registro");
        }
        // Configurar os cliques dos ImageViews
        imgprimeiro.setOnClickListener(view -> {
            if (c.getCount() > 0) { c.moveToFirst();
                indice = 1;
                atualizarTextViews();
            }
        });
        imganterior.setOnClickListener(view -> {
            if (c.getCount() > 0 && indice > 1) {
                indice--;
                c.moveToPrevious();
                atualizarTextViews();
            }
        });
        imgproximo.setOnClickListener(view -> {
            if (c.getCount() > 0 && indice < c.getCount()) {
                indice++;
                c.moveToNext();
                atualizarTextViews();
            }
        });
        imgultimo.setOnClickListener(view -> {
            if (c.getCount() > 0) { c.moveToLast();
                indice = c.getCount();
                atualizarTextViews();
            }
        });
    } catch (Exception e) {
        MostraMensagem1("Erro: " + e.toString());
    }
}
private void atualizarTextViews() {
    txtdia.setText(c.getString(0));
    txthora.setText(c.getString(1));
    txtstatus_registro.setText(indice + "/" + c.getCount());
}
public void MostraMensagem1(String str) {
    AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
    dialogo.setTitle("Aviso de Erro!").setMessage(str).setNeutralButton("OK", null).show();
}
}