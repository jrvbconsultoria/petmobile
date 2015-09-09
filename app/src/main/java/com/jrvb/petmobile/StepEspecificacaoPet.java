package com.jrvb.petmobile;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.TableLayout;
import android.widget.TableRow;
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
import java.util.UUID;

import obj.GenFunctions;
import obj.PetMobile;
import obj.TipoPetEnum;

public class StepEspecificacaoPet extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText nomePet;
    EditText dataNascimentoPet;
    Spinner selectRacas;
    Spinner selectGeneros;
    Button btnAddMaisPet;
    TableLayout tablePets;

    int headerHeight = 85;
    int rowHeight = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_especificacao_pet);

        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        Button btnProximo = (Button) findViewById(R.id.btnProximo);

        Bundle b = getIntent().getExtras();
        if(b != null){
            if(b.get("temNext").equals("S")){
                btnSalvar.setVisibility(View.GONE);
                btnProximo.setVisibility(View.VISIBLE);

                btnProximo.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), StepEspecificacaoPetGato.class);
                        startActivity(intent);
                    }
                });
            }
        }

        tablePets = (TableLayout) findViewById(R.id.tablePets);
        nomePet = (EditText) findViewById(R.id.editNome);
        dataNascimentoPet = (EditText) findViewById(R.id.editDataNascimento);
        selectRacas = (Spinner) findViewById(R.id.selectRacas);
        selectGeneros = (Spinner) findViewById(R.id.selectGenero);
        btnAddMaisPet = (Button) findViewById(R.id.btnAddPet);

        ArrayAdapter racasAdapter = ArrayAdapter.createFromResource(this, R.array.racas, android.R.layout.simple_dropdown_item_1line);
        selectRacas.setAdapter(racasAdapter);

        ArrayAdapter generosAdapter = ArrayAdapter.createFromResource(this, R.array.generos, android.R.layout.simple_dropdown_item_1line);
        selectGeneros.setAdapter(generosAdapter);

        CreateMyPets(false);

        //clicks handle
        btnAddMaisPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateMyPets(true);
            }
        });
    }

    private void CreateMyPets(boolean getValues){
        try {
            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            GenFunctions<PetMobile> _gen = new GenFunctions<PetMobile>();
            List<PetMobile> _myPets = getMyPets("_mypets", getApplicationContext());
            int index = 0;

            if(getValues){
                //create a new pet Object
                PetMobile newPet = new PetMobile();
                newPet.setNome(nomePet.getText().toString());
                newPet.setDataNascimento((Date) formatter.parse(dataNascimentoPet.getText().toString()));
                newPet.setRacaId(selectRacas.getId());
                newPet.setGeneroId(selectGeneros.getId());
                newPet.setTipoPetId(TipoPetEnum.CACHORRO.getValue());
                newPet.setIdMobile(java.util.UUID.randomUUID());

                if(_myPets == null){
                    _myPets = new ArrayList<PetMobile>();
                }
                _myPets.add(newPet);

                _gen.setList("_mypets", _myPets, getApplicationContext());
                CreateMyPets(false);
            }else{
                if(_myPets == null || _myPets.size() == 0)
                    return;
            }

            //clear the table
            tablePets.removeAllViews();

            // Create a TableRow and give it an ID
            TableRow tr = new TableRow(getApplicationContext());
            tr.setId(0);
            index += 1;
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, headerHeight));
            tr.setBackgroundResource(R.drawable.cell_shape);
            tr.setPadding(5, 5, 5, 5);

            TableRow.LayoutParams params = (TableRow.LayoutParams) tr.getLayoutParams();
            params.span = 2; //amount of columns you will span

            // Create a TextView to house the name of the province
            TextView labelHeadNome = new TextView(getApplicationContext());
            labelHeadNome.setId(index);
            labelHeadNome.setText("Meus Pets");
            labelHeadNome.setTextSize(25);
            labelHeadNome.setTextColor(Color.BLACK);
            labelHeadNome.setBackgroundColor(Color.GRAY);
            labelHeadNome.setLayoutParams(params);
            tr.addView(labelHeadNome);

            // Add the TableRow to the TableLayout
            tablePets.addView(tr, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, headerHeight));

            for (PetMobile pet : _myPets){
                // Create a TableRow and give it an ID
                tr = new TableRow(getApplicationContext());
                tr.setId(1 + index);
                index += 1;

                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, rowHeight));
                tr.setBackgroundResource(R.drawable.cell_shape_row);
                tr.setPadding(5, 5, 5, 5);

                params = (TableRow.LayoutParams) tr.getLayoutParams();
                params.weight = 0.7f; //amount of columns you will span
                params.width = 0;

                // Create a TextView to house the name of the province
                TextView labelTV = new TextView(getApplicationContext());
                labelTV.setId(100 + index);
                labelTV.setText(pet.getNome());
                labelTV.setTextColor(Color.BLACK);
                labelTV.setLayoutParams(params);
                tr.addView(labelTV);

                params.weight = 0.3f; //amount of columns you will span
                params.width = 0;
                // Create a TextView to house the value of the after-tax income
                Button btnRemover = new Button(getApplicationContext());
                btnRemover.setText("Excluir");
                btnRemover.setId(200 + index);
                btnRemover.setLayoutParams(params);
                btnRemover.setOnClickListener(getOnClickRemove(btnRemover, tr, pet.getIdMobile()));
                tr.addView(btnRemover);

                // Add the TableRow to the TableLayout
                tablePets.addView(tr, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, rowHeight));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    View.OnClickListener getOnClickRemove(final Button button, final TableRow row, final UUID _id)  {
        return new View.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(StepEspecificacaoPet.this)
                    .setMessage("Confirma exclusão do pet?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            tablePets.removeView(row);
                            List<PetMobile> _myPets = getMyPets("_mypets", StepEspecificacaoPet.this);
                            PetMobile _remove = null;
                            for (PetMobile p : _myPets)
                                if (p.getIdMobile() == _id) {
                                    _remove = p;
                                    break;
                                }

                            if (_remove != null) {
                                _myPets.remove(_remove);
                                GenFunctions<PetMobile> _gen = new GenFunctions<PetMobile>();
                                _gen.setList("_mypets", _myPets, getApplicationContext());
                            }

                        }
                    })
                    .setNegativeButton("Não", null).show();
            }
        };
    }
}
