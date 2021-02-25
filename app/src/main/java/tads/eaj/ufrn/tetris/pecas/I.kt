package tads.eaj.ufrn.tetris.pecas

class I(x: Int, y: Int) : Peca(x, y) {

    override var pt1 = Ponto(x, y - 3)
    override var pt2 = Ponto(x, y - 2)
    override var pt3 = Ponto(x, y - 1)
    override var pt4 = Ponto(x, y)

    private var quantidade: Int = 1
    override fun girar() {
        if (quantidade == 1) {
            pt1.x = pt2.x - 1
            pt1.y = pt2.y

            pt3.x = pt2.x + 1
            pt3.y = pt2.y

            pt4.x = pt2.x + 2
            pt4.y = pt2.y
            quantidade++

        } else if (quantidade == 2) {
            pt1.x = pt2.x
            pt1.y = pt2.y - 1

            pt3.x = pt2.x
            pt3.y = pt2.y + 1

            pt4.x = pt2.x
            pt4.y = pt2.y + 2
            quantidade = 1

        }
    }
}