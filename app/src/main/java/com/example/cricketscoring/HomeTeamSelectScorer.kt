package com.example.cricketscoring

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketscoring.AdapterClasses.HomeTeamSelectorAdapter
import com.example.cricketscoring.Fragments.JSONRseponseTeam
import com.example.cricketscoring.MenuActivity.Companion.homeTeamsList
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_home_team_select_scorer.*
import okhttp3.*
import java.io.IOException

class HomeTeamSelectScorer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_team_select_scorer)

        Toast.makeText(this,"Please add the players in batting order",Toast.LENGTH_LONG).show()

        home_team_scorer_selector_recycler_view.layoutManager = LinearLayoutManager(this)

        val home_team_id = intent.getIntExtra("home_team_id", 0)
        val away_team_id = intent.getIntExtra("away_team_id", 0)
        val home_team_name = intent.getStringExtra("home_team_name")
        val away_team_name = intent.getStringExtra("away_team_name")
        val match_id = intent.getIntExtra("match_id", 0)

        setUpPlayersHomeTeam(home_team_id)

        home_team_scorer_next_btn.setOnClickListener {
            if(homeTeamsList.count() == 11){
                val intent = Intent(this, AwayTeamSelectScorer::class.java)

                intent.putExtra("away_team_id", away_team_id)
                intent.putExtra("home_team_name", home_team_name)
                intent.putExtra("away_team_name", away_team_name)
                intent.putExtra("home_team_id", home_team_id)
                intent.putExtra("match_id", match_id)

                startActivity(intent)
            }else{
                Toast.makeText(this, "You must have exactly 11 players", Toast.LENGTH_LONG).show()
            }
        }

        home_team_scorer_back_btn.setOnClickListener {
            finish()
        }
    }

    private fun setUpPlayersHomeTeam(home_team_id:Int ){
        val url = "http://10.0.2.2/api/get_players.php"

        val client = OkHttpClient()

        val body = FormBody.Builder()
            .add("team_id", home_team_id.toString())
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

                val resp = gson.fromJson(body, JSONRseponseTeam::class.java)

                runOnUiThread {
                    home_team_scorer_selector_recycler_view.adapter = HomeTeamSelectorAdapter(resp)
                }
            }
        })
    }
}

