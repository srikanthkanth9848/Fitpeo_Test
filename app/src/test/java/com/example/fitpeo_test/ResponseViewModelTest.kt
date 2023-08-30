package com.example.fitpeo_test

import androidx.lifecycle.Observer
import com.example.fitpeo_test.network.ApiInterface
import com.example.fitpeo_test.viewmodel.ResponseViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
@HiltAndroidTest
class ResponseViewModelTest {
    private lateinit var viewModel: ResponseViewModel

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var apiInterfaces: ApiInterface

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Mock
    private lateinit var apiUsersObserver: Observer<List<ResponseDataItem>>
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()


    @Before
    fun doSomeSetup() {
        // do something if required
        Dispatchers.setMain(dispatcher)
        viewModel = ResponseViewModel(apiInterfaces)
        viewModel.getData.value
        assert(true)
    }

    @Test
    fun ReturnSuccess() {

    }

    @Test
    fun response_error() {
        val message = "Error from api"
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}