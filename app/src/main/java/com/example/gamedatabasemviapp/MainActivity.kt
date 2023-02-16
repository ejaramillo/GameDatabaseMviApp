package com.example.gamedatabasemviapp

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamedatabasemviapp.data.datasource.RemoteDataSource
import com.example.gamedatabasemviapp.data.repository.NetworkRepository
import com.example.gamedatabasemviapp.databinding.ActivityMainBinding
import com.example.gamedatabasemviapp.framework.network.NetworkRemoteDataSource
import com.example.gamedatabasemviapp.presentation.user.UserProcessor
import com.example.gamedatabasemviapp.presentation.user.UserUiState
import com.example.gamedatabasemviapp.presentation.user.UserViewModel
import com.example.gamedatabasemviapp.presentation.user.UserViewModelFactory
import com.example.gamedatabasemviapp.ui.listuser.GamesAdapter
import com.example.gamedatabasemviapp.ui.listuser.UserIntentHandler
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
internal class MainActivity : AppCompatActivity() {

    private val gamesAdapter: GamesAdapter by lazy { GamesAdapter() }

    val remoteDataSource: RemoteDataSource by lazy{
        NetworkRemoteDataSource("a51ef38e6d754bdc919a4104b0387fa3")
    }

    val repository: NetworkRepository by lazy {
        NetworkRepository(remoteDataSource)
    }

    private val processor: UserProcessor by lazy {
        UserProcessor(repository)
    }

    private lateinit var userViewModel: UserViewModel

    private lateinit var binding: ActivityMainBinding
    private var disposable: Disposable? = null
    private val userIntentHandler = UserIntentHandler().apply {
        this.coroutineScope = lifecycleScope
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initViewModel()
        userContent()
        observerTextInput()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    private fun initView() {
        binding.rvGame.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            adapter = gamesAdapter
        }
    }

    private fun initViewModel() {
        val vm by viewModels<UserViewModel> { UserViewModelFactory() }
        userViewModel = vm
        userViewModel.processUserIntentsAndObserveUiStates(userIntentHandler.userIntents())
    }

    private fun observerTextInput() {
        disposable =
            RxTextView.textChanges(binding.edtSearchGame).throttleLast(2000, TimeUnit.MILLISECONDS)
                .subscribe {
                    if(!it.toString().isEmpty())
                        userIntentHandler.getListGameUIntent(it.toString())
                }
    }

    private fun userContent() {
        lifecycleScope.launch {
            userViewModel.uiState.collect { userUiState ->
                when (userUiState) {
                    is UserUiState.DefaultUiState -> {
                        binding.run {
                            tvDefaultInfo.text = getString(R.string.let_s_search_your_favorite_game)
                            showAndHideView(tvDefaultInfo, rvGame, progressBar)
                        }
                    }

                    is UserUiState.LoadingUiState -> {
                        binding.run {
                            showAndHideView(progressBar, tvDefaultInfo, rvGame)
                        }
                    }

                    is UserUiState.EmptyUiState -> {
                        binding.run {
                            tvDefaultInfo.text = getString(R.string.game_not_found)
                            showAndHideView(tvDefaultInfo, rvGame, progressBar)
                        }
                    }

                    is UserUiState.FailedUiState -> {
                        binding.run {
                            tvDefaultInfo.text = userUiState.errorMessage
                        }
                    }

                    is UserUiState.ListGameUiState -> {
                        binding.run {
                            showAndHideView(rvGame, tvDefaultInfo, progressBar)
                            gamesAdapter.submitList(userUiState.data.gameDataResults)
                        }
                    }
                }
            }
        }
    }

    private fun showAndHideView(showedView: View, vararg hiddenView: View) {
        showedView.isVisible = true
        hiddenView.onEach {
            it.isVisible = false
        }
    }


}