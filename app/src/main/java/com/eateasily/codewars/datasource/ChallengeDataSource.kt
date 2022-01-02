package com.eateasily.codewars.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.eateasily.codewars.models.UserChallengeData
import com.eateasily.codewars.network.NetworkService
import retrofit2.HttpException
import java.io.IOException

class ChallengeDataSource(private val networkService: NetworkService, private val userName: String) :
    PagingSource<Int, UserChallengeData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserChallengeData> {
        val page = params.key ?: 0
        return try {
            val response = networkService.getCompletedChallenge(userName, page)
            val data = response.data
            LoadResult.Page(
                data = data!!,
                prevKey = if (page == 0) null else page - 1,
                nextKey = page + 1
            )

        } catch (exception: IOException) {
            val error = IOException("Please Check Internet Connection")
            LoadResult.Error(error)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, UserChallengeData>): Int? {
        return state.anchorPosition
    }

}