package com.example.gamedatabasemviapp

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamedatabasemviapp.data.MainRepository
import com.example.gamedatabasemviapp.data.datasource.NetworkRepositoryImpl
import com.example.gamedatabasemviapp.data.network.RetrofitService
import com.example.gamedatabasemviapp.databinding.ActivityMainBinding
import com.example.gamedatabasemviapp.presentation.user.UserAction
import com.example.gamedatabasemviapp.presentation.user.UserUiState
import com.example.gamedatabasemviapp.presentation.user.UserViewModel
import com.example.gamedatabasemviapp.presentation.user.UserViewModelFactory
import com.example.gamedatabasemviapp.ui.listuser.GamesAdapter
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val gamesAdapter: GamesAdapter by lazy { GamesAdapter() }
    private lateinit var userViewModel: UserViewModel

    private lateinit var binding: ActivityMainBinding

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initViewModel()
        loadGameData()
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
        val vm by viewModels<UserViewModel> {
            UserViewModelFactory(
                MainRepository(NetworkRepositoryImpl(RetrofitService.apiService))
            )
        }
        userViewModel = vm
    }

    private fun observerTextInput() {
        disposable =
            RxTextView.textChanges(binding.edtSearchGame).throttleLast(2000, TimeUnit.MILLISECONDS)
                .subscribe {
                    lifecycleScope.launch {
                        userViewModel.queryGameIntent.send(UserAction.FetchGamesAction(it.toString()))
                    }
                }
    }

    private fun loadGameData() {
        lifecycleScope.launch {
            userViewModel.queryGameState.collect { userUiState ->
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