package tads.eaj.ufrn.tetris

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import tads.eaj.ufrn.tetris.databinding.ActivityGameOverBinding

class GameOverActivity : AppCompatActivity() {

    lateinit var viewModel: TabuleiroViewModel
    lateinit var binding: ActivityGameOverBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_over)

        binding.novoJogoButton.setOnClickListener {
            var intent = Intent(this, TabuleiroActivity::class.java)
            startActivity(intent);
        }

        var param = intent.extras
        var pontos = param?.getString("pontos")

        binding.pontosTextView.text =  "${binding.pontosTextView.text} : $pontos"

        var settings = getSharedPreferences("prefs", MODE_PRIVATE)
        var pontuacao = settings.getString("pontuacao", "0")

        binding.recordTextView.text = "${binding.recordTextView.text} : $pontuacao"

        if (pontos.toString().toInt() > pontuacao.toString().toInt()){
            var editor = settings.edit()
            editor.putString("pontuacao", pontos)
            editor.apply()

            var inflater = LayoutInflater.from(this)

            var t = inflater.inflate(
                R.layout.inflate_textview,
                binding.gridboard,
                false
            ) as TextView
            binding.gridboard.addView(t)
        }
    }

}