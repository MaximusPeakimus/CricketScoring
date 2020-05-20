package com.example.cricketscoring.AdapterClasses

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscoring.MenuActivity
import com.example.cricketscoring.PlayerGameRecords
import com.example.cricketscoring.R
import kotlinx.android.synthetic.main.player_view.view.*

class BowlerSelectorAdapter(val players:ArrayList<PlayerGameRecords>): RecyclerView.Adapter<BowlerSelectorAdapter.BowlerSelectorCustomViewHolder>()
    {override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): BowlerSelectorCustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.player_view, parent, false)
        return BowlerSelectorCustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return players.count()
    }

    override fun onBindViewHolder(holder: BowlerSelectorCustomViewHolder, position: Int) {
        val player = players.get(position)

        if(player.current_bowling){
            holder.view.setBackgroundResource(R.color.currentlyBowling)
        }

        holder.view.player_name_team_view.text = player.player_name
        holder.view.bowling_action_team_view.text = player.bowling_action
        holder.view.bowling_arm_team_view.text = player.bowling_arm

        holder.player = player
        holder.player_num = position
    }

    inner class BowlerSelectorCustomViewHolder(val view: View, var player:PlayerGameRecords? = null, var player_num:Int? = null): RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener {
                if (player!!.current_bowling){
                    Toast.makeText(view.context, "This player is already bowling", Toast.LENGTH_LONG).show()
                }else{
                    if(MenuActivity.teamScoreInfo[1].batting){
                        MenuActivity.homeTeamsList.forEach { it.current_bowling = false}
                        MenuActivity.homeTeamsList[player_num!!].current_bowling = true
                        view.setBackgroundResource(R.color.selectedPlayer)
                    }else{
                        MenuActivity.awayTeamsList.forEach { it.current_bowling = false}
                        MenuActivity.awayTeamsList[player_num!!].current_bowling = true
                        view.setBackgroundResource(R.color.selectedPlayer)
                    }
                }
            }
        }
    }
}