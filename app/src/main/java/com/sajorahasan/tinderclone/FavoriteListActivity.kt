package com.sajorahasan.tinderclone

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sajorahasan.tinderclone.adapter.FavoriteAdapter
import com.sajorahasan.tinderclone.model.User
import com.sajorahasan.tinderclone.room.TinderRoomDb
import com.sajorahasan.tinderclone.utils.Utils
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_favorite_list.*
import kotlinx.android.synthetic.main.item_user.*


class FavoriteListActivity : AppCompatActivity() {
    private val TAG: String = "FavoriteListActivity"

    private lateinit var userList: MutableList<User>
    private lateinit var db: TinderRoomDb
    private var disposable: CompositeDisposable? = null

    private val adapter by lazy { FavoriteAdapter(userList, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_list)

        //Initialize views
        initViews()
    }

    private fun initViews() {
        userList = mutableListOf()

        disposable = CompositeDisposable()
        db = TinderRoomDb.getDatabase(this)

        rvFavorite.layoutManager = GridLayoutManager(this, 2)
        rvFavorite.adapter = adapter

        adapter.setOnItemClickListener(object : FavoriteAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                showUserDialog(position)
            }
        })

        getFvtUsers()
    }

    private fun getFvtUsers() {
        disposable!!.add(
            Single.create<List<User>> { e -> e.onSuccess(db.userDao().getFavUsers()) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showLoader() }
                .doFinally { hideLoader() }
                .subscribe({ getUserDataSuccess(it) }) { handleError(it) }
        )
    }

    private fun showLoader() {
        loader.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        loader.visibility = View.GONE
    }

    private fun getUserDataSuccess(data: List<User>) {
        Log.d(TAG, "On Success: Got User Data Successfully! $data")

        userList.clear()
        userList.addAll(data)
        adapter.notifyDataSetChanged()
    }

    private fun handleError(t: Throwable?) {
        Log.d(TAG, "handleError:  ${t?.localizedMessage}")
        Toast.makeText(this, "Error : ${t?.localizedMessage}", Toast.LENGTH_SHORT).show()
    }

    private fun showUserDialog(i: Int) {
        var user = userList[i]
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.item_user)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT

        setUserDialogData(user, dialog)

        dialog.show()
        dialog.window?.attributes = lp
    }

    private fun setUserDialogData(user: User, dialog: Dialog) {
        val leftOverlay = dialog.findViewById<FrameLayout>(R.id.left_overlay)
        val rightOverlay = dialog.findViewById<FrameLayout>(R.id.right_overlay)
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val imgUser = dialog.findViewById<ImageView>(R.id.imgUser)

        val tvTitle: TextView = dialog.findViewById(R.id.tvTitle)
        val tvTitleValue: TextView = dialog.findViewById(R.id.tvTitleValue)

        val ivPerson: ImageView = dialog.findViewById(R.id.ivPerson)
        val ivCalender: ImageView = dialog.findViewById(R.id.ivCalender)
        val ivLocation: ImageView = dialog.findViewById(R.id.ivLocation)
        val ivPhone: ImageView = dialog.findViewById(R.id.ivPhone)
        val ivLock: ImageView = dialog.findViewById(R.id.ivLock)

        val ivIndicator1: ImageView = dialog.findViewById(R.id.ivIndicator1)
        val ivIndicator2: ImageView = dialog.findViewById(R.id.ivIndicator2)
        val ivIndicator3: ImageView = dialog.findViewById(R.id.ivIndicator3)
        val ivIndicator4: ImageView = dialog.findViewById(R.id.ivIndicator4)
        val ivIndicator5: ImageView = dialog.findViewById(R.id.ivIndicator5)

        rightOverlay.visibility = View.GONE
        leftOverlay.visibility = View.GONE
        ivClose.visibility = View.VISIBLE

        Glide.with(this)
            .load(user.picture)
            .apply(RequestOptions.circleCropTransform())
            .into(imgUser)

        updateLocation(dialog, user)

        ivClose.setOnClickListener{
            dialog.dismiss()
        }
        ivPerson.setOnClickListener {
            clearImageColor(dialog)
            clearIndicator(dialog)
            ivPerson.setColorFilter(
                ContextCompat.getColor(this, R.color.green),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            ivIndicator1.setImageResource(R.drawable.minus)
            tvTitle.text = this.getString(R.string.my_name)
            tvTitleValue.text = setName(user)
        }
        ivCalender.setOnClickListener {
            clearImageColor(dialog)
            clearIndicator(dialog)
            ivCalender.setColorFilter(
                ContextCompat.getColor(this, R.color.green),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            ivIndicator2.setImageResource(R.drawable.minus)
            tvTitle.text = getString(R.string.my_dob)
            tvTitleValue.text = user.dob
        }
        ivLocation.setOnClickListener {
            updateLocation(dialog, user)
        }
        ivPhone.setOnClickListener {
            clearImageColor(dialog)
            clearIndicator(dialog)
            ivPhone.setColorFilter(
                ContextCompat.getColor(this, R.color.green),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            ivIndicator4.setImageResource(R.drawable.minus)
            tvTitle.text = getString(R.string.my_phone)
            tvTitleValue.text = user.phone
        }
        ivLock.setOnClickListener {
            clearImageColor(dialog)
            clearIndicator(dialog)
            ivLock.setColorFilter(
                ContextCompat.getColor(this, R.color.green),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            ivIndicator5.setImageResource(R.drawable.minus)
            tvTitle.text = getString(R.string.my_password)
            tvTitleValue.text = user.password
        }
    }

    private fun updateLocation(d: Dialog, user: User) {
        clearImageColor(d)
        clearIndicator(d)
        d.ivLocation.setColorFilter(
            ContextCompat.getColor(this, R.color.green),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        d.ivIndicator3.setImageResource(R.drawable.minus)
        d.tvTitle.text = getString(R.string.my_address)
        d.tvTitleValue.text = setAddress(user)
    }

    private fun setName(user: User): String? {
        return user.name.title.capitalize() + " " + user.name.first.capitalize() + " " +
                user.name.last.capitalize()
    }

    private fun setAddress(user: User): String? {
        return Utils.capitalize(user.location.street) + ", " + Utils.capitalize(user.location.city) +
                ", " + Utils.capitalize(user.location.state) + ", " + user.location.zip
    }

    private fun clearImageColor(d: Dialog) {
        d.ivPerson.setColorFilter(
            ContextCompat.getColor(this, R.color.textGrey),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        d.ivCalender.setColorFilter(
            ContextCompat.getColor(this, R.color.textGrey),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        d.ivLocation.setColorFilter(
            ContextCompat.getColor(this, R.color.textGrey),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        d.ivPhone.setColorFilter(
            ContextCompat.getColor(this, R.color.textGrey),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        d.ivLock.setColorFilter(
            ContextCompat.getColor(this, R.color.textGrey),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
    }

    private fun clearIndicator(d: Dialog) {
        d.ivIndicator1.setImageResource(0)
        d.ivIndicator2.setImageResource(0)
        d.ivIndicator3.setImageResource(0)
        d.ivIndicator4.setImageResource(0)
        d.ivIndicator5.setImageResource(0)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called from Fvt")
        disposable?.clear()
    }
}
