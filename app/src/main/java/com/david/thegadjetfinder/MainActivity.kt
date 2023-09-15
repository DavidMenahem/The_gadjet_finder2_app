package com.david.thegadjetfinder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.david.thegadjetfinder.activity.GadgetActivity
import com.david.thegadjetfinder.api.RetrofitClient
import com.david.thegadjetfinder.model.ApiResponse
import com.david.thegadjetfinder.model.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var txtResponse : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val login: Button = findViewById(R.id.login_button)
        login.setOnClickListener { login() }
        val register: Button = findViewById(R.id.register_button)
        register.setOnClickListener { register() }
        email = findViewById(R.id.email_edit_txt)
        password = findViewById(R.id.password_edit_txt)
        txtResponse = findViewById(R.id.response_txt)
    }

    private fun login(){

        val credentials = Credentials(email.text.toString(), password.text.toString())
        val loginCall : Call<ApiResponse> =
            RetrofitClient.getInstance().getIGadgetFinderService().login(credentials)
        txtResponse.text = ""
        val message = StringBuilder()
        val thread = Thread {
            loginCall.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    val userID :Long = response.body()!!.getUserID()
                    val res: String = response.body()!!.getResponse()
                    if (response.isSuccessful) {
                        message.append(res)
                        if (userID > 0) {
                            //save response data to android session
                            val preferences = getSharedPreferences("my_preferences", MODE_PRIVATE)
                            val editor = preferences.edit()
                            editor.putLong("userID", response.body()!!.getUserID())
                            editor.apply()
                            //load gadget page
                            loadGadgetActivity()
                        }
                        txtResponse.text = message.toString()
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    txtResponse.text = getString(R.string.request_failed)
                }
            })

        }
        thread.start()
    }

    private fun register(){
        val credentials = Credentials(email.text.toString(), password.text.toString())
        val loginCall =
            RetrofitClient.getInstance().getIGadgetFinderService().register(credentials)
        txtResponse.text = ""
        val message = StringBuilder()
        val thread = Thread {
            loginCall.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    val userID :Long = response.body()!!.getUserID()
                    val res: String = response.body()!!.getResponse()
                    if (response.isSuccessful) {
                        message.append(res)
                        if (userID > 0) {
                            //save response data to android session
                            val preferences = getSharedPreferences("my_preferences", MODE_PRIVATE)
                            val editor = preferences.edit()
                            editor.putLong("userID", response.body()!!.getUserID())
                            editor.apply()
                            //load gadget page
                            loadGadgetActivity()
                        }
                        txtResponse.text = message.toString()
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    txtResponse.text = getString(R.string.request_failed)
                }
            })

        }
        thread.start()
    }


    private fun loadGadgetActivity(){
        val intent = Intent(this,GadgetActivity::class.java)
        startActivity(intent)
    }
}
