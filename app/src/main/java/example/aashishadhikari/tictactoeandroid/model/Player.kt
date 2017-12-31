package example.aashishadhikari.tictactoeandroid.model

import java.util.*

/**
 * Created by asis on 6/8/17.
 */


enum class PlayerState {
    EMPTY, BRAIN, ZOMBIE
}

fun getRandom(): Int{
    if ( Random().nextInt() % 2 == 0){
        return 0
    }
    return 1
}

class Player(state:PlayerState){

    var state: PlayerState = state

    companion object {
        fun getALlPlayer():ArrayList<Player>{
            var array = ArrayList<Player>()
            array.add(Player(PlayerState.ZOMBIE))
            array.add(Player(PlayerState.BRAIN))

            return array
        }
    }
}