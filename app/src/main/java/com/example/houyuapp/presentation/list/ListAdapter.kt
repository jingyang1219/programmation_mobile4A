package com.example.houyuapp.presentation.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.houyuapp.R
import com.example.houyuapp.domain.entity.Dog


class ListAdapter(
    private val values: MutableList<Dog?>?,
    private val context: Context,
    private var listener: OnItemClickListener? = null


) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private var imageView: ImageView? = null

    interface OnItemClickListener {
        fun onItemClick(item: Dog?)
    }

    inner class ViewHolder(var layout: View) : RecyclerView.ViewHolder(
        layout
    ) {
        // each data item is just a string in this case
        var txtHeader: TextView
        var txtFooter: TextView

        init {
            txtHeader = layout.findViewById<View>(R.id.firstLine) as TextView
            txtFooter = layout.findViewById<View>(R.id.secondLine) as TextView
            imageView = layout.findViewById<View>(R.id.icon_dog) as ImageView

        }
    }

    fun add(position: Int, item: Dog) {
        if (values != null) {
            values.add(position, item)
        }
        notifyItemInserted(position)
    }

    fun remove(position: Int) {
        if (values != null) {
            values.removeAt(position)
        }
        notifyItemRemoved(position)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        // create a new view
        val inflater = LayoutInflater.from(
            parent.context
        )
        val v: View = inflater.inflate(R.layout.row_layout, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val curDog: Dog? = values?.get(position)
        if (curDog != null) {
            holder.txtHeader.text = curDog.getBreed()
        }

        if (curDog != null) {
            imageView?.let { Glide.with(context).load(curDog.getImageurl()).optionalFitCenter().into(
                it
            ) }
        }

        if (curDog != null) {
            holder.txtFooter.text = curDog.getOrigin()
        }

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                listener!!.onItemClick(curDog)

            }
        })

    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        if (values != null) {
            return values.size
        }else{
            return 0
        }

    }

}


