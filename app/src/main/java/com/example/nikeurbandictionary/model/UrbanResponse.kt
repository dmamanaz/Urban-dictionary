package com.example.nikeurbandictionary.model

data class UrbanResponse (var list: ArrayList<Definition>)

data class Definition(var definition: String,
                      var thumbs_up: Number,
                      var thumbs_down: Number)