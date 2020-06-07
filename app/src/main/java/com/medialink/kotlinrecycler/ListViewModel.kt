package com.medialink.kotlinrecycler

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class ListViewModel: ViewModel() {
    @Inject
    lateinit var usersService: UsersService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposable = CompositeDisposable()

    val users = MutableLiveData<List<User>>()
    val userLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    companion object {
        const val TAG = "ListViewModel"
    }

    fun refresh() {
        fetchUser()
    }

    private fun fetchUser() {
        loading.value = true
        disposable.add(
            usersService.getUsers()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<Data>() {
                    override fun onSuccess(t: Data) {
                        Log.d(TAG, "onSuccess: "+t)
                        users.value = t.data as List<User>?
                        userLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "onError: "+e.printStackTrace())
                        userLoadError.value = true
                        loading.value = false
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
