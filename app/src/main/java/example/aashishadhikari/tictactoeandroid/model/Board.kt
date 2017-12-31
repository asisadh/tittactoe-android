package example.aashishadhikari.tictactoeandroid.model

/**
 * Created by asis on 6/8/17.
 */

class Board{

    //get random player
    var currentPlayer = Player.getALlPlayer()[ getRandom()]

    private var empyt = PlayerState.EMPTY
    var values = arrayOf(
            arrayOf( empyt, empyt, empyt ),
            arrayOf( empyt, empyt, empyt ),
            arrayOf( empyt, empyt, empyt )
    )


    fun setValue(x:Int, y:Int){
        values[x][y] = currentPlayer.state
    }

    var isFull: Boolean = true
         get() {
             for ( row in values) {
                 for (tiles in row){
                    if (tiles == PlayerState.EMPTY){
                        return false
                    }
                 }
             }
             return true
         }

    fun hasWon():Boolean{
        for (column in 0..values.count() - 1) {
            if (values[column][0] == values[column][1] && values[column][0] == values[column][2] && values[column][0] != PlayerState.EMPTY ) {
                if(values[column][0].name == currentPlayer.state.name )
                    return true
            }
            else if (values[0][column] == values[1][column] && values[0][column] == values[2][column] && values[0][column] != PlayerState.EMPTY){
                if(values[0][column].name == currentPlayer.state.name )
                    return true
            }
        }
        if (values[0][0] == values[1][1] && values[0][0] == values[2][2] && values[0][0] != PlayerState.EMPTY ){
            if(values[0][0].name == currentPlayer.state.name )
                return true

        }else if (values[2][0] == values[1][1] && values[2][0] == values[0][2] && values[0][2] != PlayerState.EMPTY){
            if(values[2][0].name == currentPlayer.state.name )
                return true
        }

        return false
    }

    fun clear(){
        for (x in 0..values.count() - 1){
            for (y in 0..values[x].count() - 1) {
                values[x][y] = PlayerState.EMPTY
            }
        }
    }

    fun canMove(x: Int, y: Int): Boolean{
        if(values[x][y] == PlayerState.EMPTY)
            return true
        return false
    }

    fun printBoard(){
        for (x in 0..values.count() - 1){
            for (y in 0..values[x].count() - 1) {
                System.out.println("values $x, $y : " + values[x][y] )
            }
        }
    }
}