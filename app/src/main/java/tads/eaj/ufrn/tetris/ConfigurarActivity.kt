package tads.eaj.ufrn.tetris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import tads.eaj.ufrn.tetris.databinding.ActivityConfigurarBinding


class ConfigurarActivity : AppCompatActivity() {

    lateinit var binding : ActivityConfigurarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_configurar)

        var settings = getSharedPreferences("prefs", MODE_PRIVATE)
        var valor = settings.getInt("dificuldade", 2);

        if( valor == 1){
            binding.radioGroup.check(R.id.facilRadioButton)
        }else if ( valor == 2){
            binding.radioGroup.check(R.id.normalRadioButton)
        }else{
            binding.radioGroup.check(R.id.dificilRadioButton)
        }



        binding.salvarButton.setOnClickListener {
            val settings = getSharedPreferences("prefs", MODE_PRIVATE)
            val editor = settings.edit()

            if(binding.facilRadioButton.isChecked){
                editor.putInt("dificuldade", 1)
            }else if(binding.normalRadioButton.isChecked){
                editor.putInt("dificuldade", 2)
            }else{
                editor.putInt("dificuldade", 3)
            }
            editor.apply()

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }


    }



}