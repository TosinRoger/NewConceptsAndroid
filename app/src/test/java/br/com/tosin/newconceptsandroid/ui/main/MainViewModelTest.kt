package br.com.tosin.newconceptsandroid.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

class MainViewModelTest {

    @Mock
    private lateinit var mMainRepository: MainContract.IntMainRepository

    @Mock
    private lateinit var mMainView: MainContract.MainView

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    private lateinit var mMainVM: MainContract.MainUserActionListener

    @Before
    fun setupTests() {
        mMainVM = MainViewModel()
    }

    @Test
    fun getList() {

    }
}