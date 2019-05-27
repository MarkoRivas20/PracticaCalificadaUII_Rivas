package com.example.alberto.directoriomedico.addeditpaciente;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.alberto.directoriomedico.MainActivity;
import com.example.alberto.directoriomedico.R;
import com.example.alberto.directoriomedico.addeditmedico.AddEditMedicoFragment;
import com.example.alberto.directoriomedico.datos.MedicosDbHelper;
import com.example.alberto.directoriomedico.datospaciente.PacientesCursorAdapter;

import java.util.ArrayList;

public class AddEditPacienteActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_PACIENTE = 1;
    private MedicosDbHelper mMedicosDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_medico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);






        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String pacienteId = getIntent().getStringExtra(MainActivity.EXTRA_PACIENTE_ID);

        setTitle(pacienteId == null? "Añadir":"Editar");

        AddEditPacienteFragment fragment = (AddEditPacienteFragment)getSupportFragmentManager().findFragmentById(R.id.add_edit_medico_container);

        if(fragment == null){
            fragment = AddEditPacienteFragment.newInstance(pacienteId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_medico_container,fragment)
                    .commit();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
