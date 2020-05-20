package com.example.cricketscoring.AdapterClasses

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscoring.Fragments.Fixture
import com.example.cricketscoring.Fragments.JSONRepsonseFixture
import com.example.cricketscoring.HomeTeamSelectScorer
import com.example.cricketscoring.MenuActivity.Companion.current_date
import com.example.cricketscoring.MenuActivity.Companion.teams
import com.example.cricketscoring.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fixture_view.view.*

class FixturesAdapter(val fixturesList: JSONRepsonseFixture): RecyclerView.Adapter<FixturesAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.fixture_view, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return fixturesList.fixtures.count()
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val fixt = fixturesList.fixtures.get(position)

        val homeInTeamList = teams.teams.indexOfFirst { it.team_id == fixt.home_team_id }
        val awayInTeamList = teams.teams.indexOfFirst { it.team_id == fixt.away_team_id }

        val homeTeamName = teams.teams[homeInTeamList].team_name
        val awayTeamName = teams.teams[awayInTeamList].team_name

        holder.view.game_date_txt.text = fixt.game_date
        holder.view.game_start_txt.text = fixt.game_time
        holder.view.home_team_txt.text = homeTeamName
        holder.view.away_team_txt.text = awayTeamName
        Picasso.with(holder.view.context).load(teams.teams[homeInTeamList].team_image).into(holder.view.team_1_badge_fixture)
        Picasso.with(holder.view.context).load(teams.teams[awayInTeamList].team_image).into(holder.view.team_2_badge_fixture)

        if(fixt?.game_date == current_date){
            holder.view.score_match_btn.isEnabled = true
        }

        holder.fixture = fixt
        holder.homeTeamName = homeTeamName
        holder.awayTeamName = awayTeamName

    }

    inner class CustomViewHolder(val view: View, var fixture: Fixture? = null, var homeTeamName:String? = null, var awayTeamName:String? = null): RecyclerView.ViewHolder(view) {
        init{
            view.score_match_btn.setOnClickListener {
                val intent = Intent(view.context, HomeTeamSelectScorer::class.java)

                intent.putExtra("home_team_name", homeTeamName)
                intent.putExtra("away_team_name", awayTeamName)
                intent.putExtra("home_team_id", fixture?.home_team_id)
                intent.putExtra("away_team_id", fixture?.away_team_id)
                intent.putExtra("match_id", fixture?.match_id)

                view.context.startActivity(intent)
            }
        }
    }

}

