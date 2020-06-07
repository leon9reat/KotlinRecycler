package com.medialink.kotlinrecycler

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: ListViewModel
    private val userAdapter = UserListAdapter(arrayListOf())



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            //your codes here
        }

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        usersList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.refresh()
        }

        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.users.observe(this, Observer {
            it?.let {
                Log.d("observeViewModel", "users: "+it.size.toString())
                usersList.visibility = View.VISIBLE
                userAdapter.updateUsers(it)
            }
        })

        viewModel.userLoadError.observe(this, Observer {
            it?.let {
                list_error.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(this, Observer {
            Log.d("observeViewModel", "loading: "+it.toString())
            loading_view.visibility = if (it) View.VISIBLE else View.GONE
            if (it) {
                list_error.visibility = View.GONE
                usersList.visibility = View.GONE
            }
        })
    }
}