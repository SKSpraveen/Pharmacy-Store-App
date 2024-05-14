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
            val quantityText = binding.ETxt2.text.toString()
            val date = binding.ETxt3.text.toString()
            val priceText = binding.ETxt4.text.toString()

            // Check if any field is empty
            if (medicinename.isEmpty() || quantityText.isEmpty() || date.isEmpty() || priceText.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate quantity
            val quantity = try {
                quantityText.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Please enter a valid quantity", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate price
            val price = try {
                priceText.toDouble()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Please enter a valid price", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Quantity and price should be greater than zero
            if (quantity <= 0) {
                Toast.makeText(this, "Quantity should be greater than zero", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (price <= 0.0) {
                Toast.makeText(this, "Price should be greater than zero", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // If all validations pass, proceed with saving the medicine
            val medicine = Medicine(0, medicinename, quantity, date, price)
            db.insertMedicine(medicine)
            finish()
            Toast.makeText(this, "Medicine successfully Saved", Toast.LENGTH_SHORT).show()
        }
    }
}

