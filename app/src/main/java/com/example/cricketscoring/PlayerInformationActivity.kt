package com.example.cricketscoring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.GsonBuilder
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_player_information.*
import okhttp3.*
import java.io.IOException

class PlayerInformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_information)

        Slidr.attach(this)

        //get player name and id from previous screen
        player_name_information.text = intent.getStringExtra("player_name")
        val player_id = intent.getIntExtra("player_id", 0)

        getPlayerStatistics(player_id)
    }

    private fun getPlayerStatistics(playerId: Int) {
        val url = "http://10.0.2.2/api/get_player_stats.php"

        val client = OkHttpClient()

        val body = FormBody.Builder()
            .add("player_id", playerId.toString())
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

                Log.d("Response", body)

                val gson = GsonBuilder().create()

                val resp = gson.fromJson(body, JSONRseponseStatistics::class.java)

                //display player statistics
                runOnUiThread {
                    player_matches_played.text = resp.playerStats.matches_played.toString()
                    player_batting_average.text = resp.playerStats.batting_average.toString()
                    player_total_runs_scored.text = resp.playerStats.total_runs.toString()
                    player_highscore.text = resp.playerStats.highscore.toString()
                    player_hundreds.text = resp.playerStats.hundreds.toString()
                    player_fifties.text = resp.playerStats.fifties.toString()
                    player_sixes.text = resp.playerStats.sixes_scored.toString()
                    player_fours.text = resp.playerStats.fours_scored.toString()
                    player_innings_batted.text = resp.playerStats.innings_batted.toString()
                    player_not_outs.text = resp.playerStats.times_not_out.toString()
                    player_economy.text = resp.playerStats.bowling_economy.toString()
                    player_strike_rate.text = resp.playerStats.strike_rate.toString()
                    player_bowling_average.text = resp.playerStats.bowling_average.toString()
                    player_best_bowling.text = resp.playerStats.best_bowling
                    player_wickets_taken.text = resp.playerStats.wickets.toString()
                    player_five_wickets.text = resp.playerStats.five_wickets.toString()
                    player_runs_conceded.text = resp.playerStats.total_runs_conceded.toString()
                    player_wides.text = resp.playerStats.wides.toString()
                    player_no_balls.text = resp.playerStats.no_balls.toString()
                    player_overs_bowled.text = resp.playerStats.overs_bowled.toString()
                }
            }
        })
    }
}

private class JSONRseponseStatistics(val playerStats: Statistics)
private class Statistics(val highscore:Int,val fours_scored:Int,val sixes_scored:Int, val overs_bowled:Float, val fifties:Int, val hundreds:Int, val times_not_out:Int, val matches_played:Int, val innings_batted:Int, val total_runs:Int,
                         val batting_average:Float, val wickets:Int, val five_wickets:Int, val best_bowling:String, val bowling_economy:Float, val bowling_average:Float, val strike_rate:Float, val total_runs_conceded:Int, val wides:Int, val no_balls:Int)
