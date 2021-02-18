package tads.eaj.ufrn.tetris.pecas

class I (x:Int, y:Int): Peca(x, y) {

    override var pt1 = Ponto(x, y-2)
    override var pt2 = Ponto(x, y +1 )
    override var pt3 = Ponto(x, y - 1)
    override var pt4 = Ponto(x, y)

    override fun gira() {
        super.gira()
    }
}