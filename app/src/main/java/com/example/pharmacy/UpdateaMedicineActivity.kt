package com.example.pharmacy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pharmacy.databinding.ActivityUpdateaBinding

class UpdateaMedicineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateaBinding
    private lateinit var db: MedicineDatabaseHelper
    private var medicineId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MedicineDatabaseHelper(this)

        // Get medicine ID from intent extras
        medicineId = intent.getIntExtra("medicine_id", -1)

        // If medicine ID is invalid, finish the activity
        if (medicineId == -1) {
            Toast.makeText(this, "Invalid Medicine ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Retrieve medicine details from the database
        val medicine = db.getMedicineByID(medicineId)

        // If medicine is null, show a toast and finish the activity
        if (medicine == null) {
            Toast.makeText(this, "Medicine not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Populate EditText fields with medicine details
        binding.upETxt1.setText(medicine.medicinename)
        binding.upETxt2.setText(medicine.quantity.toString())
        binding.upETxt3.setText(medicine.date)
        binding.upETxt4.setText(medicine.price.toString())

        // Set onClickListener for the update button
        binding.upbtn2.setOnClickListener {
            val newMedicineName = binding.upETxt1.text.toString()
            val newQuantity = binding.upETxt2.text.toString().toIntOrNull() ?: 0
            val newDate = binding.upETxt3.text.toString()
            val newPrice = binding.upETxt4.text.toString().toDouble()

            // Create updated Medicine object
            val updatedMedicine = Medicine(medicineId, newMedicineName, newQuantity, newDate, newPrice)

            // Update medicine in the database
            db.updateMedicine(updatedMedicine)

            // Show toast indicating change saved
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()

            // Finish the activity
            finish()
        }
    }
}
