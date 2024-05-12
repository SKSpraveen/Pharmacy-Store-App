package com.example.pharmacy

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MedicineDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "medicineapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allmedicine"
        private const val COLUMN_ID = "id"
        private const val COLUMN_MEDICINENAME = "medicinename"
        private const val COLUMN_QUANTITY = "quantity"
        private const val COLUMN_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_MEDICINENAME TEXT, $COLUMN_QUANTITY INTEGER, $COLUMN_DATE TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertMedicine(medicine: Medicine) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_MEDICINENAME, medicine.medicinename)
            put(COLUMN_QUANTITY, medicine.quantity)
            put(COLUMN_DATE, medicine.date)
        }

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllMedicine():List<Medicine>{
        val medicineList = mutableListOf<Medicine>()
        val db = readableDatabase
        val query ="SELECT*FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)


        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val medicinename = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEDICINENAME))
            val quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))

            val medicine = Medicine(id,medicinename,quantity,date)
            medicineList.add(medicine)
        }

        cursor.close()
        db.close()
        return medicineList
    }

    fun updateMedicine(medicine: Medicine){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_MEDICINENAME,medicine.medicinename)
            put(COLUMN_QUANTITY,medicine.quantity)
            put(COLUMN_DATE,medicine.date)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(medicine.id.toString())
        db.update(TABLE_NAME, values, whereClause,whereArgs)
        db.close()
    }

    fun getMedicineByID(medicineId: Int): Medicine{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $medicineId "
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val medicinename = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEDICINENAME))
        val quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY))
        val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))

        cursor.close()
        db.close()
        return Medicine(id,medicinename,quantity,date)
    }

    fun deleteMedicine(medicineId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(medicineId.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }
}


