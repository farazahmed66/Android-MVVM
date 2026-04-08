package com.faraz.codewars.presentation.util

import android.content.Context
import com.faraz.codewars.R
import com.faraz.codewars.domain.Resource

fun Resource.Failure.toMessage(context: Context): String = when {
    isNetworkError -> context.getString(R.string.no_internet)
    errorCode == 429 -> context.getString(R.string.error_too_many_requests)
    errorCode == 401 || errorCode == 403 -> context.getString(R.string.error_forbidden)
    errorCode != null && errorCode >= 500 -> context.getString(R.string.error_server)
    else -> context.getString(R.string.something_went_wrong)
}