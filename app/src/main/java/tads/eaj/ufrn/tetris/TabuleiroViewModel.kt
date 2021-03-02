package tads.eaj.ufrn.tetris

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import tads.eaj.ufrn.tetris.pecas.*

class TabuleiroViewModel : ViewModel() {

    val LINHA = 22
    val COLUNA = 12

    var pontos = 0

    var board = Array(LINHA) {
        Array(COLUNA) { 0 }
    }

}