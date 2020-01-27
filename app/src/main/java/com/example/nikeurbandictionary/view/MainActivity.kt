package com.example.nikeurbandictionary.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikeurbandictionary.R
import com.example.nikeurbandictionary.model.UrbanResponse
import com.example.nikeurbandictionary.viewmodel.UrbanRepoViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var inputEditText: EditText
    lateinit var searchButton: Button
    lateinit var urbanDictionaryAdapter: UrbanDictionaryAdapter
    lateinit var rvUrban: RecyclerView
    val viewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return UrbanRepoViewModel() as T
        }
    }

    val customViewModel by lazy {
        ViewModelProvider(
            this, viewModelFactory
        ).get(UrbanRepoViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpRecyclerView()

        inputEditText = findViewById(R.id.et_input)
        searchButton = findViewById(R.id.btn_search)


        customViewModel.getUrbanDictionaryFailure().observe(this, object : Observer<String> {
            override fun onChanged(t: String?) {
                Toast.makeText(this@MainActivity, t, Toast.LENGTH_LONG).show()
            }
        })
        customViewModel.getUrbanDictionaryData().observe(this, object : Observer<UrbanResponse> {
            override fun onChanged(t: UrbanResponse?) {
                urbanDictionaryAdapter = t?.let { UrbanDictionaryAdapter(it) }!!
                rvUrban.adapter = urbanDictionaryAdapter

            }
        })

        searchButton.setOnClickListener(this)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.sort_down -> {
                    searchDownList()
                    true
                }
                R.id.sort_up -> {
                    searchUpList()
                    true
                }
                else ->
                    false
            }
        }


    }

    private fun searchUpList() {
        customViewModel.sorting(true)
    }

    private fun searchDownList() {
        customViewModel.sorting(false)
    }

    override fun onClick(view: View?) {
        customViewModel.getUrbanDefinition(
            inputEditText.text.toString()
        )

    }

    private fun setUpRecyclerView() {
        rvUrban = findViewById(R.id.rv_urban)
        rvUrban.layoutManager = LinearLayoutManager(this)
    }

}
