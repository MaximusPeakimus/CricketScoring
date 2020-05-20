package com.example.cricketscoring.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketscoring.AdapterClasses.LeagueTableAdapter

import com.example.cricketscoring.R
import com.example.cricketscoring.SharedLoginPreference
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_league.*
import okhttp3.*
import java.io.IOException

class LeagueFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_league, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        league_table_recycler.layoutManager = LinearLayoutManager(activity)

        val userPreference = SharedLoginPreference(requireContext())

        val loggedInTeamID = userPreference.getLoggedInTeamID()

        getLeagueTable(loggedInTeamID)
    }

    private fun getLeagueTable(team_id:Int) {
        val url = "http://10.0.2.2/api/get_league_table.php"

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

                val resp = gson.fromJson(body, JSONRepsonseLeagueTable::class.java)

                activity?.runOnUiThread {
                    league_table_recycler.adapter = LeagueTableAdapter(resp)
                }
            }
        })
    }
}

class JSONRepsonseLeagueTable(val league_table: List<TeamLeagueTable>)
class TeamLeagueTable(val team_name:String, val wins:Int, val draws:Int, val loss:Int, val abandoned:Int, val points:Int)
