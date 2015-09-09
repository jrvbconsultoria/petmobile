package com.jrvb.petmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class StepQuaisAnimais extends AppCompatActivity {

    Button btnCachorro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_quais_animais);

        btnCachorro = (Button) findViewById(R.id.btnSoCachorro);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_step_quais_animais, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clickSoCachorro(View v)
    {
        Intent intent = new Intent(getApplicationContext(), StepEspecificacaoPet.class);
        startActivity(intent);
    }

    public void clickSoGato(View v)
    {
        Intent intent = new Intent(getApplicationContext(), StepEspecificacaoPetGato.class);
        startActivity(intent);
    }

    public void clickCachorroEGato(View v)
    {
        Intent intent = new Intent(getApplicationContext(), StepEspecificacaoPet.class);

        Bundle b = new Bundle();
        b.putString("temNext", "S");
        intent.putExtras(b);
        startActivity(intent);
    }
}
