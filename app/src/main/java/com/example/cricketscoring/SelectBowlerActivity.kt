package com.example.cricketscoring

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketscoring.AdapterClasses.BowlerSelectorAdapter
import kotlinx.android.synthetic.main.activity_select_bowler.*

class SelectBowlerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_bowler)

        bowler_selector_recycler_view.layoutManager = LinearLayoutManager(this)

        if(MenuActivity.teamScoreInfo[0].batting){
            bowler_selector_recycler_view.adapter = BowlerSelectorAdapter(MenuActivity.awayTeamsList)
        }else{
            bowler_selector_recycler_view.adapter = BowlerSelectorAdapter(MenuActivity.homeTeamsList)
        }

        confirm_bowler_btn.setOnClickListener {
            startActivity(Intent(this, ScoreMatchScorer::class.java))
            finish()
        }
    }
}
