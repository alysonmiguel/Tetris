package tads.eaj.ufrn.tetris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import tads.eaj.ufrn.tetris.databinding.ActivityMainBinding
import tads.eaj.ufrn.tetris.databinding.ActivityTabuleiroBinding
import tads.eaj.ufrn.tetris.pecas.*
import kotlin.random.Random

class TabuleiroActivity : AppCompatActivity() {
    val LINHA = 26
    val COLUNA = 12
    var running = true
    var speed: Long = 300

    var peca = gerarPeca((0..3).random())

//    var board = Array(LINHA) {
//        Array(COLUNA) { 0 }
//    }

    var boardView = Array(LINHA) {
        arrayOfNulls<ImageView>(COLUNA)
    }

    lateinit var binding: ActivityTabuleiroBinding
    lateinit var viewmodel: TabuleiroViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var settings = getSharedPreferences("prefs", MODE_PRIVATE)
        var dificuldade = settings.getInt("dificuldade", 2)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tabuleiro)
        viewmodel = ViewModelProvider(this).get(TabuleiroViewModel::class.java)


        if (dificuldade == 1) {
            speed = 1600
            Toast.makeText(this, "Facil", Toast.LENGTH_SHORT).show()
        } else if (dificuldade == 2) {
            Toast.makeText(this, "Normal", Toast.LENGTH_SHORT).show()
        } else {
            speed = 20
            Toast.makeText(this, "Dificil", Toast.LENGTH_SHORT).show()
        }

        binding.left.setOnClickListener {
            if (peca.pt1.y == 0 || peca.pt2.y == 0 || peca.pt3.y == 0 || peca.pt4.y == 0) {
                return@setOnClickListener
            }
            peca.moveLeft()
        }
        binding.right.setOnClickListener {
            if (peca.pt1.y >= COLUNA - 1 || peca.pt2.y >= COLUNA - 1 || peca.pt3.y >= COLUNA - 1 || peca.pt4.y >= COLUNA - 1) {
                return@setOnClickListener
            }
            peca.moveRight()
        }

        binding.girar.setOnClickListener {
            if (peca.pt1.y >= COLUNA - 1 || peca.pt2.y >= COLUNA - 1 || peca.pt3.y >= COLUNA - 1 || peca.pt4.y >= COLUNA - 1) {
                return@setOnClickListener
            }
            peca.girar()
        }

        binding.gridboard.rowCount = LINHA
        binding.gridboard.columnCount = COLUNA

        val inflater = LayoutInflater.from(this)

        for (i in 0 until LINHA) {
            for (j in 0 until COLUNA) {
                boardView[i][j] = inflater.inflate(
                    R.layout.inflate_image_view,
                    binding.gridboard,
                    false
                ) as ImageView
                binding.gridboard.addView(boardView[i][j])
            }
        }

        gameRun()
    }

    fun gameRun() {
        Thread {
            while (running) {
                Thread.sleep(speed)
                runOnUiThread {
                    //limpa tela
                    for (i in 0 until LINHA) {
                        for (j in 0 until COLUNA) {
                            if (viewmodel.board[0][j] == 1) {
                                // olha amanha
//                                var intent = Intent(applicationContext, R.layout.activity_game_over::class.java)
//                                startActivity(intent)
                            }
                            if (viewmodel.board[i][j] == 1) {
                                boardView[i][j]!!.setImageResource(R.drawable.white)
                            } else {
                                boardView[i][j]!!.setImageResource(R.drawable.black)
                            }
                        }
                    }
                    //move peça atual
                    if (peca.pt3.x == LINHA - 1 || peca.pt4.x == LINHA - 1 || peca.pt1.x == LINHA - 1 || peca.pt2.x == LINHA - 1) {
                        desenhar()
                        preencheArray()

                        var p = (0..3).random()
                        Toast.makeText(this, "${p}", Toast.LENGTH_SHORT).show()

                        peca = gerarPeca(p)

                    } else if ( viewmodel.board[peca.pt1.x + 1][peca.pt1.y] == 1 ||
                                viewmodel.board[peca.pt2.x + 1][peca.pt2.y] == 1 ||
                                viewmodel.board[peca.pt3.x + 1][peca.pt3.y] == 1 ||
                                viewmodel.board[peca.pt4.x + 1][peca.pt4.y] == 1
                    ) {
                        desenhar()
                        preencheArray()
//                        peca= O(0 ,15)
                        var p = (0..3).random()

                        peca = gerarPeca(p)

                    } else {
                        peca.moveDown()
                    }

                    //print peça
                    try {
                        desenhar()
                    } catch (e: ArrayIndexOutOfBoundsException) {
                        //se a peça passou das bordas eu vou parar o jogo
                        //Toast.makeText(applicationContext, "saiu", Toast.LENGTH_SHORT).show()
                        running = false
                    }
                }
            }
        }.start()
    }

    fun desenhar() {
        boardView[peca.pt1.x][peca.pt1.y]!!.setImageResource(R.drawable.white)
        boardView[peca.pt2.x][peca.pt2.y]!!.setImageResource(R.drawable.white)
        boardView[peca.pt3.x][peca.pt3.y]!!.setImageResource(R.drawable.white)
        boardView[peca.pt4.x][peca.pt4.y]!!.setImageResource(R.drawable.white)
    }

    fun preencheArray() {
        viewmodel.board[peca.pt1.x][peca.pt1.y] = 1
        viewmodel.board[peca.pt2.x][peca.pt2.y] = 1
        viewmodel.board[peca.pt3.x][peca.pt3.y] = 1
        viewmodel.board[peca.pt4.x][peca.pt4.y] = 1
    }

    fun gerarPeca(novaPeca: Int): Peca {

        if (novaPeca == 0) {
            return O(0, 8)
        } else if (novaPeca == 1) {
            return I(0, 8)
        } else if (novaPeca == 2) {
            return L(0, 8)
        } else if (novaPeca == 3) {
            return S(0, 8)
        }
        return L(0, 8)

    }

    override fun onPause() {
        super.onPause()
        running = false
    }

    override fun onRestart() {
        super.onRestart()
        running = true
        gameRun()
    }


//
//        if (novaPeca == 0) {
//            return L(0, 8)
//        } else if (novaPeca == 1) {
//            return L(0, 8)
//        } else if (novaPeca == 2) {
//            return L(0, 8)
//        } else if (novaPeca == 3) {
//            return L(0, 8)
//        }
//        return L(0, 8)
//    }

}
