package com.example.cricketscoring

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cricketscoring.MenuActivity.Companion.teamScoreInfo
import kotlinx.android.synthetic.main.activity_score_match_toss.*

class ScoreMatchToss : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_match_toss)

        val home_team_id = intent.getIntExtra("home_team_id", 0)
        val away_team_id = intent.getIntExtra("away_team_id", 0)
        val home_team_name = intent.getStringExtra("home_team_name")
        val away_team_name = intent.getStringExtra("away_team_name")
        val match_id = intent.getIntExtra("match_id", 0)

        var team_selected = "home"

        home_team_toss_txt.text = home_team_name
        away_team_toss_txt.text = away_team_name

        toss_select_scorer_back_btn.setOnClickListener {
            teamScoreInfo.clear()
            finish()
        }

        team_select_radio_group.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId==R.id.home_team_won_toss_btn){
                teamScoreInfo.add(TeamGameRecords(home_team_name,home_team_id,match_id,true))
                teamScoreInfo.add(TeamGameRecords(away_team_name,away_team_id,match_id,false))
            }
            if (checkedId==R.id.away_team_won_toss_btn){
                team_selected = "away"
                teamScoreInfo.add(TeamGameRecords(home_team_name,home_team_id,match_id,false))
                teamScoreInfo.add(TeamGameRecords(away_team_name,away_team_id,match_id,true))
            }
        }

        bat_bowl_select_radio_group.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId==R.id.team_select_bat_btn){
                if(team_selected=="home"){
                    teamScoreInfo[0].batting = true
                }else{
                    teamScoreInfo[1].batting = true
                }
            }

            if (checkedId==R.id.team_select_bowl_btn){
                if(team_selected=="home"){
                    teamScoreInfo[1].batting = true
                }else{
                    teamScoreInfo[0].batting = true
                }
            }

            toss_select_scorer_next_btn.setOnClickListener {
                if(teamScoreInfo.isEmpty()){
                    Toast.makeText(this,"Please select the team that won the toss and what they are doing",Toast.LENGTH_SHORT).show()
                }else{
                    startActivity(Intent(this,ScoreMatchScorer::class.java))
                }
            }
        }



    }
}
