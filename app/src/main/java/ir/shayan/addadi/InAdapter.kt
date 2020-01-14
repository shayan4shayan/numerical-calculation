package ir.shayan.addadi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InAdapter(val list: ArrayList<Point>) : RecyclerView.Adapter<ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_point,parent,false)
        )

        holder.delete.setOnClickListener {
            list.removeAt(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
        }

        return holder
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val point = list[position]
        holder.x.text = "x = ${point.x}"
        holder.fx.text ="f(x) = ${point.fx}"
    }
}

class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
    val x = view.findViewById<TextView>(R.id.x)
    val fx = view.findViewById<TextView>(R.id.fx)
    val delete =view.findViewById<ImageView>(R.id.delete)
}

data class Point(val x:Double,val fx:Double)