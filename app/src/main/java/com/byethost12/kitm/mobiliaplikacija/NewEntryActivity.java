package com.byethost12.kitm.mobiliaplikacija;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class NewEntryActivity extends AppCompatActivity {

    Button btnSubmit;
    EditText etId, etName, etWeight, etHeight;
    RadioGroup rbGroup;
    RadioButton rbStrong, rbMedium, rbWeak;
    CheckBox cbVegan, cbInvisible, cbTwoHeads;
    Spinner spinner;

    Pokemonas pokemonas;

    String items[] = {"Water", "Fire", "Dark", "Grass"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        btnSubmit = (Button) findViewById(R.id.btnAdd);
        etId = (EditText) findViewById(R.id.etId);
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
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,items);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(etId.getText().toString());
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

                pokemonas = new Pokemonas();
                pokemonas.setId(id);
                pokemonas.setName(name);
                pokemonas.setHeight(height);
                pokemonas.setWeight(weight);
                pokemonas.setAbilities(checkboxText);
                pokemonas.setCp(rb);
                pokemonas.setType(spinnerText);

                toastMessage("ID: " + pokemonas.getId() + "\n" +
                        "Name: " + pokemonas.getName() + "\n" +
                        "Weight: " + pokemonas.getWeight() + "\n" +
                        "Height: " + pokemonas.getHeight() + "\n" +
                        "CP: " + pokemonas.getCp() + "\n" +
                        "Abilities: " + pokemonas.getAbilities() + "\n" +
                        "Type: " + pokemonas.getType());

                Intent goToSearchActivity = new Intent(NewEntryActivity.this, SearchActivity.class);
                startActivity(goToSearchActivity);
            }
        });
    }

    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
