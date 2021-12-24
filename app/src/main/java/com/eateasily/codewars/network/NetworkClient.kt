package com.eateasily.codewars.network

import javax.inject.Inject

class NetworkClient @Inject constructor(
    private val networkService: NetworkService
) {
}