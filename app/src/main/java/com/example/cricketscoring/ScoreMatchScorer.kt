package com.example.cricketscoring

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import android.widget.Toast
import com.example.cricketscoring.MenuActivity.Companion.awayTeamsList
import com.example.cricketscoring.MenuActivity.Companion.homeTeamsList
import com.example.cricketscoring.MenuActivity.Companion.teamScoreInfo
import kotlinx.android.synthetic.main.activity_score_match_scorer.*
import okhttp3.*
import java.io.IOException

class ScoreMatchScorer : AppCompatActivity() {
    companion object{
        var ballInOver = 0
        var batStrike = 0
        var bat1 = 0
        var bat2 = 1
        var bowlerNum = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_match_scorer)

        home_team_name_txt.text = teamScoreInfo[0].team_name
        away_team_name_txt.text = teamScoreInfo[1].team_name

        if (teamScoreInfo[0].batting){
            home_team_name_txt.append("*")
            bowlerNum = -1
            awayTeamsList.forEachIndexed { index, PlayerGameRecords ->
                if (PlayerGameRecords.current_bowling) {
                    bowlerNum = index
                }
            }
            if(batStrike == bat1 && (teamScoreInfo[0].balls_faced_team > 0)){
                batStrike = bat2
            }else{
                batStrike = bat1
            }
            if(teamScoreInfo[1].balls_faced_team > 0){
                declare_btn.isEnabled = false
            }
        }else{
            away_team_name_txt.append("*")
            bowlerNum = -1
            homeTeamsList.forEachIndexed { index, PlayerGameRecords ->
                if (PlayerGameRecords.current_bowling) {
                    bowlerNum = index
                }
            }
            if(batStrike == bat1 && (teamScoreInfo[1].balls_faced_team > 0)){
                batStrike = bat2
            }else{
                batStrike = bat1
            }
            if(teamScoreInfo[0].balls_faced_team > 0){
                declare_btn.isEnabled = false
            }
        }

        dot_ball_btn.setOnClickListener {
            if (teamScoreInfo[0].batting) {
                homeTeamsList[batStrike].balls_faced += 1
                teamScoreInfo[0].balls_faced_team += 1
                awayTeamsList[bowlerNum].balls_bowled += 1


            } else {
                awayTeamsList[batStrike].balls_faced += 1
                teamScoreInfo[1].balls_faced_team += 1
                homeTeamsList[bowlerNum].balls_bowled += 1


            }
            ballInOver+=1
            if(ballInOver == 5){
                selectBowler()
            }else{
                updateUI()
            }
        }

        one_run_btn.setOnClickListener {
            if (teamScoreInfo[0].batting) {
                //batter
                homeTeamsList[batStrike].balls_faced += 1
                homeTeamsList[batStrike].runs_scored += 1

                if (batStrike == bat1) {
                    batStrike = bat2
                } else {
                    batStrike = bat1
                }
                //team

                teamScoreInfo[0].balls_faced_team += 1
                teamScoreInfo[0].team_runs += 1

                //bowler
                awayTeamsList[bowlerNum].balls_bowled += 1
                awayTeamsList[bowlerNum].runs_conceded += 1

            } else {
                awayTeamsList[batStrike].balls_faced += 1
                awayTeamsList[batStrike].runs_scored += 1

                if (batStrike == bat1) {
                    batStrike = bat2
                } else {
                    batStrike = bat1
                }
                //team

                teamScoreInfo[1].balls_faced_team += 1
                teamScoreInfo[1].team_runs += 1

                //bowler
                homeTeamsList[bowlerNum].balls_bowled += 1
                homeTeamsList[bowlerNum].runs_conceded += 1
            }

            ballInOver+=1
            if(ballInOver == 5){
                selectBowler()
            }else{
                updateUI()
            }
        }

        two_runs_btn.setOnClickListener {
            if (teamScoreInfo[0].batting) {
                //batter
                homeTeamsList[batStrike].balls_faced += 1
                homeTeamsList[batStrike].runs_scored += 2

                //team

                teamScoreInfo[0].balls_faced_team += 1
                teamScoreInfo[0].team_runs += 2

                //bowler
                awayTeamsList[bowlerNum].balls_bowled += 1
                awayTeamsList[bowlerNum].runs_conceded += 2

            } else {
                awayTeamsList[batStrike].balls_faced += 1
                awayTeamsList[batStrike].runs_scored += 2
                //team

                teamScoreInfo[1].balls_faced_team += 1
                teamScoreInfo[1].team_runs += 2

                //bowler
                homeTeamsList[bowlerNum].balls_bowled += 1
                homeTeamsList[bowlerNum].runs_conceded += 2
            }
            ballInOver+=1
            if(ballInOver == 5){
                selectBowler()
            }else{
                updateUI()
            }
        }

        three_runs_btn.setOnClickListener {
            if (teamScoreInfo[0].batting) {
                //batter
                homeTeamsList[batStrike].balls_faced += 1
                homeTeamsList[batStrike].runs_scored += 3

                if (batStrike == bat1) {
                    batStrike = bat2
                } else {
                    batStrike = bat1
                }
                //team

                teamScoreInfo[0].balls_faced_team += 1
                teamScoreInfo[0].team_runs += 3

                //bowler
                awayTeamsList[bowlerNum].balls_bowled += 1
                awayTeamsList[bowlerNum].runs_conceded += 3

            } else {
                awayTeamsList[batStrike].balls_faced += 1
                awayTeamsList[batStrike].runs_scored += 3

                if (batStrike == bat1) {
                    batStrike = bat2
                } else {
                    batStrike = bat1
                }
                //team

                teamScoreInfo[1].balls_faced_team += 1
                teamScoreInfo[1].team_runs += 3

                //bowler
                homeTeamsList[bowlerNum].balls_bowled += 1
                homeTeamsList[bowlerNum].runs_conceded += 3
            }
            ballInOver+=1
            if(ballInOver == 5){
                selectBowler()
            }else{
                updateUI()
            }
        }

        four_runs_btn.setOnClickListener {
            if (teamScoreInfo[0].batting) {
                //batter
                homeTeamsList[batStrike].balls_faced += 1
                homeTeamsList[batStrike].runs_scored += 4
                homeTeamsList[batStrike].fours_scored += 1
                //team

                teamScoreInfo[0].balls_faced_team += 1
                teamScoreInfo[0].team_runs += 4

                //bowler
                awayTeamsList[bowlerNum].balls_bowled += 1
                awayTeamsList[bowlerNum].runs_conceded += 4

            } else {
                awayTeamsList[batStrike].balls_faced += 1
                awayTeamsList[batStrike].runs_scored += 4
                awayTeamsList[batStrike].fours_scored += 1
                //team
                teamScoreInfo[1].balls_faced_team += 1
                teamScoreInfo[1].team_runs += 4

                //bowler
                homeTeamsList[bowlerNum].balls_bowled += 1
                homeTeamsList[bowlerNum].runs_conceded += 4
            }
            ballInOver+=1
            if(ballInOver == 5){
                selectBowler()
            }else{
                updateUI()
            }
        }

        six_runs_btn.setOnClickListener {
            if (teamScoreInfo[0].batting) {
                //batter
                homeTeamsList[batStrike].balls_faced += 1
                homeTeamsList[batStrike].runs_scored += 6
                homeTeamsList[batStrike].sixes_scored += 1
                //team

                teamScoreInfo[0].balls_faced_team += 1
                teamScoreInfo[0].team_runs += 6

                //bowler
                awayTeamsList[bowlerNum].balls_bowled += 1
                awayTeamsList[bowlerNum].runs_conceded += 6

            } else {
                awayTeamsList[batStrike].balls_faced += 1
                awayTeamsList[batStrike].runs_scored += 6
                awayTeamsList[batStrike].sixes_scored += 1
                //team

                teamScoreInfo[1].balls_faced_team += 1
                teamScoreInfo[1].team_runs += 6

                //bowler
                homeTeamsList[bowlerNum].balls_bowled += 1
                homeTeamsList[bowlerNum].runs_conceded += 6
            }
            ballInOver+=1
            if(ballInOver == 5){
                selectBowler()
            }else{
                updateUI()
            }
        }

        wide_ball_btn.setOnClickListener {
            val popup = PopupMenu(this, wide_ball_btn)
            popup.inflate(R.menu.extras_menu)
            popup.setOnMenuItemClickListener {
                if (teamScoreInfo[0].batting) {
                    when (it.title) {
                        "1" -> {
                            awayTeamsList[bowlerNum].runs_conceded += 1
                            awayTeamsList[bowlerNum].wides += 1
                            teamScoreInfo[0].team_runs += 1
                            updateUI()
                        }
                        "2" -> {
                            awayTeamsList[bowlerNum].runs_conceded += 2
                            awayTeamsList[bowlerNum].wides += 2
                            teamScoreInfo[0].team_runs += 2
                            updateUI()
                        }
                        "3" -> {
                            awayTeamsList[bowlerNum].runs_conceded += 3
                            awayTeamsList[bowlerNum].wides += 3
                            teamScoreInfo[0].team_runs += 3
                            updateUI()
                        }
                        "4" -> {
                            awayTeamsList[bowlerNum].runs_conceded += 4
                            awayTeamsList[bowlerNum].wides += 4
                            teamScoreInfo[0].team_runs += 4
                            updateUI()
                        }
                        "5" -> {
                            awayTeamsList[bowlerNum].runs_conceded += 5
                            awayTeamsList[bowlerNum].wides += 5
                            teamScoreInfo[0].team_runs += 5
                            updateUI()
                        }
                        else -> Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    when (it.title) {
                        "1" -> {
                            homeTeamsList[bowlerNum].runs_conceded += 1
                            homeTeamsList[bowlerNum].wides += 1
                            teamScoreInfo[1].team_runs += 1
                            updateUI()
                        }
                        "2" -> {
                            homeTeamsList[bowlerNum].runs_conceded += 2
                            homeTeamsList[bowlerNum].wides += 2
                            teamScoreInfo[1].team_runs += 2
                            updateUI()
                        }
                        "3" -> {
                            homeTeamsList[bowlerNum].runs_conceded += 3
                            homeTeamsList[bowlerNum].wides += 3
                            teamScoreInfo[1].team_runs += 3
                            updateUI()
                        }
                        "4" -> {
                            homeTeamsList[bowlerNum].runs_conceded += 4
                            homeTeamsList[bowlerNum].wides += 4
                            teamScoreInfo[1].team_runs += 4
                            updateUI()
                        }
                        "5" -> {
                            homeTeamsList[bowlerNum].runs_conceded += 5
                            homeTeamsList[bowlerNum].wides += 5
                            teamScoreInfo[1].team_runs += 5
                            updateUI()
                        }
                        else -> Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
            popup.show()
        }

        no_ball_btn.setOnClickListener {
            val popup = PopupMenu(this, no_ball_btn)
            popup.inflate(R.menu.extras_menu)
            popup.setOnMenuItemClickListener {
                if (teamScoreInfo[0].batting) {
                    when (it.title) {
                        "1" -> {
                            awayTeamsList[bowlerNum].runs_conceded += 1
                            awayTeamsList[bowlerNum].no_balls += 1
                            teamScoreInfo[0].team_runs += 1
                            updateUI()
                        }
                        "2" -> {
                            awayTeamsList[bowlerNum].runs_conceded += 2
                            awayTeamsList[bowlerNum].no_balls += 2
                            teamScoreInfo[0].team_runs += 2
                            homeTeamsList[batStrike].runs_scored += 1
                            if (batStrike == bat1) {
                                batStrike = bat2
                            } else {
                                batStrike = bat1
                            }
                            updateUI()
                        }
                        "3" -> {
                            awayTeamsList[bowlerNum].runs_conceded += 3
                            awayTeamsList[bowlerNum].wides += 3
                            teamScoreInfo[0].team_runs += 3
                            homeTeamsList[batStrike].runs_scored += 2
                            updateUI()
                        }
                        "4" -> {
                            awayTeamsList[bowlerNum].runs_conceded += 4
                            awayTeamsList[bowlerNum].wides += 4
                            teamScoreInfo[0].team_runs += 4
                            homeTeamsList[batStrike].runs_scored += 3
                            if (batStrike == bat1) {
                                batStrike = bat2
                            } else {
                                batStrike = bat1
                            }
                            updateUI()
                        }
                        "5" -> {
                            awayTeamsList[bowlerNum].runs_conceded += 5
                            awayTeamsList[bowlerNum].wides += 5
                            teamScoreInfo[0].team_runs += 5
                            homeTeamsList[batStrike].runs_scored += 4
                            homeTeamsList[batStrike].fours_scored += 1
                            updateUI()
                        }
                        else -> Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    when (it.title) {
                        "1" -> {
                            homeTeamsList[bowlerNum].runs_conceded += 1
                            homeTeamsList[bowlerNum].no_balls += 1
                            teamScoreInfo[1].team_runs += 1
                            updateUI()
                        }
                        "2" -> {
                            homeTeamsList[bowlerNum].runs_conceded += 2
                            homeTeamsList[bowlerNum].no_balls += 2
                            teamScoreInfo[1].team_runs += 2
                            awayTeamsList[batStrike].runs_scored += 1
                            if (batStrike == bat1) {
                                batStrike = bat2
                            } else {
                                batStrike = bat1
                            }
                            updateUI()
                        }
                        "3" -> {
                            homeTeamsList[bowlerNum].runs_conceded += 3
                            homeTeamsList[bowlerNum].no_balls += 3
                            teamScoreInfo[1].team_runs += 3
                            awayTeamsList[batStrike].runs_scored += 2
                            updateUI()
                        }
                        "4" -> {
                            homeTeamsList[bowlerNum].runs_conceded += 4
                            homeTeamsList[bowlerNum].no_balls += 4
                            teamScoreInfo[1].team_runs += 4
                            awayTeamsList[batStrike].runs_scored += 3
                            if (batStrike == bat1) {
                                batStrike = bat2
                            } else {
                                batStrike = bat1
                            }
                            updateUI()
                        }
                        "5" -> {
                            homeTeamsList[bowlerNum].runs_conceded += 5
                            homeTeamsList[bowlerNum].no_balls += 5
                            teamScoreInfo[1].team_runs += 5
                            awayTeamsList[batStrike].runs_scored += 4
                            awayTeamsList[batStrike].fours_scored += 1
                            updateUI()
                        }
                        else -> Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
            popup.show()
        }

        bye_btn.setOnClickListener {
            val popup = PopupMenu(this, bye_btn)
            popup.inflate(R.menu.extras_byes_menu)
            popup.setOnMenuItemClickListener {
                if (teamScoreInfo[0].batting) {
                    when (it.title) {
                        "1" -> {
                            awayTeamsList[bowlerNum].balls_bowled += 1
                            teamScoreInfo[0].balls_faced_team += 1
                            teamScoreInfo[0].team_extras += 1
                            teamScoreInfo[0].team_runs += 1
                            if (batStrike == bat1) {
                                batStrike = bat2
                            } else {
                                batStrike = bat1
                            }
                            ballInOver+=1
                            if(ballInOver == 5){
                                selectBowler()
                            }else{
                                updateUI()
                            }
                        }
                        "2" -> {
                            awayTeamsList[bowlerNum].balls_bowled += 1
                            teamScoreInfo[0].balls_faced_team += 1
                            teamScoreInfo[0].team_extras += 2
                            teamScoreInfo[0].team_runs += 2
                            ballInOver+=1
                            if(ballInOver == 5){
                                selectBowler()
                            }else{
                                updateUI()
                            }
                        }
                        "3" -> {
                            awayTeamsList[bowlerNum].balls_bowled += 1
                            teamScoreInfo[0].balls_faced_team += 1
                            teamScoreInfo[0].team_extras += 3
                            teamScoreInfo[0].team_runs += 3
                            if (batStrike == bat1) {
                                batStrike = bat2
                            } else {
                                batStrike = bat1
                            }
                            ballInOver+=1
                            if(ballInOver == 5){
                                selectBowler()
                            }else{
                                updateUI()
                            }
                        }
                        "4" -> {
                            awayTeamsList[bowlerNum].balls_bowled += 1
                            teamScoreInfo[0].balls_faced_team += 1
                            teamScoreInfo[0].team_extras += 4
                            teamScoreInfo[0].team_runs += 4
                            ballInOver+=1
                            if(ballInOver == 5){
                                selectBowler()
                            }else{
                                updateUI()
                            }
                        }
                        else -> Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    when (it.title) {
                        "1" -> {
                            homeTeamsList[bowlerNum].balls_bowled += 1
                            teamScoreInfo[1].balls_faced_team += 1
                            teamScoreInfo[1].team_extras += 1
                            teamScoreInfo[1].team_runs += 1
                            if (batStrike == bat1) {
                                batStrike = bat2
                            } else {
                                batStrike = bat1
                            }
                            ballInOver+=1
                            if(ballInOver == 5){
                                selectBowler()
                            }else{
                                updateUI()
                            }
                        }
                        "2" -> {
                            homeTeamsList[bowlerNum].balls_bowled += 1
                            teamScoreInfo[1].balls_faced_team += 1
                            teamScoreInfo[1].team_extras += 2
                            teamScoreInfo[1].team_runs += 2
                            ballInOver+=1
                            if(ballInOver == 5){
                                selectBowler()
                            }else{
                                updateUI()
                            }
                        }
                        "3" -> {
                            homeTeamsList[bowlerNum].balls_bowled += 1
                            teamScoreInfo[1].balls_faced_team += 1
                            teamScoreInfo[1].team_extras += 3
                            teamScoreInfo[1].team_runs += 3
                            if (batStrike == bat1) {
                                batStrike = bat2
                            } else {
                                batStrike = bat1
                            }
                            ballInOver+=1
                            if(ballInOver == 5){
                                selectBowler()
                            }else{
                                updateUI()
                            }
                        }
                        "4" -> {
                            homeTeamsList[bowlerNum].balls_bowled += 1
                            teamScoreInfo[1].balls_faced_team += 1
                            teamScoreInfo[1].team_extras += 4
                            teamScoreInfo[1].team_runs += 4
                            ballInOver+=1
                            if(ballInOver == 5){
                                selectBowler()
                            }else{
                                updateUI()
                            }
                        }
                        else -> Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
            popup.show()
        }

        leg_bye_btn.setOnClickListener {
            val popup = PopupMenu(this, bye_btn)
            popup.inflate(R.menu.extras_byes_menu)
            popup.setOnMenuItemClickListener {
                if (teamScoreInfo[0].batting) {
                    when (it.title) {
                        "1" -> {
                            awayTeamsList[bowlerNum].balls_bowled += 1
                            teamScoreInfo[0].balls_faced_team += 1
                            teamScoreInfo[0].team_extras += 1
                            teamScoreInfo[0].team_runs += 1
                            if (batStrike == bat1) {
                                batStrike = bat2
                            } else {
                                batStrike = bat1
                            }
                            ballInOver+=1
                            if(ballInOver == 5){
                                selectBowler()
                            }else{
                                updateUI()
                            }
                        }
                        "2" -> {
                            awayTeamsList[bowlerNum].balls_bowled += 1
                            teamScoreInfo[0].balls_faced_team += 1
                            teamScoreInfo[0].team_extras += 2
                            teamScoreInfo[0].team_runs += 2
                            ballInOver+=1
                            if(ballInOver == 5){
                                selectBowler()
                            }else{
                                updateUI()
                            }
                        }
                        "3" -> {
                            awayTeamsList[bowlerNum].balls_bowled += 1
                            teamScoreInfo[0].balls_faced_team += 1
                            teamScoreInfo[0].team_extras += 3
                            teamScoreInfo[0].team_runs += 3
                            if (batStrike == bat1) {
                                batStrike = bat2
                            } else {
                                batStrike = bat1
                            }
                            ballInOver+=1
                            if(ballInOver == 5){
                                selectBowler()
                            }else{
                                updateUI()
                            }
                        }
                        "4" -> {
                            awayTeamsList[bowlerNum].balls_bowled += 1
                            teamScoreInfo[0].balls_faced_team += 1
                            teamScoreInfo[0].team_extras += 4
                            teamScoreInfo[0].team_runs += 4
                            ballInOver+=1
                            if(ballInOver == 5){
                                selectBowler()
                            }else{
                                updateUI()
                            }
                        }
                        else -> Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    when (it.title) {
                        "1" -> {
                            homeTeamsList[bowlerNum].balls_bowled += 1
                            teamScoreInfo[1].balls_faced_team += 1
                            teamScoreInfo[1].team_extras += 1
                            teamScoreInfo[1].team_runs += 1
                            if (batStrike == bat1) {
                                batStrike = bat2
                            } else {
                                batStrike = bat1
                            }
                            ballInOver+=1
                            if(ballInOver == 5){
                                selectBowler()
                            }else{
                                updateUI()
                            }
                        }
                        "2" -> {
                            homeTeamsList[bowlerNum].balls_bowled += 1
                            teamScoreInfo[1].balls_faced_team += 1
                            teamScoreInfo[1].team_extras += 2
                            teamScoreInfo[1].team_runs += 2
                            ballInOver+=1
                            if(ballInOver == 5){
                                selectBowler()
                            }else{
                                updateUI()
                            }
                        }
                        "3" -> {
                            homeTeamsList[bowlerNum].balls_bowled += 1
                            teamScoreInfo[1].balls_faced_team += 1
                            teamScoreInfo[1].team_extras += 3
                            teamScoreInfo[1].team_runs += 3
                            if (batStrike == bat1) {
                                batStrike = bat2
                            } else {
                                batStrike = bat1
                            }
                            ballInOver+=1
                            if(ballInOver == 5){
                                selectBowler()
                            }else{
                                updateUI()
                            }
                        }
                        "4" -> {
                            homeTeamsList[bowlerNum].balls_bowled += 1
                            teamScoreInfo[1].balls_faced_team += 1
                            teamScoreInfo[1].team_extras += 4
                            teamScoreInfo[1].team_runs += 4
                            ballInOver+=1
                            if(ballInOver == 5){
                                selectBowler()
                            }else{
                                updateUI()
                            }
                        }
                        else -> Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
            popup.show()
        }

        penalty_runs_btn.setOnClickListener {
            if (teamScoreInfo[0].batting) {
                teamScoreInfo[0].team_runs += 5

            } else {
                teamScoreInfo[1].team_runs += 5
            }
            updateUI()
        }

        wicket_taken_btn.setOnClickListener {
            if (bat2 != 10) {
                if (teamScoreInfo[0].batting) {
                    //batter
                    homeTeamsList[batStrike].balls_faced += 1
                    homeTeamsList[batStrike].batted_out = 1

                    //team
                    teamScoreInfo[0].balls_faced_team += 1
                    teamScoreInfo[0].team_wickets += 1

                    //bowler
                    awayTeamsList[bowlerNum].balls_bowled += 1
                    awayTeamsList[bowlerNum].wickets += 1

                    //switch batters
                    if (batStrike == bat1) {
                        bat1 = bat2
                        batStrike = bat1
                        bat2 += 1
                    } else {
                        bat2 += 1
                        batStrike = bat2
                    }
                } else {
                    awayTeamsList[batStrike].balls_faced += 1
                    awayTeamsList[batStrike].batted_out = 1
                    //team

                    teamScoreInfo[1].balls_faced_team += 1
                    teamScoreInfo[1].team_wickets += 1

                    //bowler
                    homeTeamsList[bowlerNum].balls_bowled += 1
                    homeTeamsList[bowlerNum].wickets += 1

                    //switch batters
                    if (batStrike == bat1) {
                        bat1 = bat2
                        batStrike = bat1
                        bat2 += 1
                    } else {
                        bat2 += 1
                        batStrike = bat2
                    }
                }
                ballInOver+=1
                if(ballInOver == 5){
                    selectBowler()
                }else{
                    updateUI()
                }
            }else{
                if(teamScoreInfo[0].batting && (teamScoreInfo[1].balls_faced_team == 0)){
                    homeTeamsList[batStrike].balls_faced += 1
                    homeTeamsList[batStrike].batted_out = 1

                    //team
                    teamScoreInfo[0].balls_faced_team += 1
                    teamScoreInfo[0].team_wickets += 1

                    //bowler
                    awayTeamsList[bowlerNum].balls_bowled += 1
                    awayTeamsList[bowlerNum].wickets += 1

                    batStrike = 0
                    bat1 = 0
                    bat2 = 1
                    teamScoreInfo[0].batting = false
                    teamScoreInfo[1].batting = true
                    bowlerNum = -1
                    ballInOver+=1
                    if(ballInOver == 5){
                        selectBowler()
                    }else{
                        updateUI()
                    }
                }else if(teamScoreInfo[1].batting && (teamScoreInfo[0].balls_faced_team == 0)){

                    awayTeamsList[batStrike].balls_faced += 1
                    awayTeamsList[batStrike].batted_out = 1
                    //team

                    teamScoreInfo[1].balls_faced_team += 1
                    teamScoreInfo[1].team_wickets += 1

                    //bowler
                    homeTeamsList[bowlerNum].balls_bowled += 1
                    homeTeamsList[bowlerNum].wickets += 1

                    batStrike = 0
                    bat1 = 0
                    bat2 = 1
                    teamScoreInfo[0].batting = true
                    teamScoreInfo[1].batting = false
                    bowlerNum = -1
                    ballInOver+=1
                    if(ballInOver == 5){
                        selectBowler()
                    }else{
                        updateUI()
                    }
                }else{
                    if(teamScoreInfo[0].batting){
                        homeTeamsList[batStrike].balls_faced += 1
                        homeTeamsList[batStrike].batted_out = 1

                        //team
                        teamScoreInfo[0].balls_faced_team += 1
                        teamScoreInfo[0].team_wickets += 1

                        //bowler
                        awayTeamsList[bowlerNum].balls_bowled += 1
                        awayTeamsList[bowlerNum].wickets += 1

                        endGame()
                    }else if(teamScoreInfo[1].batting){
                        awayTeamsList[batStrike].balls_faced += 1
                        awayTeamsList[batStrike].batted_out = 1
                        //team

                        teamScoreInfo[1].balls_faced_team += 1
                        teamScoreInfo[1].team_wickets += 1

                        //bowler
                        homeTeamsList[bowlerNum].balls_bowled += 1
                        homeTeamsList[bowlerNum].wickets += 1

                        endGame()
                    }
                }
            }
        }

        declare_btn.setOnClickListener {
            if(teamScoreInfo[0].batting){
                batStrike = 0
                bat1 = 0
                bat2 = 1
                teamScoreInfo[0].batting = false
                teamScoreInfo[1].batting = true
                bowlerNum = -1
                updateUI()
            }else{
                batStrike = 0
                bat1 = 0
                bat2 = 1
                teamScoreInfo[0].batting = true
                teamScoreInfo[1].batting = false
                bowlerNum = -1
                updateUI()
            }
        }

        abandon_match_btn.setOnClickListener {
            val popup = PopupMenu(this, bye_btn)
            popup.inflate(R.menu.extras_byes_menu)
            popup.setOnMenuItemClickListener {
                when (it.title) {
                    "Yes" -> {endGame("abandon")}
                    "No" -> {Toast.makeText(this, "Don't abandon match", Toast.LENGTH_LONG).show()}
                     else -> Log.d("Error", "Error")
                }
                true
            }
        }

        ballInOver = -1
        enableAllScoreButtons()
        updateUI()
    }

    private fun endGame(type:String = "Normal") {
        finish_match_btn.isEnabled = true
        declare_btn.isEnabled = false
        abandon_match_btn.isEnabled = false
        game_scoring_back_btn.isEnabled = false
        disableAllScoreButtons()
        finish_match_btn.setOnClickListener {
            if(type == "abandon"){
                homeTeamsList.forEach { player: PlayerGameRecords ->
                    postPlayersInfo(player.player_id, player.runs_scored, player.balls_faced, player.fours_scored, player.sixes_scored, player.batted_out, player.balls_bowled, player.runs_conceded, player.wickets, player.wides, player.no_balls, teamScoreInfo[0].match_id)
                }
                awayTeamsList.forEach { player: PlayerGameRecords ->
                    postPlayersInfo(player.player_id, player.runs_scored, player.balls_faced, player.fours_scored, player.sixes_scored, player.batted_out, player.balls_bowled, player.runs_conceded, player.wickets, player.wides, player.no_balls, teamScoreInfo[1].match_id)
                }
                postHomeTeamInfo("A",8)
                postAwayTeamInfo("A",8)
                val gameScore = teamScoreInfo[0].team_runs.toString() + "/" + teamScoreInfo[0].team_wickets.toString() + " v " +
                        teamScoreInfo[1].team_runs.toString() + "/" + teamScoreInfo[1].team_wickets.toString() + ", " +
                        "match abandoned"
                postMatchInfo(gameScore)
                startActivity(Intent(this, MenuActivity::class.java))
                finish()
            }else{
                if(teamScoreInfo[0].team_runs == teamScoreInfo[1].team_runs){
                    homeTeamsList.forEach { player: PlayerGameRecords ->
                        postPlayersInfo(player.player_id, player.runs_scored, player.balls_faced, player.fours_scored, player.sixes_scored, player.batted_out, player.balls_bowled, player.runs_conceded, player.wickets, player.wides, player.no_balls, teamScoreInfo[0].match_id)
                    }
                    awayTeamsList.forEach { player: PlayerGameRecords ->
                        postPlayersInfo(player.player_id, player.runs_scored, player.balls_faced, player.fours_scored, player.sixes_scored, player.batted_out, player.balls_bowled, player.runs_conceded, player.wickets, player.wides, player.no_balls, teamScoreInfo[1].match_id)
                    }

                    var tossWonBy = ""
                    if(teamScoreInfo[0].won_toss){
                        tossWonBy = teamScoreInfo[0].team_name
                    }else{
                        tossWonBy = teamScoreInfo[1].team_name
                    }
                    postHomeTeamInfo("D",8)
                    postAwayTeamInfo("D",8)
                    val gameScore = teamScoreInfo[0].team_runs.toString() + "/" + teamScoreInfo[0].team_wickets.toString() + " v " +
                            teamScoreInfo[1].team_runs.toString() + "/" + teamScoreInfo[1].team_wickets.toString() + ", " +
                            "match drawn. Toss was won by " + tossWonBy
                    postMatchInfo(gameScore)
                    startActivity(Intent(this, MenuActivity::class.java))
                    finish()
                }else if(teamScoreInfo[0].team_runs > teamScoreInfo[1].team_runs){
                    homeTeamsList.forEach { player: PlayerGameRecords ->
                        postPlayersInfo(player.player_id, player.runs_scored, player.balls_faced, player.fours_scored, player.sixes_scored, player.batted_out, player.balls_bowled, player.runs_conceded, player.wickets, player.wides, player.no_balls, teamScoreInfo[0].match_id)
                    }
                    awayTeamsList.forEach { player: PlayerGameRecords ->
                        postPlayersInfo(player.player_id, player.runs_scored, player.balls_faced, player.fours_scored, player.sixes_scored, player.batted_out, player.balls_bowled, player.runs_conceded, player.wickets, player.wides, player.no_balls, teamScoreInfo[1].match_id)
                    }

                    var tossWonBy = ""
                    if(teamScoreInfo[0].won_toss){
                        tossWonBy = teamScoreInfo[0].team_name
                    }else{
                        tossWonBy = teamScoreInfo[1].team_name
                    }
                    postHomeTeamInfo("W",22)
                    postAwayTeamInfo("L",0)
                    val gameScore = teamScoreInfo[0].team_runs.toString() + "/" + teamScoreInfo[0].team_wickets.toString() + " v " +
                            teamScoreInfo[1].team_runs.toString() + "/" + teamScoreInfo[1].team_wickets.toString() + ", " +
                            teamScoreInfo[0].team_name + " won. Toss was won by " + tossWonBy
                    postMatchInfo(gameScore)
                    startActivity(Intent(this, MenuActivity::class.java))
                    finish()
                }else{
                    homeTeamsList.forEach { player: PlayerGameRecords ->
                        postPlayersInfo(player.player_id, player.runs_scored, player.balls_faced, player.fours_scored, player.sixes_scored, player.batted_out, player.balls_bowled, player.runs_conceded, player.wickets, player.wides, player.no_balls, teamScoreInfo[0].match_id)
                    }
                    awayTeamsList.forEach { player: PlayerGameRecords ->
                        postPlayersInfo(player.player_id, player.runs_scored, player.balls_faced, player.fours_scored, player.sixes_scored, player.batted_out, player.balls_bowled, player.runs_conceded, player.wickets, player.wides, player.no_balls, teamScoreInfo[1].match_id)
                    }

                    var tossWonBy = ""
                    if(teamScoreInfo[0].won_toss){
                        tossWonBy = teamScoreInfo[0].team_name
                    }else{
                        tossWonBy = teamScoreInfo[1].team_name
                    }
                    postHomeTeamInfo("L",0)
                    postAwayTeamInfo("W",22)
                    val gameScore = teamScoreInfo[0].team_runs.toString() + "/" + teamScoreInfo[0].team_wickets.toString() + " v " +
                            teamScoreInfo[1].team_runs.toString() + "/" + teamScoreInfo[1].team_wickets.toString() + ", " +
                            teamScoreInfo[1].team_name + " won. Toss was won by " + tossWonBy
                    postMatchInfo(gameScore)
                    startActivity(Intent(this, MenuActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun postMatchInfo(result:String) {
        val url = "http://10.0.2.2/api/add_game_score.php"

        val client = OkHttpClient()

        val body = FormBody.Builder()
            .add("match_id", teamScoreInfo[0].match_id.toString())
            .add("game_score", result)
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
            }
        })
    }

    private fun postAwayTeamInfo(team_result:String, team_points:Int) {
        val url = "http://10.0.2.2/api/add_team_game_stats.php"

        val client = OkHttpClient()

        val body = FormBody.Builder()
            .add("team_id", teamScoreInfo[1].team_id.toString())
            .add("match_id", teamScoreInfo[1].match_id.toString())
            .add("game_result", team_result)
            .add("game_points", team_points.toString())
            .add("home_away", "Away")
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
            }
        })
    }

    private fun postHomeTeamInfo(team_result:String, team_points:Int) {
        val url = "http://10.0.2.2/api/add_team_game_stats.php"

        val client = OkHttpClient()

        val body = FormBody.Builder()
            .add("team_id", teamScoreInfo[0].team_id.toString())
            .add("match_id", teamScoreInfo[0].match_id.toString())
            .add("game_result", team_result)
            .add("game_points", team_points.toString())
            .add("home_away", "Home")
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
            }
        })
    }

    private fun postPlayersInfo(player_id: Int, runs_scored: Int, balls_faced: Int, fours_scored: Int, sixes_scored: Int, batted_out: Int, balls_bowled: Int,runs_conceded: Int, wickets: Int, wides: Int, no_balls: Int, match_id: Int) {
        val url = "http://10.0.2.2/api/add_player_game_stats.php"

        val client = OkHttpClient()

        val body = FormBody.Builder()
            .add("player_id", player_id.toString())
            .add("runs_scored", runs_scored.toString())
            .add("balls_faced", convertBallsToOvers(balls_faced).toString())
            .add("fours_scored", fours_scored.toString())
            .add("sixes_scored", sixes_scored.toString())
            .add("batted_out", batted_out.toString())
            .add("overs_bowled", convertBallsToOvers(balls_bowled).toString())
            .add("runs_conceded", runs_conceded.toString())
            .add("wickets", wickets.toString())
            .add("wides", wides.toString())
            .add("no_balls", no_balls.toString())
            .add("match_id", match_id.toString())
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
            }
        })
    }

    private fun disableAllScoreButtons() {
        dot_ball_btn.isEnabled = false
        one_run_btn.isEnabled = false
        two_runs_btn.isEnabled = false
        three_runs_btn.isEnabled = false
        four_runs_btn.isEnabled = false
        six_runs_btn.isEnabled = false
        wide_ball_btn.isEnabled = false
        no_ball_btn.isEnabled = false
        bye_btn.isEnabled = false
        leg_bye_btn.isEnabled = false
        penalty_runs_btn.isEnabled = false
        wicket_taken_btn.isEnabled = false
    }

    private fun enableAllScoreButtons(){
        dot_ball_btn.isEnabled = true
        one_run_btn.isEnabled = true
        two_runs_btn.isEnabled = true
        three_runs_btn.isEnabled = true
        four_runs_btn.isEnabled = true
        six_runs_btn.isEnabled = true
        wide_ball_btn.isEnabled = true
        no_ball_btn.isEnabled = true
        bye_btn.isEnabled = true
        leg_bye_btn.isEnabled = true
        penalty_runs_btn.isEnabled = true
        wicket_taken_btn.isEnabled = true
    }

    private fun selectBowler() {
        if(teamScoreInfo[0].batting && (teamScoreInfo[0].balls_faced_team == 6)){
            if(teamScoreInfo[1].balls_faced_team > 0){
                updateUI()
                endGame()
            }else{
                teamScoreInfo[0].batting = false
                teamScoreInfo[1].batting = true
                startActivity(Intent(this, SelectBowlerActivity::class.java))
                finish()
            }
        }else if(teamScoreInfo[1].batting && (teamScoreInfo[1].balls_faced_team == 6)){
            if(teamScoreInfo[0].balls_faced_team > 0){
                updateUI()
                endGame()
            }else{
                teamScoreInfo[0].batting = true
                teamScoreInfo[1].batting = false
                startActivity(Intent(this, SelectBowlerActivity::class.java))
                finish()
            }
        }else{
            startActivity(Intent(this, SelectBowlerActivity::class.java))
            finish()
        }
    }

    private fun updateUI() {

        //var bowlerSelected = false

        if(teamScoreInfo[1].batting){
            //set up bowler
            homeTeamsList.forEachIndexed { index, PlayerGameRecords ->
                if(PlayerGameRecords.current_bowling){
                    bowlerNum = index
                    bowler_name.text = PlayerGameRecords.player_name
                    overs_bowled_current.text = convertBallsToOvers(PlayerGameRecords.balls_bowled).toString()
                    runs_conceded_current.text = PlayerGameRecords.runs_conceded.toString()
                    wickets_taken_current.text = PlayerGameRecords.wickets.toString()
                }
            }
            //get new bowler
            if(bowlerNum<0){
                selectBowler()
            }

            batter1_name.text = awayTeamsList[bat1].player_name
            batter1_runs.text = awayTeamsList[bat1].runs_scored.toString()
            batter1_balls.text = awayTeamsList[bat1].balls_faced.toString()
            batter1_fours.text = awayTeamsList[bat1].fours_scored.toString()
            batter1_sixes.text = awayTeamsList[bat1].sixes_scored.toString()

            batter2_name.text = awayTeamsList[bat2].player_name
            batter2_runs.text = awayTeamsList[bat2].runs_scored.toString()
            batter2_balls.text = awayTeamsList[bat2].balls_faced.toString()
            batter2_fours.text = awayTeamsList[bat2].fours_scored.toString()
            batter2_sixes.text = awayTeamsList[bat2].sixes_scored.toString()

            if(batStrike == bat1){
                batter1_name.append("*")
            }else{
                batter2_name.append("*")
            }


            updateTeamScores()


        }else{
            awayTeamsList.forEachIndexed { index, PlayerGameRecords ->
                if(PlayerGameRecords.current_bowling){
                    bowlerNum = index
                    bowler_name.text = PlayerGameRecords.player_name
                    overs_bowled_current.text = convertBallsToOvers(PlayerGameRecords.balls_bowled).toString()
                    runs_conceded_current.text = PlayerGameRecords.runs_conceded.toString()
                    wickets_taken_current.text = PlayerGameRecords.wickets.toString()
                }
            }

            if(bowlerNum<0){
                selectBowler()
            }

            batter1_name.text = homeTeamsList[bat1].player_name
            batter1_runs.text = homeTeamsList[bat1].runs_scored.toString()
            batter1_balls.text = homeTeamsList[bat1].balls_faced.toString()
            batter1_fours.text = homeTeamsList[bat1].fours_scored.toString()
            batter1_sixes.text = homeTeamsList[bat1].sixes_scored.toString()

            batter2_name.text = homeTeamsList[bat2].player_name
            batter2_runs.text = homeTeamsList[bat2].runs_scored.toString()
            batter2_balls.text = homeTeamsList[bat2].balls_faced.toString()
            batter2_fours.text = homeTeamsList[bat2].fours_scored.toString()
            batter2_sixes.text = homeTeamsList[bat2].sixes_scored.toString()

            if(batStrike == bat1){
                batter1_name.append("*")
            }else{
                batter2_name.append("*")
            }


            updateTeamScores()
        }


    }

    private fun convertBallsToOvers(balls:Int): Double{
        Log.d("balls", balls.toString())

        val remainder = balls % 6



        if(remainder == 0){
            return (balls/6).toDouble()
        }


        val wholeOvers = kotlin.math.floor((balls / 6).toDouble())

        val ball = (remainder.toDouble()/10)

        Log.d("balls rem", ball.toString())

        val toRet = (wholeOvers + ball)

        Log.d("return", toRet.toString())

        return toRet
    }

    private fun updateTeamScores() {
        val homeScore = teamScoreInfo[0].team_runs.toString() + "/" + teamScoreInfo[0].team_wickets.toString()
        val awayScore = teamScoreInfo[1].team_runs.toString() + "/" + teamScoreInfo[1].team_wickets.toString()

        home_team_score_txt.text = homeScore
        away_team_score_txt.text = awayScore
        home_team_overs_txt.text = convertBallsToOvers(teamScoreInfo[0].balls_faced_team).toString()
        away_team_overs_txt.text = convertBallsToOvers(teamScoreInfo[1].balls_faced_team).toString()
    }

}
class PlayerGameRecords(val player_id:Int, val player_name:String, val bowling_arm:String, val bowling_action:String, var runs_scored:Int = 0,
                        var balls_faced:Int = 0, var fours_scored:Int = 0, var sixes_scored:Int = 0,
                        var batted_out:Int = 0, var balls_bowled: Int = 0, var runs_conceded:Int =0,
                        var wickets:Int = 0, var wides:Int = 0, var no_balls:Int = 0, var current_bowling:Boolean = false)

class TeamGameRecords(var team_name:String, var team_id:Int, var match_id:Int, var won_toss:Boolean = false, var batting:Boolean = false, var team_extras:Int = 0, var team_runs:Int = 0, var team_wickets:Int = 0, var balls_faced_team:Int = 0)