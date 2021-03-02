package tads.eaj.ufrn.tetris

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
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
    val LINHA = 22
    val COLUNA = 12
    var running = true
    var speed: Long = 300
    val REQUEST_CODE = 1

    var peca = gerarPeca()

    var boardView = Array(LINHA) {
        arrayOfNulls<ImageView>(COLUNA)
    }

//    var proxima = Array(4) {
//        arrayOfNulls<ImageView>(4)
//    }

    lateinit var binding: ActivityTabuleiroBinding
    lateinit var viewmodel: TabuleiroViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tabuleiro)
        viewmodel = ViewModelProvider(this).get(TabuleiroViewModel::class.java)

        vericarDificuldade()

        binding.left.setOnClickListener {
            if (peca.pt1.y == 1 || peca.pt2.y == 1 || peca.pt3.y == 1 || peca.pt4.y == 1) {
                return@setOnClickListener
            }
            peca.moveLeft()
        }

        binding.right.setOnClickListener {
            if (peca.pt1.y >= COLUNA - 2 ||
                peca.pt2.y >= COLUNA - 2 ||
                peca.pt3.y >= COLUNA - 2 ||
                peca.pt4.y >= COLUNA - 2
            ) {
                return@setOnClickListener
            }
            peca.moveRight()
        }

        binding.girar.setOnClickListener {
            if (peca.pt1.y >= COLUNA - 2 ||
                peca.pt2.y >= COLUNA - 2 ||
                peca.pt3.y >= COLUNA - 2 ||
                peca.pt4.y >= COLUNA - 2 ||
                peca.pt1.y == 1 ||
                peca.pt2.y == 1 ||
                peca.pt3.y == 1 ||
                peca.pt4.y == 1
            ) {
                return@setOnClickListener
            }
            peca.girar()
        }
        binding.descer.setOnClickListener {
            peca.moveDown()
        }

        // o onPause é chamando automaticamente
        binding.pause.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            var param = Bundle()
            param.putInt("continuar", 1)
            intent.putExtras(param)
            startActivityForResult(intent, REQUEST_CODE)

        }

        binding.descer.setOnClickListener {

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
                if (i == 0 || i == LINHA - 1 || j == 0 || j == COLUNA - 1) {
                    boardView[i][j]!!.setImageResource(R.drawable.gray)
                }
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
                            if (viewmodel.board[i][j] == 1) {
                                boardView[i][j]!!.setImageResource(R.drawable.white)
                            } else if (i == 0 || i == LINHA - 1 || j == 0 || j == COLUNA - 1) {
                                boardView[i][j]!!.setImageResource(R.drawable.gray)
                            } else {
                                boardView[i][j]!!.setImageResource(R.drawable.black)
                            }
                        }
                    }
                    if (peca.pt3.x == LINHA - 2 || peca.pt4.x == LINHA - 2 || peca.pt1.x == LINHA - 2 || peca.pt2.x == LINHA - 2) {
                        desenhar()
                        preencheArray()
                        peca = gerarPeca()

                    } else if (viewmodel.board[peca.pt1.x + 1][peca.pt1.y] == 1 ||
                        viewmodel.board[peca.pt2.x + 1][peca.pt2.y] == 1 ||
                        viewmodel.board[peca.pt3.x + 1][peca.pt3.y] == 1 ||
                        viewmodel.board[peca.pt4.x + 1][peca.pt4.y] == 1
                    ) {
                        desenhar()
                        preencheArray()
                        peca = gerarPeca()

                    } else {
                        peca.moveDown()
                    }
                    try {
                        desenhar()
                    } catch (e: ArrayIndexOutOfBoundsException) {
                        running = false
                    }
                }
            }
        }.start()
    }

    fun vericarDificuldade() {

        var settings = getSharedPreferences("prefs", MODE_PRIVATE)
        var dificuldade = settings.getInt("dificuldade", 2)

        if (dificuldade == 1) {
            speed = 500
            Toast.makeText(this, "Facil", Toast.LENGTH_SHORT).show()
        } else if (dificuldade == 2) {
            speed = 400
            Toast.makeText(this, "Normal", Toast.LENGTH_SHORT).show()
        } else {
            speed = 100
            Toast.makeText(this, "Dificil", Toast.LENGTH_SHORT).show()
        }

    }

    fun pontuar() {
        for (i in 0 until LINHA) {
            if (viewmodel.board[i].sum() == COLUNA - 2) {
                Toast.makeText(this, "Pontuar", Toast.LENGTH_SHORT).show()
                viewmodel.pontos += 50
                binding.pontos.text = (viewmodel.pontos).toString()

                for (k in i downTo 1) {
                    viewmodel.board[k] = viewmodel.board[k - 1]
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        Toast.makeText(this, "Continuando", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
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

        pontuar()
        if (viewmodel.board[0].contains(1)){
            var intent = Intent(this, GameOverActivity::class.java)
            var pontuacao = viewmodel.pontos.toString()
            var param = Bundle()
            param.putString("pontos", pontuacao)
            intent.putExtras(param)
            startActivity(intent)
            running = false
            finish()
        }

    }

    fun gerarPeca(): Peca {
        when ((0..3).random()) {
            0 -> return F(0, 8)
            1 -> return L(0, 8)
            2 -> return N(0, 8)
            3 -> return S(0, 8)
        }
        return S(0, 8)
    }

    override fun onStart() {
        Log.i("AULA", "onStart() invocado.")
        super.onStart()
    }

    override fun onResume() {
        Log.i("AULA", "onResume() invocado.")
        super.onResume()
    }

    override fun onPause() {
        Log.i("AULA", "onPause() invocado.")
        super.onPause()
        running = false

    }

}




