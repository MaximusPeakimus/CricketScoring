package com.example.cricketscoring.AdapterClasses

import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscoring.Fragments.JSONRseponseTeam
import com.example.cricketscoring.Fragments.Player
import com.example.cricketscoring.PlayerInformationActivity
import com.example.cricketscoring.R
import kotlinx.android.synthetic.main.player_view.view.*

class TeamAdapter(val playersList: JSONRseponseTeam): RecyclerView.Adapter<TeamAdapter.TeamCustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamCustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.player_view, parent, false)
        return TeamCustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return playersList.players.count()
    }

    override fun onBindViewHolder(holder: TeamCustomViewHolder, position: Int) {
        val player=playersList.players.get(position)

        holder.view.player_name_team_view.text = player.player_name
        holder.view.bowling_action_team_view.text = player.bowling_action
        holder.view.bowling_arm_team_view.text = player.bowling_arm
        holder.view.batting_hand_team_view.text = player.batting_hand

        holder.player = player
    }


    inner class TeamCustomViewHolder(val view: View, var player: Player? = null): RecyclerView.ViewHolder(view) {
        init{
            view.setOnClickListener {
                val intent = Intent(view.context, PlayerInformationActivity::class.java)

                intent.putExtra("player_name", player?.player_name)
                intent.putExtra("player_id", player?.player_id)

                view.context.startActivity(intent)
            }
        }
    }

}