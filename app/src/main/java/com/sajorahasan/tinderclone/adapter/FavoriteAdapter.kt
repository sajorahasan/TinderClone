package com.sajorahasan.tinderclone.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sajorahasan.tinderclone.R
import com.sajorahasan.tinderclone.model.User
import kotlinx.android.synthetic.main.item_fvt_user.view.*


class FavoriteAdapter(
    private val user: List<User>,
    private val context: Context
) :
    RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {

    private var mListener: OnItemClickListener? = null

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.tvFvtUserName
        val pic: ImageView = view.ivFvtUserImg
    }

    // Define the mListener interface
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    // Define the method that allows the parent activity or fragment to define the listener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fvt_user, parent, false) as CardView
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, pos: Int) {
        val u = user[pos]
        holder.tvName.text = setName(u)

        Glide.with(context)
            .load(Uri.parse(u.picture))
            .into(holder.pic)

        holder.itemView.setOnClickListener { mListener?.onItemClick(it, pos) }

    }

    override fun getItemCount() = user.size

    private fun setName(user: User): String? {
        return user.name.title.capitalize() + " " + user.name.first.capitalize() + " " +
                user.name.last.capitalize()
    }
}