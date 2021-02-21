package tads.eaj.ufrn.tetris

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import tads.eaj.ufrn.tetris.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.configurarButton.setOnClickListener {
            var intent = Intent(this, ConfigurarActivity::class.java)
            startActivity(intent);
        }

        binding.novoJogoButton.setOnClickListener {
            var intent = Intent(this, TabuleiroActivity::class.java)
            startActivity(intent);
        }

//        binding.gridboard.setOnClickListener {
//            Toast.makeText(this, "Voltando", Toast.LENGTH_SHORT).show()
//        }

        var param = intent.extras
        var num = param?.getInt("continuar")

        if (num == 1) {
            var inflater = LayoutInflater.from(this)

            var t = inflater.inflate(
                R.layout.inflate_button,
                binding.gridboard,
                false
            ) as Button

            binding.gridboard.addView(t)

            t.setOnClickListener {
                var intent = Intent()
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }


//        var inflater = LayoutInflater.from(this)
//        binding.gridboard.addView(inflater.inflate(R.layout.inflate_button, binding.gridboard, false) as Button)


//        var inflater = LayoutInflater.from(this)
//        bindingMain.gridboard.addView(inflater.inflate(R.layout.inflate_button, binding.gridboard, false) as Button)
//        var intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)

//        p = findViewById(R.id.gridboard
//                gri
//
//        p.addView(inflater.inflate(R.layout.inflate_image, p, false) as ImageView)
//


    }
}