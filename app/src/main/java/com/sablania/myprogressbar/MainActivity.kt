package com.sablania.myprogressbar

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sablania.myprogressbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnAnimate.setOnClickListener {
                val str = etProgressValue.text.toString()
                if (str.isEmpty()) {
                    Toast.makeText(
                        this@MainActivity,
                        R.string.please_enter_value_to_animate,
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                if (str.toInt() > 100) {
                    Toast.makeText(
                        this@MainActivity,
                        R.string.enter_value_from_0_to_100_to_animate,
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                progressBar.animateProgress(str.toInt())
            }
        }

    }
}
