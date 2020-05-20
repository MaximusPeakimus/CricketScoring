package com.example.cricketscoring.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.cricketscoring.MainActivity
import com.example.cricketscoring.MenuActivity

import com.example.cricketscoring.R
import com.example.cricketscoring.SharedLoginPreference
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.*
import java.io.IOException

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var userPreference = SharedLoginPreference(requireContext())

        profile_username.text = userPreference.getLoggedInName()
        email_profile.text = userPreference.getLoggedInEmail()

        val loggedScorerId = userPreference.getLoggedInUserId()

        update_password_profile_btn.setOnClickListener {
            updatePassword(update_password_profile_et.text.toString(), loggedScorerId)
        }

        logout_btn_profile.setOnClickListener {
            userPreference.setLoggedInTeamID(0)
            userPreference.setLoggedInEmail("")

            startActivity(Intent(context, MainActivity::class.java))

            activity?.finish()
        }
    }

    private fun updatePassword(password: String, loggedUserId: Int) {
        val url = "http://10.0.2.2/api/update_user_password.php"

        val client = OkHttpClient()

        val body = FormBody.Builder()
            .add("password", password)
            .add("user_id", loggedUserId.toString())
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
                val body = response.body()?.string()

                Log.d("response", body)

                activity?.runOnUiThread {
                    Toast.makeText(context,"Password updated",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
