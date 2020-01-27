package com.example.nikeurbandictionary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nikeurbandictionary.UrbanDictionaryApplication
import com.example.nikeurbandictionary.model.UrbanResponse
import com.example.nikeurbandictionary.model.buildNetworkRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UrbanRepoViewModel : ViewModel() {

    private var urbanDictionaryResponse:
            MutableLiveData<UrbanResponse> = MutableLiveData()
    private var urbanDictionaryFailure:
            MutableLiveData<String> = MutableLiveData()
    var lastRequestTime : Long = -1


    //users click button, then show loading
    //checks connectivity ->(False) disable loading, show cache
    //check connectivity ->(True) disable loading, show data
    //checks connectivity ->(true) -> (network error) -> disable loading, show error

    fun getUrbanDictionaryData(): LiveData<UrbanResponse> = urbanDictionaryResponse

    fun getUrbanDictionaryFailure(): LiveData<String> = urbanDictionaryFailure

    fun getUrbanDefinition(query: String) {

        if((System.currentTimeMillis() - lastRequestTime) < 10000){
            return
        }


        buildNetworkRequest(UrbanDictionaryApplication.instance).getUrbanDefinition(query)
            .enqueue(object : Callback<UrbanResponse> {
                override fun onFailure(call: Call<UrbanResponse>, t: Throwable) {
                    urbanDictionaryFailure.value = t.message
                }

                override fun onResponse(
                    call: Call<UrbanResponse>,
                    response: Response<UrbanResponse>
                ) {
                    urbanDictionaryResponse.value = response.body()
                }
            })
    }
}