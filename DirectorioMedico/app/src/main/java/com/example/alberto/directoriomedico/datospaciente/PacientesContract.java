package com.example.alberto.directoriomedico.datospaciente;

import android.provider.BaseColumns;

public class PacientesContract {
    public static abstract class PacienteEntry implements BaseColumns {
        public static final String TABLE_NAME = "pacientes";

        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String DOCTOR = "doctor";
        public static final String TELEFONO = "telefono";
        public static final String AVATAR = "avatar";
    }
}
