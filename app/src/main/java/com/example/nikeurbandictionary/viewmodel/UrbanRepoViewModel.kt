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

    fun getUrbanDictionaryData(): LiveData<UrbanResponse> = urbanDictionaryResponse

    fun getUrbanDictionaryFailure(): LiveData<String> = urbanDictionaryFailure

    fun getUrbanDefinition(query: String) {

        if(query.isEmpty()){
            urbanDictionaryFailure.value = "No empty queries available, please try again"
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
                    if(response.isSuccessful && response.body() != null) {
                        urbanDictionaryResponse.value = response.body().let {
                            sortUpData(it!!)
                        }
                    }
                }
            })
    }

    fun sorting(sortingUp: Boolean){
        var data = urbanDictionaryResponse.let {
            it.value!!
        }
        when(sortingUp){
            true-> data = sortUpData(data)
            false-> data = sortDownData(data)
        }
        urbanDictionaryResponse.value = data
    }

    private fun sortUpData(data: UrbanResponse): UrbanResponse{
        data.list.sortWith(kotlin.Comparator{
                leftTUp, rightTUp ->
            when {
                leftTUp.thumbs_up.toInt() > rightTUp.thumbs_up.toInt() -> -1
                leftTUp.thumbs_up.toInt() < rightTUp.thumbs_up.toInt() -> 1
                else -> 0
            }
        })
        return data
    }
    private fun sortDownData(data: UrbanResponse): UrbanResponse{
        data.list.sortWith(kotlin.Comparator{
                leftTDown, rightTDow  ->
            when {
                leftTDown.thumbs_down.toInt() > rightTDow.thumbs_down.toInt() -> -1
                leftTDown.thumbs_down.toInt() < rightTDow.thumbs_down.toInt() -> 1
                else -> 0
            }
        })
        return data
    }
}