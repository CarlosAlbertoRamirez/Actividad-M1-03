package com.carlos.arreglosorterjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;mport android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class SortingActivity extends AppCompatActivity {

    private static final String TAG = "SortingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Asegúrate de que tu layout se llame activity_sorting.xml
        setContentView(R.layout.activity_sorting);

        EditText inputNumbers = findViewById(R.id.inputNumbers);
        Button btnSort = findViewById(R.id.btnSort);
        TextView txtResult = findViewById(R.id.txtResult);

        // 1) Ordenamiento de los números que escriba el usuario (UI)
        btnSort.setOnClickListener(v -> {
            String raw = inputNumbers.getText().toString().trim();

            if (TextUtils.isEmpty(raw)) {
                txtResult.setText("Por favor, escribe números separados por comas o espacios.");
                return;
            }

            try {
                // Admite comas/espacios/tabs/saltos de línea como separadores
                String[] parts = raw.split("[,\\s]+");
                int[] numbers = new int[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    numbers[i] = Integer.parseInt(parts[i].trim());
                }

                Arrays.sort(numbers);

                // Mostrar un máximo de 50 para no saturar la UI
                int toShow = Math.min(50, numbers.length);
                int[] preview = Arrays.copyOf(numbers, toShow);
                String suffix = (numbers.length > toShow)
                        ? " … (total " + numbers.length + " números)"
                        : "";

                txtResult.setText("Ordenado: " + Arrays.toString(preview) + suffix);

            } catch (NumberFormatException e) {
                txtResult.setText("Error: usa solo enteros separados por comas/espacios.");
            }
        });

        // 2) Benchmark de 1,000,000 (se ejecuta al abrir esta pantalla)
        runBenchmarkOneMillionAsync();
    }

    /**
     * Ejecuta el benchmark en segundo plano para no congelar la UI.
     * Genera 1,000,000 enteros, los ordena y loguea el tiempo total en Logcat.
     */
    private void runBenchmarkOneMillionAsync() {
        Toast.makeText(this, "Iniciando benchmark (revisa Logcat)…", Toast.LENGTH_SHORT).show();

        new Thread(() -> {
            final int N = 1_000_000;
            int[] big = new int[N];

            // Generación rápida y reproducible (LCG sencillo)
            long seed = 123456789L;
            for (int i = 0; i < N; i++) {
                seed = (1664525L * seed + 1013904223L) & 0xFFFFFFFFL;
                big[i] = (int) (seed & 0x7FFFFFFF);
            }

            long t0 = System.currentTimeMillis();
            Arrays.sort(big);
            long t1 = System.currentTimeMillis();

            Log.i(TAG, "Ordenamiento de " + N + " elementos tomó " + (t1 - t0) + " ms");

            runOnUiThread(() ->
                    Toast.makeText(this, "Benchmark terminado (mira Logcat)", Toast.LENGTH_SHORT).show()
            );
        }).start();
    }
}
