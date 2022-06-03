package com.rrd.ho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrasiActivity extends AppCompatActivity {
    private EditText edtrepassword, edtpassword,edtnamadepan, edtnamabelakang, edtemail;
    private Button m_regist;
    private ProgressBar loading;
    private static String URL_REGIST = "https://historicalobject.skom.id/api/Register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);


        loading = findViewById(R.id.loading);
        edtnamadepan = (EditText) findViewById(R.id.edtnamadepan);
        edtnamabelakang =  (EditText) findViewById(R.id.edtnamabelakang);
        edtemail =  (EditText) findViewById(R.id.edtemail);
        edtpassword =  (EditText) findViewById(R.id.edtpassword);
        edtrepassword =  (EditText) findViewById(R.id.edtrepassword);
        m_regist = findViewById(R.id.btn_regist);

        m_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Regist();            }
        });
    }

    public void Regist(){
        loading.setVisibility(View.VISIBLE);
        m_regist.setVisibility(View.GONE);

        final String namadepan = this.edtnamadepan.getText().toString().trim();
        final String namabelakang = this.edtnamabelakang.getText().toString().trim();
        final String email = this.edtemail.getText().toString().trim();
        final String password = this.edtpassword.getText().toString().trim();
        final String repassword = this.edtrepassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                Toast.makeText(RegistrasiActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                                edtnamadepan.setText("");
                                edtnamabelakang.setText("");
                                edtemail.setText("");
                                edtpassword.setText("");
                                edtrepassword.setText("");

                                Intent intent = new Intent(RegistrasiActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(RegistrasiActivity.this, "Register Error! 2" + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            m_regist.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistrasiActivity.this, "Register Error!" + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        m_regist.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("namadepan", namadepan);
                params.put("namabelakang", namabelakang);
                params.put("email", email);
                params.put("password", password);
                params.put("repassword", repassword);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}