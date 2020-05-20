package com.example.cricketscoring.AdapterClasses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscoring.Fragments.JSONRepsonseLeagueTable
import com.example.cricketscoring.R
import kotlinx.android.synthetic.main.team_league_table_view.view.*

class LeagueTableAdapter(val teamsList: JSONRepsonseLeagueTable): RecyclerView.Adapter<LeagueTableAdapter.TeamLeagueTableCustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamLeagueTableCustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.team_league_table_view, parent, false)
        return TeamLeagueTableCustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return teamsList.league_table.count()
    }

    override fun onBindViewHolder(holder: TeamLeagueTableCustomViewHolder, position: Int) {
        val team=teamsList.league_table.get(position)

        holder.view.team_name_league_table_txt.text = team.team_name
        holder.view.team_wins_league_table_txt.text = team.wins.toString()
        holder.view.team_draws_league_table_txt.text = team.draws.toString()
        holder.view.team_loss_league_table_txt.text = team.loss.toString()
        holder.view.team_abandoned_league_table_txt.text = team.abandoned.toString()
        holder.view.team_points_league_table_txt.text = team.points.toString()

    }


    inner class TeamLeagueTableCustomViewHolder(val view: View): RecyclerView.ViewHolder(view) { }

}