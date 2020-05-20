package com.example.cricketscoring

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var userPreference:SharedLoginPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userPreference = SharedLoginPreference(this)

        val loggedInTeamID = userPreference.getLoggedInTeamID()

        if(loggedInTeamID > 0){
            startActivity(Intent(this@MainActivity, MenuActivity::class.java))
        }

        register_txt_btn.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        btn_login.setOnClickListener {
            authenticateUser(email_login_et.text.toString(), password_login_et.text.toString())
        }
    }

    private fun authenticateUser(email: String, password: String) {
        val url = "http://10.0.2.2/api/login.php"

        val client = OkHttpClient()

        val body = FormBody.Builder()
            .add("email", email)
            .add("password", password)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()

                val gson = GsonBuilder().create()

                val resp = gson.fromJson(body, JSONRepsonse::class.java)

                Log.d("Response", body)

                runOnUiThread {
                    if(resp.error==true){
                        Toast.makeText(this@MainActivity, resp.error_msg,Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@MainActivity, "login sucessful for " + resp.user.name ,Toast.LENGTH_SHORT).show()

                        userPreference.setLoggedInTeamID(resp.user.FK_team_id)
                        userPreference.setLoggedInEmail(resp.user.email)
                        userPreference.setLoggedInName(resp.user.name)
                        userPreference.setLoggedInUserId(resp.user.user_id)

                        startActivity(Intent(this@MainActivity, MenuActivity::class.java))

                        finish()
                    }
                }
            }
        })
    }
}

private class JSONRepsonse(val error:Boolean, val error_msg:String, val user: User)

private class User(val user_id:Int, val name:String, val email:String, val FK_team_id:Int)