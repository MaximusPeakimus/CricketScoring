package com.example.cricketscoring

import android.content.Context

class SharedLoginPreference(context:Context) {
    val PREFERENCE_NAME = "loggedInTeamID"
    val PREFERENCE_TEAM_ID = "teamID"
    val PREFERENCE_EMAIL = "email"
    val PREFERENCE__USERNAME = "name"
    val PREFERENCE_USER_ID = "user"

    val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getLoggedInTeamID() : Int{
        return preference.getInt(PREFERENCE_TEAM_ID,0)
    }

    fun setLoggedInTeamID(teamID:Int) {
        val editor = preference.edit()
        editor.putInt(PREFERENCE_TEAM_ID, teamID)
        editor.apply()
    }

    fun getLoggedInEmail() : String? {
        return preference.getString(PREFERENCE_EMAIL, "SIGN OUT")
    }

    fun setLoggedInEmail(email:String){
        val editor = preference.edit()
        editor.putString(PREFERENCE_EMAIL, email)
        editor.apply()
    }

    fun getLoggedInName() : String? {
        return preference.getString(PREFERENCE__USERNAME, "SIGN OUT")
    }

    fun setLoggedInName(name:String){
        val editor = preference.edit()
        editor.putString(PREFERENCE__USERNAME, name)
        editor.apply()
    }

    fun getLoggedInUserId() : Int {
        return preference.getInt(PREFERENCE_USER_ID, 0)
    }

    fun setLoggedInUserId(user_id:Int){
        val editor = preference.edit()
        editor.putInt(PREFERENCE_USER_ID, user_id)
        editor.apply()
    }
}