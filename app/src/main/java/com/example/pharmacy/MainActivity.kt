package com.example.pharmacy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmacy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: MedicineDatabaseHelper
    private lateinit var medicineAdapter: MedicineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MedicineDatabaseHelper(this)

        // Initialize RecyclerView
        medicineAdapter = MedicineAdapter(db.getAllMedicine(),this)
        binding.MedRV.layoutManager = LinearLayoutManager(this)
        binding.MedRV.adapter = medicineAdapter

        // Set click listener for add button
        binding.btn1.setOnClickListener {
            val intent = Intent(this, AddMedicineActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh data when activity is resumed
        medicineAdapter.refreshData(db.getAllMedicine())
    }
}
