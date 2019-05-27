package com.example.alberto.directoriomedico;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alberto.directoriomedico.addeditmedico.AddEditMedicoActivity;
import com.example.alberto.directoriomedico.addeditpaciente.AddEditPacienteActivity;
import com.example.alberto.directoriomedico.datos.MedicosContract;
import com.example.alberto.directoriomedico.datos.MedicosCursorAdapter;
import com.example.alberto.directoriomedico.datos.MedicosDbHelper;
import com.example.alberto.directoriomedico.datospaciente.PacientesContract;
import com.example.alberto.directoriomedico.datospaciente.PacientesCursorAdapter;
import com.example.alberto.directoriomedico.datospaciente.PacientesDbHelper;
import com.example.alberto.directoriomedico.medicodetail.MedicoDetailActivity;
import com.example.alberto.directoriomedico.pacientedetail.PacienteDetailActivity;

public class PacientesFragmen extends Fragment {

    ListView mPacientesList;
    FloatingActionButton mAddButton;
    PacientesCursorAdapter mPacientesAdapter;
    PacientesDbHelper mPacientesDBHelper;

    public static final int REQUEST_UPDATE_DELETE_PACIENTE = 2;


    public PacientesFragmen() {
        // Required empty public constructor
    }

    public static PacientesFragmen newInstance(){return new PacientesFragmen();}




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_medicos, container, false);

        mPacientesList = (ListView)root.findViewById(R.id.medicos_list);
        mPacientesAdapter = new PacientesCursorAdapter(getActivity(),null,0);
        mAddButton = (FloatingActionButton)getActivity().findViewById(R.id.fab);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddScreen();
            }
        });

        mPacientesList.setAdapter(mPacientesAdapter);

        mPacientesDBHelper = new PacientesDbHelper(getActivity());

        mPacientesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor currentItem = (Cursor)mPacientesAdapter.getItem(position);
                String currentPacienteId = currentItem.getString(currentItem.getColumnIndex(PacientesContract.PacienteEntry.ID));
                showDetailScreen(currentPacienteId);
            }
        });


        loadPacientes();

        return root;
    }

    private void showAddScreen(){
        Intent intent = new Intent(getActivity(), AddEditPacienteActivity.class);
        startActivityForResult(intent, AddEditPacienteActivity.REQUEST_ADD_PACIENTE);
    }

    private void showDetailScreen(String pacienteId){
        Intent intent = new Intent(getActivity(), PacienteDetailActivity.class);
        intent.putExtra(MainActivity.EXTRA_PACIENTE_ID,pacienteId);
        startActivityForResult(intent,REQUEST_UPDATE_DELETE_PACIENTE);
    }

    private void loadPacientes(){
        new PacientesFragmen.PacienteLoadTask().execute();
    }

    private class PacienteLoadTask extends AsyncTask<Void,Void,Cursor> {


        @Override
        protected Cursor doInBackground(Void... params) {
            return mPacientesDBHelper.getAllPacientes();
        }

        @Override
        protected void onPostExecute(Cursor c){
            if(c!=null && c.getCount()>0){
                mPacientesAdapter.swapCursor(c);
            }else{

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Activity.RESULT_OK == resultCode){
            switch(requestCode){
                case AddEditPacienteActivity.REQUEST_ADD_PACIENTE:
                    showSuccessfullSavedMessage();
                    loadPacientes();
                    break;
                case REQUEST_UPDATE_DELETE_PACIENTE:
                    loadPacientes();
                    break;

            }
        }
    }



    private void showSuccessfullSavedMessage(){
        Toast.makeText(getActivity(),"Paciente guardado correctamente",Toast.LENGTH_SHORT).show();
    }
}
