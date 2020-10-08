package com.pluu.samplecoroutinetest

import com.nhaarman.mockitokotlin2.*
import com.pluu.samplecoroutinetest.api.ApiService
import com.pluu.samplecoroutinetest.utils.ViewModelTestRule
import com.pluu.samplecoroutinetest.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {
    @get:Rule
    var viewModelTestRule = ViewModelTestRule()

    private lateinit var viewModel: MainViewModel

    private val apiService: ApiService = mock()

    @Before
    fun setUp() {
        viewModel = MainViewModel(apiService)
    }

    @Test
    fun successError() = runBlockingTest {
        doReturn(Throwable("Error"))
            .`when`(apiService)
            .contributors(any(), any())

        viewModel.ping()

        val value = viewModel.errorEvent.getOrAwaitValue()
        assertEquals(value, "error")
    }

    @Test
    fun failError() = runBlockingTest {
        // Issues : https://github.com/mockito/mockito/issues/1166?fbclid=IwAR0AAo-Ze5uX2JEZqc2a0N2k44mBxNP7J1jUnhDwGDoq8dA5UEnwdiUO0qA
        whenever(apiService.contributors(any(), any()))
            .doThrow(Throwable("Error"))

        viewModel.ping()

        val value = viewModel.errorEvent.getOrAwaitValue()
        assertEquals(value, "error")
    }
}