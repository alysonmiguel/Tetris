package tads.eaj.ufrn.tetris.pecas

abstract class Peca(var x:Int, var y:Int){

    open lateinit var pt1:Ponto
    open lateinit var pt2:Ponto
    open lateinit var pt3:Ponto
    open lateinit var pt4:Ponto

    open fun moveDown(){
        pt1.moveDown()
        pt2.moveDown()
        pt3.moveDown()
        pt4.moveDown()
    }

    open fun moveLeft(){
        pt1.moveLeft()
        pt2.moveLeft()
        pt3.moveLeft()
        pt4.moveLeft()
    }

    open fun moveRight(){
        pt1.moveRight()
        pt2.moveRight()
        pt3.moveRight()
        pt4.moveRight()
    }

    open fun gira(){

    }

}