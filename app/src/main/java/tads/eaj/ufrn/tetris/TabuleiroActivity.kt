package tads.eaj.ufrn.tetris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import tads.eaj.ufrn.tetris.databinding.ActivityMainBinding
import tads.eaj.ufrn.tetris.databinding.ActivityTabuleiroBinding
import tads.eaj.ufrn.tetris.pecas.O

class TabuleiroActivity : AppCompatActivity() {
    val LINHA = 40
    val COLUNA = 22
    var running = true
    var speed: Long = 300

    var o = O(0, 15)

    var board = Array(LINHA) {
        Array(COLUNA) { 0 }
    }

    var boardView = Array(LINHA) {
        arrayOfNulls<ImageView>(COLUNA)
    }

    lateinit var binding: ActivityTabuleiroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var settings = getSharedPreferences("prefs", MODE_PRIVATE)
        var dificuldade = settings.getInt("dificuldade", 2)

        if (dificuldade == 1) {
            speed = 400
            Toast.makeText(this, "Facil", Toast.LENGTH_SHORT).show()
        } else if (dificuldade == 2) {
            Toast.makeText(this, "Normal", Toast.LENGTH_SHORT).show()
        } else {
            speed = 150
            Toast.makeText(this, "Dificil", Toast.LENGTH_SHORT).show()
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tabuleiro)

        binding.left.setOnClickListener {
            if (o.pt1.y == 0) {
                return@setOnClickListener
            }
            o.moveLeft()
        }
        binding.right.setOnClickListener {
            if (o.pt2.y >= COLUNA - 1) {
                return@setOnClickListener
            }
            o.moveRight()
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
                            if (board[i][j] == 1) {
                                boardView[i][j]!!.setImageResource(R.drawable.white)
                            } else {
                                boardView[i][j]!!.setImageResource(R.drawable.black)
                            }
                        }
                    }
                    //move peça atual
                    if (o.pt3.x == LINHA - 1 && o.pt4.x == LINHA - 1 && o.pt1.x == LINHA - 2 && o.pt2.x == LINHA - 2) {
                        boardView[o.pt1.x][o.pt1.y]!!.setImageResource(R.drawable.white)
                        boardView[o.pt2.x][o.pt2.y]!!.setImageResource(R.drawable.white)
                        boardView[o.pt3.x][o.pt3.y]!!.setImageResource(R.drawable.white)
                        boardView[o.pt4.x][o.pt4.y]!!.setImageResource(R.drawable.white)

                        board[o.pt1.x][o.pt1.y] = 1
                        board[o.pt2.x][o.pt2.y] = 1
                        board[o.pt3.x][o.pt3.y] = 1
                        board[o.pt4.x][o.pt4.y] = 1

                        o = O(0 ,15)
//                        novapeca()
                    } else {
                        o.moveDown()
                    }

                    //print peça
                    try {
                        boardView[o.pt1.x][o.pt1.y]!!.setImageResource(R.drawable.white)
                        boardView[o.pt2.x][o.pt2.y]!!.setImageResource(R.drawable.white)
                        boardView[o.pt3.x][o.pt3.y]!!.setImageResource(R.drawable.white)
                        boardView[o.pt4.x][o.pt4.y]!!.setImageResource(R.drawable.white)

                    } catch (e: ArrayIndexOutOfBoundsException) {
                        //se a peça passou das bordas eu vou parar o jogo
                        Toast.makeText(applicationContext, "saiu", Toast.LENGTH_SHORT).show()
                        running = false
                    }
                }
            }
        }.start()
    }
}
