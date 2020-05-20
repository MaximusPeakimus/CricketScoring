package com.example.cricketscoring

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.*
import java.io.IOException

class RegisterActivity : AppCompatActivity() {
    var selected_team_id = ""
    val spinner_teams: MutableList<String> = ArrayList()
    private lateinit var teams:TeamList


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        getTeamsRegistration()

        team_signup_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //var temp_team = spinner_teams.get(position)
                if(position==0){
                    selected_team_id = ""
                }else{
                    selected_team_id = teams.Teams[position-1].team_id
                }
                Log.d("TEAM", selected_team_id)
            }
        }

        login_txt_btn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        btn_register.setOnClickListener {
            createUser(name_register_et.text.toString(), email_register_et.text.toString(), password_register_et.text.toString(), selected_team_id)
        }
    }

    private fun getTeamsRegistration() {

        val url = "http://10.0.2.2/api/get_teams.php"

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()

                val gson = GsonBuilder().create()

                teams = gson.fromJson(body, TeamList::class.java)

                spinner_teams.add("")

                for(x in 0..(teams.Teams.size -1)){
                    spinner_teams.add(teams.Teams[x].team_name)
                }

                team_signup_spinner.adapter = ArrayAdapter<String>(this@RegisterActivity, android.R.layout.simple_list_item_1, spinner_teams)
            }
        })
    }

    private fun createUser(name: String, email: String, password: String, team:String){
        val url = "http://10.0.2.2/api/register.php"

        val client = OkHttpClient()

        if(name==""||email==""||password==""||team==""){
            Toast.makeText(this@RegisterActivity,"Fill out form, some fields missing",Toast.LENGTH_SHORT).show()
            return
        }else{
            val body = FormBody.Builder()
                .add("name", name)
                .add("email", email)
                .add("password", password)
                .add("team_id", team)
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
                    Log.d("Response", response.body().toString())
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "Account created sucessfully",Toast.LENGTH_SHORT).show()
                    }
                    finish()
                }
            })
        }
    }
}

private class TeamList(val Teams: List<TeamSetUp>)

private class TeamSetUp(val team_id:String, val team_name:String)

