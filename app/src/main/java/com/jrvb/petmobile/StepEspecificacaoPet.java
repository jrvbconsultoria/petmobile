package com.jrvb.petmobile;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import obj.GenFunctions;
import obj.PetMobile;
import obj.TipoPetEnum;

public class StepEspecificacaoPet extends ListActivity implements AdapterView.OnItemSelectedListener {

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems = new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;


    EditText nomePet;
    EditText dataNascimentoPet;
    Spinner selectRacas;
    Spinner selectGeneros;
    Button btnAddMaisPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_step_especificacao_pet);

        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        setListAdapter(adapter);

        nomePet = (EditText) findViewById(R.id.editNome);
        dataNascimentoPet = (EditText) findViewById(R.id.editDataNascimento);
        selectRacas = (Spinner) findViewById(R.id.selectRacas);
        selectGeneros = (Spinner) findViewById(R.id.selectGenero);
        btnAddMaisPet = (Button) findViewById(R.id.btnAddPet);

        ArrayAdapter racasAdapter = ArrayAdapter.createFromResource(this, R.array.racas, android.R.layout.simple_dropdown_item_1line);
        selectRacas.setAdapter(racasAdapter);

        ArrayAdapter generosAdapter = ArrayAdapter.createFromResource(this, R.array.generos, android.R.layout.simple_dropdown_item_1line);
        selectGeneros.setAdapter(generosAdapter);

        //clicks handle
        btnAddMaisPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

                    //create a new pet Object
                    PetMobile newPet = new PetMobile();
                    newPet.setNome(nomePet.getText().toString());
                    newPet.setDataNascimento((Date) formatter.parse(dataNascimentoPet.getText().toString()));
                    newPet.setRacaId(selectRacas.getId());
                    newPet.setGeneroId(selectGeneros.getId());
                    newPet.setTipoPetId(TipoPetEnum.CACHORRO.getValue());

                    //add to list of pets
                    GenFunctions<PetMobile> _gen = new GenFunctions<PetMobile>();
                    List<PetMobile> _myPets = getMyPets("_mypets", getApplicationContext());

                    if(_myPets == null){
                        _myPets = new ArrayList<PetMobile>();
                    }
                    _myPets.add(newPet);
                    _gen.setList("_mypets", _myPets, getApplicationContext());

                    listItems.clear();
                    for (PetMobile pet : _myPets){
                        listItems.add(pet.getNome());
                        adapter.notifyDataSetChanged();
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

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

    public ArrayList<PetMobile> getMyPets(String key, Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String data = sharedPreferences.getString(key, "novalue");

        if(data.equals("novalue"))
            return null;

        GsonBuilder gson = new GsonBuilder();
        Type collectionType = new TypeToken<ArrayList<PetMobile>>(){}.getType();

        ArrayList<PetMobile> myJson = gson.create().fromJson(data, collectionType);
        return myJson;
    }
}
