package com.example.alberto.directoriomedico.datospaciente;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.alberto.directoriomedico.datos.MedicosContract;

import java.util.UUID;

public class Pacientes {

    private String id;
    private String nombre;
    private String doctor;
    private String telefono;
    private String avatar;

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getAvatar() {
        return avatar;
    }



    public Pacientes(String nombre, String doctor, String telefono,String avatar) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.doctor = doctor;
        this.telefono = telefono;
        this.avatar = avatar;
    }

    public Pacientes(Cursor cursor){
        id = cursor.getString(cursor.getColumnIndex
                (PacientesContract.PacienteEntry.ID));
        nombre = cursor.getString(cursor.getColumnIndex
                (PacientesContract.PacienteEntry.NOMBRE));
        doctor = cursor.getString(cursor.getColumnIndex
                (PacientesContract.PacienteEntry.DOCTOR));
        telefono = cursor.getString(cursor.getColumnIndex
                (PacientesContract.PacienteEntry.TELEFONO));
        avatar = cursor.getString(cursor.getColumnIndex
                (PacientesContract.PacienteEntry.AVATAR));
    }

    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(PacientesContract.PacienteEntry.ID,id);
        values.put(PacientesContract.PacienteEntry.NOMBRE,nombre);
        values.put(PacientesContract.PacienteEntry.DOCTOR
                ,doctor);
        values.put(PacientesContract.PacienteEntry.TELEFONO
                ,telefono);
        values.put(PacientesContract.PacienteEntry.AVATAR,avatar);
        return values;
    }


}
