package tads.eaj.ufrn.tetris.pecas

class F  (x:Int, y:Int): Peca(x, y) {

    override var pt1 = Ponto(x , y)
    override var pt2 = Ponto(x, y -1)
    override var pt3 = Ponto(x+1, y - 1)
    override var pt4 = Ponto(x + 2, y -1)

}

