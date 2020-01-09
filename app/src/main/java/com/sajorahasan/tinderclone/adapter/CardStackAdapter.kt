package com.sajorahasan.tinderclone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sajorahasan.tinderclone.R
import com.sajorahasan.tinderclone.model.User
import com.sajorahasan.tinderclone.utils.Utils

class CardStackAdapter(
    private var users: List<User> = emptyList(),
    private val context: Context
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]

        Glide.with(holder.image)
            .load(user.picture)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.image)

        updateLocation(holder, user)

        holder.ivPerson.setOnClickListener {
            clearImageColor(holder)
            clearIndicator(holder)
            holder.ivPerson.setColorFilter(
                ContextCompat.getColor(context, R.color.green),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            holder.ivIndicator1.setImageResource(R.drawable.minus)
            holder.tvTitle.text = context.getString(R.string.my_name)
            holder.tvTitleValue.text = setName(user)
        }
        holder.ivCalender.setOnClickListener {
            clearImageColor(holder)
            clearIndicator(holder)
            holder.ivCalender.setColorFilter(
                ContextCompat.getColor(holder.itemView.context, R.color.green),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            holder.ivIndicator2.setImageResource(R.drawable.minus)
            holder.tvTitle.text = context.getString(R.string.my_dob)
            holder.tvTitleValue.text = user.dob
        }
        holder.ivLocation.setOnClickListener {
            updateLocation(holder, user)
        }
        holder.ivPhone.setOnClickListener {
            clearImageColor(holder)
            clearIndicator(holder)
            holder.ivPhone.setColorFilter(
                ContextCompat.getColor(holder.itemView.context, R.color.green),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            holder.ivIndicator4.setImageResource(R.drawable.minus)
            holder.tvTitle.text = context.getString(R.string.my_phone)
            holder.tvTitleValue.text = user.phone
        }
        holder.ivLock.setOnClickListener {
            clearImageColor(holder)
            clearIndicator(holder)
            holder.ivLock.setColorFilter(
                ContextCompat.getColor(holder.itemView.context, R.color.green),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            holder.ivIndicator5.setImageResource(R.drawable.minus)
            holder.tvTitle.text = context.getString(R.string.my_password)
            holder.tvTitleValue.text = user.password
        }
    }

    private fun updateLocation(holder: ViewHolder, user: User) {
        clearImageColor(holder)
        clearIndicator(holder)
        holder.ivLocation.setColorFilter(
            ContextCompat.getColor(holder.itemView.context, R.color.green),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        holder.ivIndicator3.setImageResource(R.drawable.minus)
        holder.tvTitle.text = context.getString(R.string.my_address)
        holder.tvTitleValue.text = setAddress(user)
    }

    private fun setName(user: User): String? {
        return user.name.title.capitalize() + " " + user.name.first.capitalize() + " " +
                user.name.last.capitalize()
    }

    private fun setAddress(user: User): String? {
        return Utils.capitalize(user.location.street) + ", " + Utils.capitalize(user.location.city) +
                ", " + Utils.capitalize(user.location.state) + ", " + user.location.zip
    }

    private fun clearImageColor(holder: ViewHolder) {
        holder.ivPerson.setColorFilter(
            ContextCompat.getColor(holder.itemView.context, R.color.textGrey),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        holder.ivCalender.setColorFilter(
            ContextCompat.getColor(holder.itemView.context, R.color.textGrey),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        holder.ivLocation.setColorFilter(
            ContextCompat.getColor(holder.itemView.context, R.color.textGrey),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        holder.ivPhone.setColorFilter(
            ContextCompat.getColor(holder.itemView.context, R.color.textGrey),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        holder.ivLock.setColorFilter(
            ContextCompat.getColor(holder.itemView.context, R.color.textGrey),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
    }

    private fun clearIndicator(holder: ViewHolder) {
        holder.ivIndicator1.setImageResource(0)
        holder.ivIndicator2.setImageResource(0)
        holder.ivIndicator3.setImageResource(0)
        holder.ivIndicator4.setImageResource(0)
        holder.ivIndicator5.setImageResource(0)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun setUsers(users: List<User>) {
        this.users = users
    }

    fun getUsers(): List<User> {
        return users
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        var tvTitleValue: TextView = view.findViewById(R.id.tvTitleValue)
        var ivPerson: ImageView = view.findViewById(R.id.ivPerson)
        val ivCalender: ImageView = view.findViewById(R.id.ivCalender)
        val ivLocation: ImageView = view.findViewById(R.id.ivLocation)
        val ivPhone: ImageView = view.findViewById(R.id.ivPhone)
        val ivLock: ImageView = view.findViewById(R.id.ivLock)

        val ivIndicator1: ImageView = view.findViewById(R.id.ivIndicator1)
        val ivIndicator2: ImageView = view.findViewById(R.id.ivIndicator2)
        val ivIndicator3: ImageView = view.findViewById(R.id.ivIndicator3)
        val ivIndicator4: ImageView = view.findViewById(R.id.ivIndicator4)
        val ivIndicator5: ImageView = view.findViewById(R.id.ivIndicator5)

        var image: ImageView = view.findViewById(R.id.imgUser)
    }

}