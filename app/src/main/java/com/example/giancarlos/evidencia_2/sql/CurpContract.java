package com.example.giancarlos.evidencia_2.sql;

import android.provider.BaseColumns;

public class CurpContract {
    public static final class CurpEntry implements BaseColumns {

        public static final String TABLE_NAME = "curp";
        public static final String COLUMN_CURP_LASTNAME1 = "curp_primerApellido";
        public static final String COLUMN_CURP_LASTNAME2 = "curp_segundoApellido";
        public static final String COLUMN_CURP_NAME = "curp_nombre";
        public static final String COLUMN_CURP_BIRTHDAY = "curp_nacimiento";
        public static final String COLUMN_CURP_GENDER = "curp_sexo";
        public static final String COLUMN_CURP_STATE = "curp_entidadFederativa";
    }
}
