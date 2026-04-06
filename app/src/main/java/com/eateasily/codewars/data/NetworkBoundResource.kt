package com.eateasily.codewars.data

import com.eateasily.codewars.domain.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

/**
 * Stale-while-revalidate NetworkBoundResource.
 *
 * Flow of emissions:
 * 1. [Resource.Loading]
 * 2. [Resource.Success] with cached data immediately, if available.
 * 3a. [Resource.Success] with fresh network data after a successful fetch + save.
 * 3b. Nothing extra on network failure when cached data was already emitted.
 * 3c. [Resource.Failure] when there is no cached data and the network call fails.
 * 3d. [Resource.Empty] when [shouldFetch] returns false and there is no cached data.
 */
/**
 * Two-type variant: [Remote] is the network/DTO type, [Domain] is the domain model.
 * Use when [fetch] returns a different type than [query].
 */
inline fun <Remote, Domain> networkBoundResource(
    crossinline query: suspend () -> Domain?,
    crossinline fetch: suspend () -> Remote,
    crossinline saveFetchResult: suspend (Remote) -> Unit,
    crossinline mapResult: (Remote) -> Domain,
    crossinline shouldFetch: (Domain?) -> Boolean = { true }
): Flow<Resource<Domain>> = flow {
    emit(Resource.Loading)

    val cached = try { query() } catch (_: Exception) { null }

    if (cached != null) {
        emit(Resource.Success(cached))
    }

    if (shouldFetch(cached)) {
        try {
            val networkResult = fetch()
            saveFetchResult(networkResult)
            emit(Resource.Success(mapResult(networkResult)))
        } catch (throwable: Throwable) {
            if (cached == null) {
                emit(throwable.toFailure())
            }
        }
    } else if (cached == null) {
        emit(Resource.Empty)
    }
}

inline fun <T> networkBoundResource(
    crossinline query: suspend () -> T?,
    crossinline fetch: suspend () -> T,
    crossinline saveFetchResult: suspend (T) -> Unit,
    crossinline shouldFetch: (T?) -> Boolean = { true }
): Flow<Resource<T>> = flow {
    emit(Resource.Loading)

    val cached = try { query() } catch (_: Exception) { null }

    if (cached != null) {
        emit(Resource.Success(cached))
    }

    if (shouldFetch(cached)) {
        try {
            val networkResult = fetch()
            saveFetchResult(networkResult)
            emit(Resource.Success(networkResult))
        } catch (throwable: Throwable) {
            if (cached == null) {
                emit(throwable.toFailure())
            }
        }
    } else if (cached == null) {
        emit(Resource.Empty)
    }
}

fun Throwable.toFailure(): Resource.Failure =
    if (this is HttpException)
        Resource.Failure(false, code(), response()?.errorBody()?.use { it.string() })
    else
        Resource.Failure(true, null, null)
