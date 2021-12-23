package com.example.devicepopulation.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.devicepopulation.data.models.DeviceModel
import com.example.devicepopulation.data.viewstate.Loading
import com.example.devicepopulation.data.viewstate.Success
import com.example.devicepopulation.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Error

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainActivityViewModel by viewModels<MainActivityViewModel>()
    private val devices = mutableListOf<DeviceModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContent {
//            DevicePopulationTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
//                    Greeting("Android")
//                }
//            }
//        }

        initializeData()
    }

    private fun initializeData(){
        mainActivityViewModel.deviceLiveData.observe(this,
        Observer { viewState ->
            when (viewState){
                is Loading -> Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                is Success -> {
                    devices.clear()
                    viewState.data.forEach {
                        devices.add(it)
                    }
                }

                is Error -> {
                    Toast.makeText(this, "Error Fetching Data", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}