package com.example.cricketscoring.AdapterClasses

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscoring.Fragments.JSONRseponseTeam
import com.example.cricketscoring.Fragments.Player
import com.example.cricketscoring.MenuActivity.Companion.awayTeamsList
import com.example.cricketscoring.PlayerGameRecords
import com.example.cricketscoring.R
import kotlinx.android.synthetic.main.player_view.view.*

class AwayTeamSelectorAdapter(val playersList: JSONRseponseTeam): RecyclerView.Adapter<AwayTeamSelectorAdapter.ScorerSelectAwayTeamCustomViewHolder>() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScorerSelectAwayTeamCustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.player_view, parent, false)
        return ScorerSelectAwayTeamCustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return playersList.players.count()
    }

    override fun onBindViewHolder(holder: ScorerSelectAwayTeamCustomViewHolder, position: Int) {
        val player=playersList.players.get(position)

        if(awayTeamsList.any { it.player_id == player!!.player_id }){
            holder.view.setBackgroundResource(R.color.selectedPlayer)
        }else{
            holder.view.setBackgroundResource(R.color.playerInfoBackground)
        }

        holder.view.player_name_team_view.text = player.player_name
        holder.view.bowling_action_team_view.text = player.bowling_action
        holder.view.bowling_arm_team_view.text = player.bowling_arm
        holder.view.batting_hand_team_view.text = player.batting_hand

        holder.player = player
    }

    @RequiresApi(Build.VERSION_CODES.N)
    inner class ScorerSelectAwayTeamCustomViewHolder(val view: View, var player: Player? = null): RecyclerView.ViewHolder(view) {
        init{
            view.setOnClickListener {
                if(!awayTeamsList.any { it.player_id == player!!.player_id }){
                    view.setBackgroundResource(R.color.selectedPlayer)
                    awayTeamsList.add(PlayerGameRecords(player!!.player_id, player!!.player_name, player!!.bowling_arm, player!!.bowling_action))
                }else{
                    view.setBackgroundResource(R.color.playerInfoBackground)
                    awayTeamsList.removeIf { it.player_id == player!!.player_id }
                }
            }
        }
    }
}