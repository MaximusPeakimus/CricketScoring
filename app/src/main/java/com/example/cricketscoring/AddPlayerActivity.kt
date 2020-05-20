package com.example.cricketscoring

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.cricketscoring.Fragments.Player
import com.google.gson.GsonBuilder
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_add_player.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.player_view.*
import okhttp3.*
import java.io.IOException

class AddPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_player)

        Slidr.attach(this)

        val team_id = intent.getIntExtra("team_id",0)

        btn_confirm_add_player.setOnClickListener {
            addPlayer(add_player_name.text.toString(), add_player_bowling_action.text.toString(), add_player_bowling_arm.text.toString(), add_player_batting_hand.text.toString(), team_id)
        }
    }

    private fun addPlayer(player_name: String, bowling_action: String, bowling_arm: String, batting_hand: String, team_id:Int) {
        if(player_name==""||bowling_action==""||bowling_arm==""||batting_hand==""){
            Toast.makeText(this@AddPlayerActivity,"Fill out form, some fields missing",Toast.LENGTH_SHORT).show()
            return
        }else{
            val url = "http://10.0.2.2/api/add_player.php"

            val client = OkHttpClient()

            val body = FormBody.Builder()
                .add("player_name", player_name)
                .add("bowling_action", bowling_action)
                .add("bowling_arm", bowling_arm)
                .add("batting_hand", batting_hand)
                .add("team_id", team_id.toString())
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

                    Log.d("response", body)

                    runOnUiThread {
                        Toast.makeText(this@AddPlayerActivity, "Player added sucessfully",Toast.LENGTH_SHORT).show()
                    }
                    finish()
                }
            })
        }
    }
}