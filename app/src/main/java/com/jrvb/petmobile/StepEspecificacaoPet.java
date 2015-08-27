package com.jrvb.petmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class StepEspecificacaoPet extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner selectRacas;
    Spinner selectIdades;
    Spinner selectGeneros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_especificacao_pet);

        selectRacas = (Spinner) findViewById(R.id.selectRacas);
        selectIdades = (Spinner) findViewById(R.id.selectIdade);
        selectGeneros = (Spinner) findViewById(R.id.selectGenero);

        ArrayAdapter racasAdapter = ArrayAdapter.createFromResource(this, R.array.racas, android.R.layout.simple_spinner_item);
        selectRacas.setAdapter(racasAdapter);

        ArrayAdapter idadesAdapter = ArrayAdapter.createFromResource(this, R.array.idades, android.R.layout.simple_spinner_item);
        selectIdades.setAdapter(idadesAdapter);

        ArrayAdapter generosAdapter = ArrayAdapter.createFromResource(this, R.array.generos, android.R.layout.simple_spinner_item);
        selectGeneros.setAdapter(generosAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_step_especificacao_pet, menu);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView itemSelected = (TextView) view;

        Toast.makeText(this, "You selected: " + itemSelected.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void backButton(View v)
    {
        finish();
    }
}
