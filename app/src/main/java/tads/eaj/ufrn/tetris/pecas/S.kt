package tads.eaj.ufrn.tetris.pecas

class S (x:Int, y:Int): Peca(x, y) {

    override var pt1 = Ponto(x, y)
    override var pt2 = Ponto(x, y + 1)
    override var pt3 = Ponto(x+1, y )
    override var pt4 = Ponto(x+1, y- 1)

    private var quantidade:Int = 1
    override fun girar() {
        if (quantidade == 1){
            pt1.x = pt3.x
            pt1.y = pt3.y-1

            pt2.x = pt3.x - 1
            pt2.y = pt3.y - 1

            pt4.x = pt3.x + 1
            pt4.y = pt3.y
            quantidade++

        }else if (quantidade == 2){
            pt1.x = pt3.x - 1
            pt1.y = pt3.y

            pt2.x = pt3.x - 1
            pt2.y = pt3.y + 1

            pt4.x = pt3.x
            pt4.y = pt3.y - 1
            quantidade = 1
        }
    }

}