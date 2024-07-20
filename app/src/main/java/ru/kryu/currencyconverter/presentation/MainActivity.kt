package ru.kryu.currencyconverter.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.kryu.currencyconverter.R
import ru.kryu.currencyconverter.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            viewModel.screenState.collect { state ->
                when (state) {
                    ConvertState.Loading -> {
                        binding.button.isEnabled = false
                    }

                    is ConvertState.Content -> {
                        binding.button.isEnabled = true
                        binding.textViewResult.text = state.result.toString()
                    }

                    is ConvertState.Error -> {
                        Toast.makeText(this@MainActivity, state.message, Toast.LENGTH_SHORT).show()
                        binding.textViewResult.text = getString(R.string.zero)
                    }
                }
            }
        }

        binding.button.setOnClickListener {
            val userInput = binding.editText.text.toString().toDoubleOrNull()
            if (userInput != null) {
                viewModel.convert(
                    to = binding.spinnerTo.selectedItem.toString(),
                    from = binding.spinnerFrom.selectedItem.toString(),
                    amount = userInput
                )
            }
        }
    }
}