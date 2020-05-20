package com.example.cricketscoring.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketscoring.AdapterClasses.TeamAdapter
import com.example.cricketscoring.AddPlayerActivity
import com.example.cricketscoring.R
import com.example.cricketscoring.SharedLoginPreference
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_team.*
import okhttp3.*
import java.io.IOException
import com.example.cricketscoring.MenuActivity.Companion.teams

class TeamFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val userPreference = SharedLoginPreference(requireContext())

        val loggedInTeamID = userPreference.getLoggedInTeamID()

        team_recycler_view.layoutManager = LinearLayoutManager(activity)



        val numTeams = teams.teams.count()

        val teamsToDisplay = List(numTeams){ i -> teams.teams[i].team_name}

        val arrayAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, teamsToDisplay)

        team_stats_select_spinner.adapter = arrayAdapter
        team_stats_select_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                getplayers(loggedInTeamID)
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                getplayers(teams.teams[position].team_id)
            }

        }

        add_player_btn.setOnClickListener {
            val intent = Intent(context, AddPlayerActivity::class.java)

            intent.putExtra("team_id", loggedInTeamID)

            startActivity(intent)
        }
    }

    private fun getplayers(team_id: Int) {
        val url = "http://10.0.2.2/api/get_players.php"

        val client = OkHttpClient()

        val body = FormBody.Builder()
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

                val gson = GsonBuilder().create()

                //Log.d("response", body)

                val resp = gson.fromJson(body, JSONRseponseTeam::class.java)

                activity?.runOnUiThread {
                    team_recycler_view.adapter = TeamAdapter(resp)
                }
            }
        })
    }
}

class JSONRseponseTeam(val error:Boolean, val error_msg:String, val players: List<Player>)
class Player(val player_id: Int, val player_name:String, val bowling_action:String, val bowling_arm:String, val batting_hand:String)
