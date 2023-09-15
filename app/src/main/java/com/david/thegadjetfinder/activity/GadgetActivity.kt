package com.david.thegadjetfinder.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.david.thegadjetfinder.MainActivity
import com.david.thegadjetfinder.R
import com.david.thegadjetfinder.api.RetrofitClient
import com.david.thegadjetfinder.model.ApiResponse
import com.david.thegadjetfinder.model.Gadget
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GadgetActivity() : AppCompatActivity(){
    private lateinit var name: EditText
    private lateinit var btnAdd: Button
    private lateinit var txtResponse: TextView
    private lateinit var  sharedPreferences : SharedPreferences
    private var userID : Long = 0
    private lateinit var layout: GridLayout
    private lateinit var displayMetrics: DisplayMetrics
    private var screenWidth : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gadget)
        name = findViewById(R.id.gadget_name_edit_txt)
        txtResponse = findViewById(R.id.gadget_txt_response)
        btnAdd = findViewById(R.id.gadget_add_button)
        btnAdd.setOnClickListener { addGadget() }
        sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE)
        userID  = sharedPreferences.getLong("userID",0)
        layout = findViewById(R.id.view_all)
        displayMetrics = resources.displayMetrics
        screenWidth = displayMetrics.widthPixels
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        getAllGadgets(userID)
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        loginActivity()
        return true
    }

    private fun loginActivity() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    private fun addGadget() {

        txtResponse.text = ""
        val gadget = Gadget(userID,name.text.toString())
        val addCall = RetrofitClient.getInstance().getIGadgetFinderService().addGadget(gadget)
        val thread = Thread {
            addCall.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    val res = StringBuilder()
                    res.append(response.body()!!.getResponse())
                    if (response.isSuccessful) {
                        createButton(gadget)
                        txtResponse.text = res
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    txtResponse.text = getString(R.string.request_failed)
                }
            })
        }
        thread.start()
    }

    private fun getAllGadgets(userID: Long){

        val allGadgetsCall = RetrofitClient.getInstance().getIGadgetFinderService().getAllGadgets(userID)
        val thread = Thread {
            allGadgetsCall.enqueue(object : Callback<List<Gadget>>{
                override fun onResponse(
                    call: Call<List<Gadget>>,
                    response: Response<List<Gadget>>
                ) {
                    response.body()?.forEach { gadget: Gadget ->
                        run {
                                createButton(gadget)
                            }
                        }
                    }

                override fun onFailure(call: Call<List<Gadget>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
        thread.start()
    }

    private fun createButton(gadget: Gadget) {
        val id = View.generateViewId()
        val button = Button(this@GadgetActivity)
        button.text = gadget.getGadget()
        button.width = screenWidth / 4
        button.id = id
        button.textSize = 15f
        button.setOnClickListener { v: View? ->
                                mapActivity(
                                    gadget
                                )

        }
        layout.addView(button)
    }

    private fun mapActivity(gadget: Gadget){
        val intent = Intent(this,MapActivity::class.java)
        intent.putExtra("name",gadget.getGadget())
        intent.putExtra("id",gadget.getID())
        startActivity(intent)
    }
}