package com.medialink.kotlinrecycler

import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: UsersService)
    fun inject(viewModel: ListViewModel)
}