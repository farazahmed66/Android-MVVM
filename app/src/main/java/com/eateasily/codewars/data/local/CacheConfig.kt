package com.eateasily.codewars.data.local

import com.eateasily.codewars.data.local.entity.AuthoredChallengeCacheEntity
import com.eateasily.codewars.data.local.entity.ChallengeDetailsCacheEntity
import com.eateasily.codewars.data.local.entity.UserCacheEntity

const val CACHE_TTL_MS = 5 * 60 * 1_000L // 5 minutes

fun UserCacheEntity.isExpired(): Boolean = System.currentTimeMillis() - cachedAt > CACHE_TTL_MS
fun AuthoredChallengeCacheEntity.isExpired(): Boolean = System.currentTimeMillis() - cachedAt > CACHE_TTL_MS
fun ChallengeDetailsCacheEntity.isExpired(): Boolean = System.currentTimeMillis() - cachedAt > CACHE_TTL_MS