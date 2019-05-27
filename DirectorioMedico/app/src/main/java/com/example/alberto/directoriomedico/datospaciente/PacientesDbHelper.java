package com.example.alberto.directoriomedico.datospaciente;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.alberto.directoriomedico.datos.Medicos;
import com.example.alberto.directoriomedico.datos.MedicosContract;

public class PacientesDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION
            = 1;
    public static final String DATABASE_NAME
            = "Pacientes.db";

    public PacientesDbHelper(Context context){
        super(context,DATABASE_NAME
                ,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "
                + PacientesContract.PacienteEntry.TABLE_NAME + "("
                + PacientesContract.PacienteEntry._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PacientesContract.PacienteEntry.ID
                + " TEXT NOT NULL,"
                + PacientesContract.PacienteEntry.NOMBRE
                + " TEXT NOT NULL,"
                + PacientesContract.PacienteEntry.DOCTOR
                + " TEXT NOT NULL,"
                + PacientesContract.PacienteEntry.TELEFONO
                + " TEXT NOT NULL,"
                + PacientesContract.PacienteEntry.AVATAR
                + " TEXT,"
                + "UNIQUE (" + PacientesContract.PacienteEntry.ID + "))"
        );

        //realizar inserción
        mockData(sqLiteDatabase);
    }

    private void mockData(SQLiteDatabase sqLiteDatabase){
        mockPacientes(sqLiteDatabase,new Pacientes("Marko Rivas"
                ,"Carlos Sanchez","982823697",
                "" ));

    }

    public long mockPacientes(SQLiteDatabase db
            , Pacientes pacientes){
        return db.insert(PacientesContract.PacienteEntry.TABLE_NAME
                ,null
                ,pacientes.toContentValues());
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getAllPacientes(){
        return getReadableDatabase()
                .query(PacientesContract.PacienteEntry.TABLE_NAME
                        ,null // columnas
                        ,null // WHERE
                        ,null // valores WHERE
                        ,null // GROUP BY
                        ,null // HAVING
                        ,null); // OREDER BY
    }


    public Cursor getPacienteById(String pacienteId){
        return getReadableDatabase()
                .query(PacientesContract.PacienteEntry.TABLE_NAME
                        ,null
                        , PacientesContract.PacienteEntry.ID + " LIKE ?"
                        ,new String[] {pacienteId}
                        ,null
                        ,null
                        ,null);
    }

    public long savePaciente(Pacientes pacientes){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                PacientesContract.PacienteEntry.TABLE_NAME
                ,null
                ,pacientes.toContentValues());
    }

    public int updatePaciente(Pacientes pacientes
            ,String pacienteId){
        return getWritableDatabase().update(
                PacientesContract.PacienteEntry.TABLE_NAME
                ,pacientes.toContentValues()
                , PacientesContract.PacienteEntry.ID+" LIKE ?"
                ,new String[]{pacienteId}
        );
    }

    public int deletePaciente(String pacienteId){
        return getWritableDatabase().delete(
                PacientesContract.PacienteEntry.TABLE_NAME
                , PacientesContract.PacienteEntry.ID+" LIKE ?"
                ,new String[]{pacienteId}
        );
    }
}
