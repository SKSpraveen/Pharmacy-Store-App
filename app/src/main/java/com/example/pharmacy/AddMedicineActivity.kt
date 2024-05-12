package com.example.pharmacy

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pharmacy.databinding.ActivityAddMedicineBinding

class AddMedicineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMedicineBinding
    private lateinit var db: MedicineDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMedicineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MedicineDatabaseHelper(this)

        binding.btn2.setOnClickListener {
            val medicinename = binding.ETxt1.text.toString()
            val quantity = binding.ETxt2.text.toString().toInt()
            val date = binding.ETxt3.text.toString()
            val medicine = Medicine(0, medicinename, quantity, date)
            db.insertMedicine(medicine)
            finish()
            Toast.makeText(this, "Medicine Saved", Toast.LENGTH_SHORT).show()
        }
    }
}

