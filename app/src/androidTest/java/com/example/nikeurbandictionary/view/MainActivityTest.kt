package com.example.nikeurbandictionary.view

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.nikeurbandictionary.R
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

    @RunWith(AndroidJUnit4::class)
    class MainActivityTest{
        @get:Rule
        val activityRule = ActivityTestRule(MainActivity::class.java)
        val testInput = "lol"

        @Test
        fun doSearchWithDefinedInput(){
            Espresso.onView(
                ViewMatchers.withId(R.id.et_input))
                .perform(ViewActions.typeText(testInput))
            Espresso.onView(
                ViewMatchers.withId(R.id.btn_search))
                .perform(ViewActions.click())
            Thread.sleep(1000)
            Espresso.onView(
                ViewMatchers.withId(R.id.rv_urban))
                .perform(RecyclerViewActions.actionOnItemAtPosition<UrbanDictionaryAdapter.UrbanDictionaryViewHolder>(3, click()))
        }
    }
