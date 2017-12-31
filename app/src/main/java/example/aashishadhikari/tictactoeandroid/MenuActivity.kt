package example.aashishadhikari.tictactoeandroid

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.WindowManager

import kotlinx.android.synthetic.main.activity_start_menu.*

/**
 * Created by asis on 6/10/17.
 */
class MenuActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_menu)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val face = Typeface.createFromAsset(
                assets, "handdrawnshapes.otf"
        )

        information.typeface = face

        pvp_online.setOnClickListener {
            startActivity(Intent(this,PlayOnline::class.java))
        }

        pvp_offline.setOnClickListener {
            startActivity(Intent(this,PlayWithFriendsOffline::class.java))
        }
    }
}