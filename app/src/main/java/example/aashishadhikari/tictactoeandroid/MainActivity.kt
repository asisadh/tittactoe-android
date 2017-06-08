package example.aashishadhikari.tictactoeandroid

import android.app.Activity
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.TextureView
import android.view.View
import android.view.WindowManager
import android.widget.TextView


// importing views From xml

import kotlinx.android.synthetic.main.activity_main.v0
import kotlinx.android.synthetic.main.activity_main.v1
import kotlinx.android.synthetic.main.activity_main.v2
import kotlinx.android.synthetic.main.activity_main.v3
import kotlinx.android.synthetic.main.activity_main.v4
import kotlinx.android.synthetic.main.activity_main.v5
import kotlinx.android.synthetic.main.activity_main.v6
import kotlinx.android.synthetic.main.activity_main.v7
import kotlinx.android.synthetic.main.activity_main.v8

import kotlinx.android.synthetic.main.activity_main.information


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val face = Typeface.createFromAsset(
                assets, "handdrawnshapes.otf"
        )
        
        information.typeface = face
    }

    fun onBoxClick(v: View){

        when(v.id) {
            R.id.v0 ->{
                v0.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.brain))
            }
            R.id.v1 -> v1.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.brain));
            R.id.v2 -> v2.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.brain));
            R.id.v3 -> v3.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.brain));
            R.id.v4 -> v4.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.brain));
            R.id.v5 -> v5.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.brain));
            R.id.v6 -> v6.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.brain));
            R.id.v7 -> v7.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.brain));
            R.id.v8 -> v8.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.brain));
        }
    }
}
