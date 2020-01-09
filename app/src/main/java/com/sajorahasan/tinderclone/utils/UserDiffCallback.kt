package com.sajorahasan.tinderclone.utils

import androidx.recyclerview.widget.DiffUtil
import com.sajorahasan.tinderclone.model.User

class UserDiffCallback(
    private val old: List<User>,
    private val new: List<User>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition].username == new[newPosition].username
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition] == new[newPosition]
    }
}