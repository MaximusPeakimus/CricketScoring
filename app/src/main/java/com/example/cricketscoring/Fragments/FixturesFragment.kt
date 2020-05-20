package com.example.cricketscoring.Fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketscoring.AdapterClasses.FixturesAdapter

import com.example.cricketscoring.R
import com.example.cricketscoring.SharedLoginPreference
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_fixtures.*
import okhttp3.*
import java.io.IOException

class FixturesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fixtures, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //create layout manager
        fixtures_recycler_view.layoutManager = LinearLayoutManager(activity)
        //get logged in user
        val userPreference = SharedLoginPreference(requireContext())
        //get logged in team id
        val loggedInTeamID = userPreference.getLoggedInTeamID()


        //get fixtures for that team
        getFixtures(loggedInTeamID)
    }

    private fun getFixtures(team_id:Int) {
        //url to connect to
        val url = "http://10.0.2.2/api/get_fixtures.php"
        //create client
        val client = OkHttpClient()
        //body to request api
        val body = FormBody.Builder()
            .add("team_id", team_id.toString())
            .build()

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object: Callback {
            //if fail print error
            override fun onFailure(call: Call, e: IOException) {
                println(e.toString())
            }
            //convert response to gson and pass it to fixtures adapter
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()

                val gson = GsonBuilder().create()

                val resp = gson.fromJson(body, JSONRepsonseFixture::class.java)

                activity?.runOnUiThread {
                    fixtures_recycler_view.adapter = FixturesAdapter(resp)
                }

            }
        })
    }
}

class JSONRepsonseFixture(val error:Boolean, val error_msg:String, val fixtures: List<Fixture>)
class Fixture(val match_id:Int, val game_date:String, val game_time:String, val home_team_id:Int, val away_team_id:Int)
