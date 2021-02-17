package tads.eaj.ufrn.tetris.pecas

open class Ponto(var x:Int, var y:Int){

    open fun moveDown(){
        x++
    }

    open fun moveLeft(){
        y--
    }
    fun moveRight(){
        y++
    }

}
