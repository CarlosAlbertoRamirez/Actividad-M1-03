package com.carlos.arreglosorterjava;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Botón que abrirá la pantalla de ordenamiento
        Button btnOpenSorting = findViewById(R.id.btnOpenSorting);

        btnOpenSorting.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SortingActivity.class);
            startActivity(intent);
        });
    }
}

