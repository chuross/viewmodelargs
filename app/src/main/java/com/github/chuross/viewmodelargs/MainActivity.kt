package com.github.chuross.viewmodelargs

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = MainViewModelBuilder("fooo")
                .fuga(9999)
                .build(this)

        Toast.makeText(this, "hoge: ${viewModel.hoge}, fuga: ${viewModel.fuga}", Toast.LENGTH_SHORT).show()
    }
}
