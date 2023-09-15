package com.david.thegadjetfinder.activity

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.david.thegadjetfinder.R
import com.david.thegadjetfinder.api.RetrofitClient
import com.david.thegadjetfinder.model.CircleView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class MapActivity : AppCompatActivity() {
    private lateinit var displayMetrics: DisplayMetrics
    private lateinit var layout: LinearLayout
    private lateinit var txtName: TextView
    private lateinit var btnDel: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        layout = findViewById(R.id.map)
        txtName = findViewById(R.id.gadget_name)
        btnDel = findViewById(R.id.delete_gadget)
        btnDel.setOnClickListener{ delGadget() }
        displayMetrics = resources.displayMetrics
        txtName.text = getName()
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        showCircle()
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        gadgetActivity()
        return true
    }
    private fun delGadget() {
        val delCall = RetrofitClient.getInstance().getIGadgetFinderService().deleteGadget(getID())
        val thread = Thread{
            delCall.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    gadgetActivity()
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {}
            })
        }
        thread.start()
    }

    private fun gadgetActivity() {
        val intent = Intent(this,GadgetActivity::class.java)
        startActivity(intent)
    }

    private fun showCircle() {
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels
        var randomX = Random.nextInt(screenWidth);
        var randomY = Random.nextInt(screenHeight);
        randomX -= screenWidth / 2
        randomY -= screenHeight / 2
        val circle = CircleView(this,randomX,randomY)
        layout.addView(circle)
    }

    private fun getID():Long{
        return intent.extras!!.getLong("id",0)
    }
    private fun getName():String?{
        return intent.extras?.getString("name")
    }
}