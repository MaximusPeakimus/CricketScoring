package com.example.cricketscoring.AdapterClasses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscoring.Fragments.JSONRepsonseResult
import com.example.cricketscoring.MenuActivity
import com.example.cricketscoring.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.result_view.view.*

class ResultsAdapter(val resultsList: JSONRepsonseResult): RecyclerView.Adapter<ResultsAdapter.ResultCustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultCustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.result_view, parent, false)
        return ResultCustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return resultsList.results.count()
    }

    override fun onBindViewHolder(holder: ResultCustomViewHolder, position: Int) {
        val result = resultsList.results.get(position)

        val homeInTeamList = MenuActivity.teams.teams.indexOfFirst { it.team_id == result.home_team_id }
        val awayInTeamList = MenuActivity.teams.teams.indexOfFirst { it.team_id == result.away_team_id }

        val homeTeamName = MenuActivity.teams.teams[homeInTeamList].team_name
        val awayTeamName = MenuActivity.teams.teams[awayInTeamList].team_name

        holder.view.game_date_result_txt.text = result.game_date
        holder.view.game_start_result_txt.text = result.game_time
        holder.view.home_team_result_txt.text = homeTeamName
        holder.view.away_team_result_txt.text = awayTeamName
        Picasso.with(holder.view.context).load(MenuActivity.teams.teams[homeInTeamList].team_image).into(holder.view.team_1_badge_result)
        Picasso.with(holder.view.context).load(MenuActivity.teams.teams[awayInTeamList].team_image).into(holder.view.team_2_badge_result)
        holder.view.results_game_score_txt.text = result.game_score

    }

    inner class ResultCustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {  }

}