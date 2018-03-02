package com.byethost12.kitm.mobiliaplikacija;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    Pokemonas pokemonas;

    DatabaseSQLite db;

    String items[] = {"Water", "Fire", "Dark", "Grass"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

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

        pokemonas = new Pokemonas();
        if (entryID == -1) { //naujas irasas
            pokemonas.setId(-1);
            pokemonas.setName("");
            pokemonas.setAbilities("Vegan");
            pokemonas.setCp("Medium");
            pokemonas.setType("Water");
            pokemonas.setHeight(0);
            pokemonas.setWeight(0);
        } else { // egzistuojantis irasas
           pokemonas = db.getPokemonas(entryID);
        }

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

        fillFields(pokemonas);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFields();

                db.addPokemon(pokemonas);

                Intent goToSearchActivity = new Intent(EntryActivity.this, SearchActivity.class);
                startActivity(goToSearchActivity);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFields();

                db.updatePokemon(pokemonas);

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
            checkboxText = checkboxText + "Vegan,";
        }

        if(cbInvisible.isChecked()){
            checkboxText = checkboxText + "Invisible,";
        }

        if(cbTwoHeads.isChecked()){
            checkboxText = checkboxText + "Two heads";
        }

        spinnerText = spinner.getSelectedItem().toString();

        pokemonas.setId(pokemonas.getId());
        pokemonas.setName(name);
        pokemonas.setHeight(height);
        pokemonas.setWeight(weight);
        pokemonas.setAbilities(checkboxText);
        pokemonas.setCp(rb);
        pokemonas.setType(spinnerText);
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
}
