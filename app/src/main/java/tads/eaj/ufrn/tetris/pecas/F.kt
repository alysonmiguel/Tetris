package tads.eaj.ufrn.tetris.pecas

class F  (x:Int, y:Int): Peca(x, y) {

    override var pt1 = Ponto(x , y)
    override var pt2 = Ponto(x, y -1)
    override var pt3 = Ponto(x+1, y - 1)
    override var pt4 = Ponto(x + 2, y -1)

    private var quantidade:Int = 1     //  1
    override fun girar() {      //  3
        if (quantidade == 1) {  //  2  4
            pt1.x = pt3.x + 1
            pt1.y = pt3.y + 1

            pt2.x = pt3.x
            pt2.y = pt3.y + 1

            pt4.x = pt3.x
            pt4.y = pt3.y - 1
            quantidade++

        } else if (quantidade == 2) {
            pt1.x = pt3.x + 1
            pt1.y = pt3.y - 1

            pt2.x = pt3.x + 1
            pt2.y = pt3.y

            pt4.x = pt3.x - 1
            pt4.y = pt3.y
            quantidade++

        } else if (quantidade == 3) {
            pt1.x = pt3.x - 1
            pt1.y = pt3.y - 1

            pt2.x = pt3.x
            pt2.y = pt3.y - 1

            pt4.x = pt3.x
            pt4.y = pt3.y + 1

            quantidade++
        } else {
            pt1.x = pt3.x - 1
            pt1.y = pt3.y + 1

            pt2.x = pt3.x - 1
            pt2.y = pt3.y

            pt4.x = pt3.x + 1
            pt4.y = pt3.y
            quantidade = 1
        }
    }

}

