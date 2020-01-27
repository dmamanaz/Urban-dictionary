package com.example.nikeurbandictionary.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4:: class)
class UrbanRepoViewModelTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    lateinit var classUnderTest: UrbanRepoViewModel
    var observableFailureCase: MutableLiveData<String> = MutableLiveData()
    val failureMessage = "No empty queries available, please try again"
    @Before
    fun setup(){
        classUnderTest = UrbanRepoViewModel()
        observableFailureCase.value = failureMessage
    }

    @Test
    fun validateNoEmptyInputString(){
        assertNotNull(classUnderTest)
        classUnderTest.getUrbanDefinition("")
        assertEquals(observableFailureCase.value, classUnderTest.getUrbanDictionaryFailure().value)
    }

}