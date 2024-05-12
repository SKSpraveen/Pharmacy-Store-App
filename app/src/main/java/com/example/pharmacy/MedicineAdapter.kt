package com.example.pharmacy

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmacy.R
import com.example.pharmacy.UpdateaMedicineActivity

class MedicineAdapter(private var medicines: List<Medicine>, private val context: Context) :
    RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    private val db: MedicineDatabaseHelper = MedicineDatabaseHelper(context)

    class MedicineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt3: TextView = itemView.findViewById(R.id.txt3)
        val txt4: TextView = itemView.findViewById(R.id.txt4)
        val txt5: TextView = itemView.findViewById(R.id.txt5)
        val upbtn2: TextView = itemView.findViewById(R.id.upbtn2)
        val delbtn: TextView = itemView.findViewById(R.id.delbtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.medicine_item, parent, false)
        return MedicineViewHolder(view)
    }

    override fun getItemCount(): Int = medicines.size

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine = medicines[position]
        holder.txt3.text = medicine.medicinename
        holder.txt4.text = medicine.quantity.toString()
        holder.txt5.text = medicine.date

        holder.upbtn2.setOnClickListener {
            val intent = Intent(context, UpdateaMedicineActivity::class.java).apply {
                putExtra("medicine_id", medicine.id)
            }
            context.startActivity(intent)
        }

        holder.delbtn.setOnClickListener {
            db.deleteMedicine(medicine.id)
            refreshData(db.getAllMedicine())
            Toast.makeText(context, "Medicine Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newMedicine: List<Medicine>) {
        medicines = newMedicine
        notifyDataSetChanged()
    }
}
