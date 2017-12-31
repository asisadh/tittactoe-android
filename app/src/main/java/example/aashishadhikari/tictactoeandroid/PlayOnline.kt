package example.aashishadhikari.tictactoeandroid

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Typeface
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONArray
import org.json.JSONObject
import android.os.Handler
import android.os.Message
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.widget.ImageView
import android.widget.Toast
import example.aashishadhikari.tictactoeandroid.model.Board
import example.aashishadhikari.tictactoeandroid.model.Player
import example.aashishadhikari.tictactoeandroid.model.PlayerState

import kotlinx.android.synthetic.main.activity_play_online.*
import kotlinx.android.synthetic.main.layout_loading.*
import org.json.JSONException


/**
 * Created by asis on 6/10/17.
 */
class PlayOnline : Activity() {

    //private val socket: Socket = IO.socket("http://10.0.3.2:8080")
    //private val socket: Socket = IO.socket("http://192.168.100.177:8080")
    private val socket: Socket = IO.socket("http://45.77.37.210:8080")
    //private val socket: Socket = IO.socket("https://onlinetictactoe.herokuapp.com/index.js")

    private val CONNECTION_SETUP = 1
    private val PLAYER_MOVED = 2
    private val MESSAGE_KEY = 3
    private val SETUP_KEY = "KEY"

    private var playerType: String? = null
    private var gameID:String? = null
    private var opponentID:String? = null
    private var turn:Boolean = false

    private var board = Board()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_online)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val face = Typeface.createFromAsset(
                assets, "handdrawnshapes.otf"
        )
        information.typeface = face
        finding_match.typeface = face

        setupConnection()
        socketCommunication()
    }

    fun onBoxClick(v: View){
        if(!turn){
            Toast.makeText(applicationContext,"Waiting for opponent's move!",Toast.LENGTH_LONG).show()
            return
        }
        when(v.id) {
            R.id.v0 -> {
                updateBoard(v0,0,0)
                updateServer(0,0)
            }
            R.id.v1 ->{
                updateBoard(v1,0,1)
                updateServer(0,1)
            }
            R.id.v2 -> {
                updateBoard(v2,0,2)
                updateServer(0,2)
            }
            R.id.v3 -> {
                updateBoard(v3,1,0)
                updateServer(1,0)
            }
            R.id.v4 -> {
                updateBoard(v4,1,1)
                updateServer(1,1)
            }
            R.id.v5 -> {
                updateBoard(v5,1,2)
                updateServer(1,2)
            }
            R.id.v6 -> {
                updateBoard(v6,2,0)
                updateServer(2,0)
            }
            R.id.v7 -> {
                updateBoard(v7,2,1)
                updateServer(2,1)
            }
            R.id.v8 -> {
                updateBoard(v8,2,2)
                updateServer(2,2)
            }
        }
        turn = false
        information.text = "Waiting for opponent's move!"
    }

    private fun setupInitialDisplay(){
        if(turn){
            information.text = "Make your move!"
        }else{
            information.text = "Waiting for opponent's move!"
        }
        miniInformation.text = "You're " + playerType

        board.currentPlayer = Player(PlayerState.BRAIN)
    }

    private fun setupDisplayMessage(){

        this.turn = !turn

        if(turn){
            information.text = "Make your move!"
        }else{
            information.text = "Waiting for opponent's move!"
        }
    }

    private fun socketCommunication(){

        //System.out.println(" IS this being called")

//        socket.on("findAMatch", {
//            var player = it[0] as JSONObject
//            var gameID = it[1] as JSONObject
//
//            System.out.println("Player " + player.toString())
//            System.out.println("gameID " + gameID.toString())
//
//            var msg:Message = handler.obtainMessage()
//            val b = Bundle()
//            b.putInt(SETUP_KEY, CONNECTION_SETUP)
//            msg.data = b
//            handler.sendMessage(msg)
//        })
    }

    private fun setupConnection(){
        System.out.println("Setup connection called")

        try {
            socket.connect()

            socket.on(Socket.EVENT_CONNECT, { System.out.println("SocketIO connected") })

                    .emit("findAMatch")

                    .on("opponent", {
                        var data = it[0] as JSONObject
                        var type = data.getString("type")
                        var turn = data.getString("turn")
                        var opponent_id = data.getString("opponentPlayerID")
                        var game_id = data.getString("game_id")

                        this.playerType = type
                        this.turn = turn.toBoolean()
                        this.gameID = game_id
                        this.opponentID = opponent_id

                        var msg:Message = handler.obtainMessage()
                        val b = Bundle()
                        b.putInt(SETUP_KEY, CONNECTION_SETUP)
                        msg.data = b
                        handler.sendMessage(msg)

                    })
                    .on("playerMoved", {

                        System.out.println("Is this called ... ")

                        var data : JSONObject = it[0] as JSONObject
                        var x: Int = data.getString("x").toInt()
                        var y: Int = data.getString("y").toInt()

                        var msg:Message = handler.obtainMessage()
                        val b = Bundle()
                        b.putInt(SETUP_KEY, PLAYER_MOVED)
                        b.putInt("x",x)
                        b.putInt("y",y)
                        msg.data = b
                        handler.sendMessage(msg)
                    })
                    .on("gameMessage", {
                        var data : JSONObject = it[0] as JSONObject
                        var gameMessage = data.getString("message")

                        var msg = handler.obtainMessage()
                        val b = Bundle()
                        b.putInt(SETUP_KEY, MESSAGE_KEY)
                        b.putString("msg",gameMessage)
                        msg.data = b
                        handler.sendMessage(msg)

                    })

        }catch (e: Exception){
          e.printStackTrace()
        }
    }

    private fun updateGame(){

        board.printBoard()

        if (board.isFull){
            information.text = "Game Draw!!!"
            //pushMessage("Look like no more Move to be Played. Play a New game?")
            showDialog("Look like no more Move to be Played. Play a New game?")
            return
        }

        if (board.hasWon()){
            System.out.println("Won The Game")
            if(playerType!!.toLowerCase().equals(board.currentPlayer.state.name.toLowerCase())){
                information.text = "Congratulation! you have won the Game."
                showDialog("Congratulation! you have won the Game. Play with Other?")
            }else{
                information.text = "Opponent has won the game."
                showDialog("Opponent has won the game. Play with Other?")
            }
            //pushMessage("")
            return
        }

        if(board.currentPlayer.state == PlayerState.ZOMBIE){
            board.currentPlayer.state = PlayerState.BRAIN
        }else{
            board.currentPlayer.state = PlayerState.ZOMBIE
        }

    }

    private fun updateBoard(v:ImageView, x: Int, y: Int ){

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

    private fun updateServer(x:Int, y: Int){
        var data: JSONObject = JSONObject()
        try{
            data.put("gameID",gameID)
            data.put("opponentID",opponentID)
            data.put("x",x)
            data.put("y",y)
            socket.emit("playerMoved",data)
        }catch (e: JSONException){}
    }

    private fun pushMessage(msg:String){
        var data: JSONObject = JSONObject()
        try{
            data.put("message",msg)
            socket.emit("gameMessage",data)
        }catch (e: JSONException){}
    }

    val handler: Handler = object : Handler() {

        override  fun handleMessage(msg: Message) {

            val b: Bundle = msg.data
            val value = b.get(SETUP_KEY)

            if (value === CONNECTION_SETUP ) {
                setupInitialDisplay()
                layout_loading.visibility = View.GONE
                content.visibility = View.VISIBLE

            }

            if (value === PLAYER_MOVED ) {

                val x = b.getInt("x")
                val y = b.getInt("y")

                updateBoard(getImageView(x,y),x,y)

                setupDisplayMessage()
            }

            if (value === MESSAGE_KEY ) {
                information.text = b.getString("msg")
                showDialog("" + msg  + ".Find a New opponent?")
            }

            super.handleMessage(msg)
        }
    }

    fun showDialog(message:String){

        val alertDialog = AlertDialog.Builder(this@PlayOnline)

        alertDialog.setPositiveButton("Yes") { dialog, which -> restartGame(); }

        alertDialog.setNegativeButton("No") { dialog, which -> finish(); }

        alertDialog.setMessage(message)
        alertDialog.setCancelable(false)
        alertDialog.setTitle(resources.getString(R.string.app_name))
        alertDialog.show()

    }

    private fun restartGame(){
        var i = intent
        finish()
        startActivity(i)
    }

    override fun finish() {
        socket.close()
        super.finish()
    }

    private fun getImageView(x:Int, y: Int): ImageView{
        if(x == 0 && y == 0){
            return v0
        }
        else if(x == 0 && y == 1){
            return v1
        }
        else if(x == 0 && y == 2){
            return v2
        }
        else if(x == 1 && y == 0){
            return v3
        }
        else if(x == 1 && y == 1){
            return v4
        }
        else if(x == 1 && y == 2){
            return v5
        }
        else if(x == 2 && y == 0){
            return v6
        }
        else if(x == 2 && y == 1){
            return v7
        }
        else{
            return v8
        }

    }
}