package com.example.cricketscoring

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentTransaction
import com.example.cricketscoring.Fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MenuActivity : AppCompatActivity() {

    companion object{
        lateinit var teams:Teams
        lateinit var current_date:String
        var homeTeamsList = ArrayList<PlayerGameRecords>()
        var awayTeamsList = ArrayList<PlayerGameRecords>()
        var teamScoreInfo = ArrayList<TeamGameRecords>()
    }

    lateinit var fixturesFragment:FixturesFragment
    lateinit var resultsFragment: ResultsFragment
    lateinit var leagueFragment: LeagueFragment
    lateinit var teamFragment: TeamFragment
    lateinit var proifleFragment: ProfileFragment

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyy-MM-dd")
        current_date = current.format(formatter)

        val bottomNavigation : BottomNavigationView = findViewById(R.id.bottom_navigation)

        fixturesFragment = FixturesFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, fixturesFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.fixtures ->{
                    fixturesFragment = FixturesFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, fixturesFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.results ->{
                    resultsFragment = ResultsFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, resultsFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.league_table ->{
                    leagueFragment = LeagueFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, leagueFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.team ->{
                    teamFragment = TeamFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, teamFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.profile ->{
                    proifleFragment = ProfileFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, proifleFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }
            true
        }

        var userPreference = SharedLoginPreference(this)

        val loggedInTeamID = userPreference.getLoggedInTeamID()

        setTeams(loggedInTeamID)
    }

    private fun setTeams(team_id: Int) {
        val url = "http://10.0.2.2/api/get_teams_league.php"

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

                val resp = gson.fromJson(body, Teams::class.java)

                teams = resp
            }
        })
    }
}

class Teams (var teams:List<Team>)
class Team (var team_id:Int, var team_name:String, val team_email:String, val team_phone_number1:String, val team_phone_number2:String, val team_league:String, val team_address:String, val team_image:String)
