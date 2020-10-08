package com.pluu.samplecoroutinetest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pluu.samplecoroutinetest.api.GitHubApi

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>(
        factoryProducer = { MainFactory(this) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.p1Event.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}

class MainFactory(
    private val owner: HasDefaultViewModelProviderFactory
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            MainViewModel::class.java -> MainViewModel(GitHubApi.apiService) as T
            else -> owner.defaultViewModelProviderFactory.create(modelClass)
        }
    }
}