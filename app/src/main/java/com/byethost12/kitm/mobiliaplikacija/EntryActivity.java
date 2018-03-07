package com.byethost12.kitm.mobiliaplikacija;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import static com.byethost12.kitm.mobiliaplikacija.PokemonAdapter.ENTRY_ID;

public class EntryActivity extends AppCompatActivity {

    Button btnSubmit, btnUpdate;

    EditText etName, etWeight, etHeight;
    RadioGroup rbGroup;
    RadioButton rbStrong, rbMedium, rbWeak;
    CheckBox cbVegan, cbInvisible, cbTwoHeads;
    Spinner spinner;
    ArrayAdapter<String> adapter;

    Pokemonas pradinisPokemonas;
    Pokemonas galutinisPokemonas;

    DatabaseSQLite db;

    String items[] = {"Water", "Fire", "Dark", "Grass"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseSQLite(EntryActivity.this);

        int entryID = -1;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(!extras.isEmpty()) {
                entryID = extras.getInt(ENTRY_ID);
            }
        } else { // jeigu yra naujas irasas, id = -1, jeigu egzistuojantis, bus teigiamas
            entryID = (Integer) savedInstanceState.getSerializable(ENTRY_ID);
        }

        if (entryID == -1) {
            setTitle(R.string.new_entry_label);
        } else {
            setTitle(R.string.entry_update_label);
        }

        pradinisPokemonas = new Pokemonas();
        if (entryID == -1) { //naujas irasas
            pradinisPokemonas.setId(-1);
            pradinisPokemonas.setName("");
            pradinisPokemonas.setAbilities("Vegan ");
            pradinisPokemonas.setCp("Medium");
            pradinisPokemonas.setType("Water");
            pradinisPokemonas.setHeight(0);
            pradinisPokemonas.setWeight(0);
        } else { // egzistuojantis irasas
           pradinisPokemonas = db.getPokemonas(entryID);
        }
        galutinisPokemonas = new Pokemonas();


        btnSubmit = (Button) findViewById(R.id.btnAdd);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        if (entryID == -1){ //naujas irasas - disable update button
            btnUpdate.setEnabled(false);
            btnSubmit.setEnabled(true);
        }else { // egzistuojantis irasas - disable submit
            btnUpdate.setEnabled(true);
            btnSubmit.setEnabled(false);
        }

        etName = (EditText) findViewById(R.id.etName);
        etWeight = (EditText) findViewById(R.id.etWeight);
        etHeight = (EditText) findViewById(R.id.etHeight);

        rbGroup = (RadioGroup) findViewById(R.id.rbGroup);
        rbStrong = (RadioButton) findViewById(R.id.rbStrong);
        rbMedium = (RadioButton) findViewById(R.id.rbMedium);
        rbWeak = (RadioButton) findViewById(R.id.rbWeak);

        cbVegan = (CheckBox) findViewById(R.id.cbVegan);
        cbInvisible = (CheckBox) findViewById(R.id.cbInvisible);
        cbTwoHeads = (CheckBox) findViewById(R.id.cbTwoHeads);

        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,items);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        fillFields(pradinisPokemonas);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFields();

                db.addPokemon(galutinisPokemonas);

                Intent goToSearchActivity = new Intent(EntryActivity.this, SearchActivity.class);
                startActivity(goToSearchActivity);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFields();
                db.updatePokemon(galutinisPokemonas);
                Intent goToSearchActivity = new Intent(EntryActivity.this, SearchActivity.class);
                startActivity(goToSearchActivity);
            }
        });
    }

    private void getFields(){
        String name = etName.getText().toString();
        double weight = Double.parseDouble(etWeight.getText().toString());
        double height = Double.parseDouble(etHeight.getText().toString());
        String rb = "";
        String spinnerText = "";

        if(rbStrong.isChecked()){
            rb = rbStrong.getText().toString();
        }else if(rbMedium.isChecked()){
            rb = rbMedium.getText().toString();
        }else{
            rb = rbWeak.getText().toString();
        }

        String checkboxText = "";

        if(cbVegan.isChecked()){
            checkboxText = checkboxText + "Vegan ";
        }

        if(cbInvisible.isChecked()){
            checkboxText = checkboxText + "Invisible ";
        }

        if(cbTwoHeads.isChecked()){
            checkboxText = checkboxText + "Two heads ";
        }

        spinnerText = spinner.getSelectedItem().toString();

        galutinisPokemonas.setId(pradinisPokemonas.getId());
        galutinisPokemonas.setName(name);
        galutinisPokemonas.setHeight(height);
        galutinisPokemonas.setWeight(weight);
        galutinisPokemonas.setAbilities(checkboxText);
        galutinisPokemonas.setCp(rb);
        galutinisPokemonas.setType(spinnerText);
    }

    private void fillFields (Pokemonas pokemonas){
        etName.setText(pokemonas.getName());
        etHeight.setText(String.valueOf(pokemonas.getHeight()));
        etWeight.setText(String.valueOf(pokemonas.getWeight()));

        cbInvisible.setChecked(pokemonas.getAbilities().contains("Invisible"));
        cbVegan.setChecked(pokemonas.getAbilities().contains("Vegan"));
        cbTwoHeads.setChecked(pokemonas.getAbilities().contains("Two heads"));

        rbMedium.setChecked(pokemonas.getCp().equals("Medium"));
        rbStrong.setChecked(pokemonas.getCp().equals("Strong"));
        rbWeak.setChecked(pokemonas.getCp().equals("Weak"));

        spinner.setSelection(adapter.getPosition(pokemonas.getType()));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getFields();
                if (pradinisPokemonas.equals(galutinisPokemonas)) { //Nebuvo pakeistas
                    finish();
                } else {  //Buvo pakeistas
                    showDialog();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                EntryActivity.this);

        // set title
        alertDialogBuilder.setTitle("Įspėjimas");

        // set dialog message
        alertDialogBuilder
                .setMessage("Išsaugoti pakeitimus?")
                .setCancelable(false)
                .setPositiveButton("Taip",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        dialog.cancel();
                    }
                })
                .setNegativeButton("Ne",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        EntryActivity.this.finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}

