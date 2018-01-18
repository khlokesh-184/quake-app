package com.example.android.quakereport.Data;

import android.provider.BaseColumns;

/**
 * Created by LOKESH on 07-08-2017.
 */

public class PetContract {
    private PetContract(){

    }

    public  static  final class PetEntry implements BaseColumns{
        public  static  final String TABLE_NAME="Pets";
        public  static  final String _ID=BaseColumns._ID;
        public  static  final String COLOUMN_PET_NAME="name";
        public  static  final String COLOUMN_PET_BREED="breed";
        public  static  final String COLOUMN_PET_WEIGHT="weight";
        public  static  final String COLOUMN_PET_GENDER="gender";

        public  static  final int GENDER_UNKNOWN=0;
        public  static  final int GENDER_MALE=1;
        public  static  final int GENDER_FEMALE=2;
    }
}
