package tads.eaj.ufrn.tetris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import tads.eaj.ufrn.tetris.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.configurarButton.setOnClickListener{
            var intent = Intent(this, ConfigurarActivity::class.java)
            startActivity(intent);
        }

        binding.novoJogoButton.setOnClickListener{
            var intent = Intent(this, TabuleiroActivity::class.java)
            startActivity(intent);
        }

//        var inflater = LayoutInflater.from(this)
//        binding.gridboard.addView(inflater.inflate(R.layout.inflate_button, binding.gridboard, false) as Button)



//        p = findViewById(R.id.gridboard
//                gri
//
//        p.addView(inflater.inflate(R.layout.inflate_image, p, false) as ImageView)
//


    }
}