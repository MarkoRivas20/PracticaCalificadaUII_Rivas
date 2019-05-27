package com.example.alberto.directoriomedico.addeditpaciente;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alberto.directoriomedico.R;
import com.example.alberto.directoriomedico.addeditmedico.AddEditMedicoFragment;
import com.example.alberto.directoriomedico.datos.Medicos;
import com.example.alberto.directoriomedico.datos.MedicosContract;
import com.example.alberto.directoriomedico.datos.MedicosDbHelper;
import com.example.alberto.directoriomedico.datospaciente.Pacientes;
import com.example.alberto.directoriomedico.datospaciente.PacientesCursorAdapter;
import com.example.alberto.directoriomedico.datospaciente.PacientesDbHelper;

import java.util.ArrayList;

public class AddEditPacienteFragment extends Fragment {

    private static final String ARG_PACIENTE_ID = "arg_paciente_id";
    private String mPacienteId;
    private PacientesDbHelper mPacientesDBHelper;
    private MedicosDbHelper mMedicosDBHelper;
    private FloatingActionButton mSaveButton;
    private TextInputEditText mNameField;
    private TextInputEditText mPhoneNumberField;
    private TextInputEditText mDoctorField;
    private TextInputLayout mNameLabel;
    private TextInputLayout mPhoneNumberLabel;
    private TextInputLayout mDoctorLabel;
    private Spinner spinner;

    ArrayList<Medicos> listamedicos;


    public AddEditPacienteFragment() {
        // Required empty public constructor
    }

    public static AddEditPacienteFragment newInstance(String pacienteId){
        AddEditPacienteFragment fragment = new AddEditPacienteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PACIENTE_ID,pacienteId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mPacienteId = getArguments().getString(ARG_PACIENTE_ID);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_edit_paciente, container, false);

        mSaveButton = (FloatingActionButton)getActivity().findViewById(R.id.fab);
        mNameLabel = (TextInputLayout)root.findViewById(R.id.til_name);
        mNameField = (TextInputEditText)root.findViewById(R.id.et_name);
        mPhoneNumberLabel = (TextInputLayout)root.findViewById(R.id.til_phone_number);
        mPhoneNumberField = (TextInputEditText)root.findViewById(R.id.et_phone_number);
        mDoctorLabel = (TextInputLayout)root.findViewById(R.id.til_doctor);
        mDoctorField = (TextInputEditText)root.findViewById(R.id.et_doctor);
        spinner=(Spinner)root.findViewById(R.id.spinner);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEditPaciente();
            }
        });

        mPacientesDBHelper = new PacientesDbHelper(getActivity());

        mMedicosDBHelper=new MedicosDbHelper(getActivity());

        SQLiteDatabase db=mMedicosDBHelper.getReadableDatabase();
        Medicos medicos=null;
        listamedicos=new ArrayList<Medicos>();
        Cursor cursor=db.rawQuery("SELECT * FROM "+ MedicosContract.MedicoEntry.TABLE_NAME,null);

        while (cursor.moveToNext()){
            medicos=new Medicos();
            medicos.setId(cursor.getString(1));
            medicos.setName(cursor.getString(2));
            medicos.setSpeciality(cursor.getString(3));
            medicos.setPhoneNumber(cursor.getString(4));
            medicos.setBio(cursor.getString(5));
            medicos.setAvatarUri(cursor.getString(6));
            Log.i("id",medicos.getId());
            Log.i("name",medicos.getName());
            Log.i("speciality",medicos.getSpeciality());
            Log.i("phoneNumber",medicos.getPhoneNumber());
            Log.i("bio",medicos.getBio());
            Log.i("avatarUri",medicos.getAvatarUri());
            listamedicos.add(medicos);
        }

        final ArrayList<String> arrayList=new ArrayList<String>();
        for(int i=0;i<listamedicos.size();i++){

            arrayList.add(listamedicos.get(i).getName());

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, arrayList);


        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDoctorField.setText(arrayList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(mPacienteId != null) {
            loadPaciente();
        }



        return root;
    }

    private void loadPaciente(){
        new AddEditPacienteFragment.GetPacienteByIdTask().execute();
    }

    private void showPaciente(Pacientes pacientes){
        mNameField.setText(pacientes.getNombre());
        mPhoneNumberField.setText(pacientes.getTelefono());
        mDoctorField.setText(pacientes.getDoctor());
    }

    private void showLoadError(){
        Toast.makeText(getActivity(),"Error al editar",Toast.LENGTH_SHORT).show();

    }

    private class GetPacienteByIdTask extends AsyncTask<Void,Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... params) {
            return mPacientesDBHelper.getPacienteById(mPacienteId);
        }
        @Override
        protected void onPostExecute(Cursor c){
            if(c!=null && c.moveToLast()){
                showPaciente(new Pacientes(c));
            }else{
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }
    }

    private class AddEditPacienteTask extends AsyncTask<Pacientes,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Pacientes... params) {
            if(mPacienteId != null){
                return mPacientesDBHelper.updatePaciente(params[0],mPacienteId)>0;
            }else{
                return mPacientesDBHelper.savePaciente(params[0])>0;
            }
        }

        @Override
        protected void onPostExecute(Boolean result){
            showPacienteScreen(result);
        }
    }

    private void showPacienteScreen(Boolean requery){
        if(!requery){
            showAddeditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        }else{
            getActivity().setResult(Activity.RESULT_OK);
        }
        getActivity().finish();
    }

    private void showAddeditError(){
        Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT).show();
    }

    private void addEditPaciente(){
        boolean error = false;
        String name = mNameField.getText().toString();
        String phoneNumber = mPhoneNumberField.getText().toString();
        String doctor = mDoctorField.getText().toString();


        if(TextUtils.isEmpty(name)){
            mNameLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(phoneNumber)){
            mPhoneNumberLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(doctor)){
            mDoctorLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if(error){
            return;
        }
        Pacientes pacientes = new Pacientes(name,doctor,phoneNumber,"");
        new AddEditPacienteFragment.AddEditPacienteTask().execute(pacientes);
    }
}
