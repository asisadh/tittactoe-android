package example.aashishadhikari.tictactoeandroid

import android.app.Activity
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast


// importing views From xml
import kotlinx.android.synthetic.main.activity_play_with_friend_offline.*

import example.aashishadhikari.tictactoeandroid.model.Board
import example.aashishadhikari.tictactoeandroid.model.PlayerState

class PlayWithFriendsOffline : Activity() {

    var board: Board = Board()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_with_friend_offline)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val face = Typeface.createFromAsset(
                assets, "handdrawnshapes.otf"
        )

        information.typeface = face

        updateGame()
    }

    fun onBoxClick(v: View){

        when(v.id) {
            R.id.v0 -> updateBoard(v0,0,0)
            R.id.v1 -> updateBoard(v1,0,1)
            R.id.v2 -> updateBoard(v2,0,2)
            R.id.v3 -> updateBoard(v3,1,0)
            R.id.v4 -> updateBoard(v4,1,1)
            R.id.v5 -> updateBoard(v5,1,2)
            R.id.v6 -> updateBoard(v6,2,0)
            R.id.v7 -> updateBoard(v7,2,1)
            R.id.v8 -> updateBoard(v8,2,2)
        }
    }

    fun updateGame(){
        if (board.isFull){
            information.text = "Game Finished"
            showDialog("Draw. Play Another Match ?")
            return
        }

        if (board.hasWon()){
            information.text = board.currentPlayer.state.name +  " has won"
            showDialog(board.currentPlayer.state.name + " has won the Game. Play Another Match?")
            return
        }

        if(board.currentPlayer.state == PlayerState.ZOMBIE){
            board.currentPlayer.state = PlayerState.BRAIN
        }else{
            board.currentPlayer.state = PlayerState.ZOMBIE
        }

        information.text = board.currentPlayer.state.name + "'s Turn"
    }

    fun updateBoard(v:ImageView, x:Int, y:Int){

        if(board.isFull){
            Toast.makeText(applicationContext,"Completed",Toast.LENGTH_LONG).show()
            return
        }

        if(!board.canMove(x,y)){
            Toast.makeText(applicationContext,"cannot add at $x and $y",Toast.LENGTH_LONG).show()
            return
        }

        board.setValue(x,y)

        if(board.currentPlayer.state == PlayerState.ZOMBIE){
            v.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.zombie_head))
        }else{
            v.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.brain))
        }

        updateGame()
    }

    fun showDialog(message:String){

        val alertDialog = AlertDialog.Builder(this@PlayWithFriendsOffline)

        alertDialog.setPositiveButton("Yes") { dialog, which -> board.clear(); clearBoard(); updateGame() }


        alertDialog.setNegativeButton("No") { dialog, which -> finish() }
        alertDialog.setMessage(message)
        alertDialog.setCancelable(false)
        alertDialog.setTitle(resources.getString(R.string.app_name))
        alertDialog.show()

    }

    fun clearBoard(){
        v0.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.blank))
        v1.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.blank))
        v2.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.blank))
        v3.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.blank))
        v4.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.blank))
        v5.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.blank))
        v6.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.blank))
        v7.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.blank))
        v8.setImageDrawable(ActivityCompat.getDrawable(this, R.drawable.blank))
    }

    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this@PlayWithFriendsOffline)
        alertDialog.setPositiveButton("Yes") { dialog, which -> finish() }
        alertDialog.setNegativeButton("No") { dialog, which -> null }
        alertDialog.setMessage("Are you Sure you want to quit the game?")
        alertDialog.setTitle(resources.getString(R.string.app_name))
        alertDialog.show()
    }
}
