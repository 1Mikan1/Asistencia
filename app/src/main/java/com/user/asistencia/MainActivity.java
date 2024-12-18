package com.user.asistencia;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button Registro, Login;
    EditText Usuario, Passw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Usuario = findViewById(R.id.LBLA1Usuario);
        Passw = findViewById(R.id.LBLA1Pass);
        Registro = findViewById(R.id.BNA1Registro);
        Login = findViewById(R.id.BNA1Login);
        
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comprobar();
            }
        });
        
        Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registrar();
            }
        });
    }

    private void Registrar() {
    }

    private void Comprobar() {
        String usuario = Usuario.getText().toString().trim();
        String password = Passw.getText().toString().trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            // Mostrar mensaje de error si los campos están vacíos
            Toast.makeText(MainActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // URL de tu backend (cambia por tu dirección real)
        String url = "http://Android.test/index.php";

        // Crear una solicitud POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String status = jsonResponse.getString("status");
                        String message = jsonResponse.getString("message");

                        if (status.equals("success")) {
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            // Navegar a otra actividad si el usuario es válido
                        } else {
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(MainActivity.this, "Error en la conexión: " + error.getMessage(), Toast.LENGTH_SHORT).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("usuario", usuario);
                params.put("password", password);
                return params;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}