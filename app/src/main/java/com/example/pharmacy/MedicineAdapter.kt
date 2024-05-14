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
        val txt6: TextView = itemView.findViewById(R.id.txt6)
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
        // Set the text for Medicine Name
        holder.txt3.text = context.getString(R.string.medicine_name_format, medicine.medicinename)
        // Set the text for Quantity
        holder.txt4.text = context.getString(R.string.quantity_format, medicine.quantity)
        holder.txt5.text = context.getString(R.string.date_format, medicine.date)
        // Set the text for Price
        holder.txt6.text = context.getString(R.string.price_format, medicine.price)

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
