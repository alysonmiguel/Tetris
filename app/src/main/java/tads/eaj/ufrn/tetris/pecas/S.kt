package tads.eaj.ufrn.tetris.pecas

class S (x:Int, y:Int): Peca(x, y) {

    override var pt1 = Ponto(x+1, y-2)
    override var pt2 = Ponto(x, y)
    override var pt3 = Ponto(x, y -1)
    override var pt4 = Ponto(x+1, y-1)

    override fun moveDown() {
        pt1.moveDown()
        pt2.moveDown()
        pt3.moveDown()
        pt4.moveDown()
    }

    override fun moveLeft() {
        pt1.moveLeft()
        pt2.moveLeft()
        pt3.moveLeft()
        pt4.moveLeft()
    }
}