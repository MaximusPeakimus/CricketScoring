package com.example.cricketscoring.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketscoring.AdapterClasses.ResultsAdapter

import com.example.cricketscoring.R
import com.example.cricketscoring.SharedLoginPreference
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_results.*
import okhttp3.*
import java.io.IOException

class ResultsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        results_recycler_view.layoutManager = LinearLayoutManager(activity)

        val userPreference = SharedLoginPreference(requireContext())

        val loggedInTeamID = userPreference.getLoggedInTeamID()



        getFixtures(loggedInTeamID)
    }

    private fun getFixtures(team_id:Int) {
        val url = "http://10.0.2.2/api/get_results.php"

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

                Log.d("Body", body)

                val gson = GsonBuilder().create()

                val resp = gson.fromJson(body, JSONRepsonseResult::class.java)

                activity?.runOnUiThread {
                    results_recycler_view.adapter = ResultsAdapter(resp)
                }
            }
        })
    }
}

class JSONRepsonseResult(val error:Boolean, val error_msg:String, val results: List<Result>)
class Result(val match_id:Int, val game_date:String, val game_time:String, val home_team_id:Int, val away_team_id:Int, val game_score:String)
