package com.pandulapeter.campfire.data.repository

import com.pandulapeter.campfire.data.model.SongInfo
import com.pandulapeter.campfire.data.network.NetworkManager
import com.pandulapeter.campfire.data.storage.DataStorageManager
import com.pandulapeter.campfire.data.storage.PreferenceStorageManager
import com.pandulapeter.campfire.util.enqueueCall

/**
 * Wraps caching and updating of [SongInfo] objects.
 */
class SongInfoRepository(
    private val preferenceStorageManager: PreferenceStorageManager,
    private val dataStorageManager: DataStorageManager,
    private val networkManager: NetworkManager,
    private val languageRepository: LanguageRepository) : Repository() {
    private var dataSet = dataStorageManager.songInfoCache
        get() {
            if (!isLoading && System.currentTimeMillis() - preferenceStorageManager.lastUpdateTimestamp > CACHE_VALIDITY_LIMIT) {
                updateDataSet()
            }
            return field
        }
        set(value) {
            if (field != value) {
                field = value
                languageRepository.updateLanguages(value)
                notifySubscribers()
            }
        }
    var isLoading = false
        set(value) {
            if (field != value) {
                field = value
                notifySubscribers()
            }
        }

    init {
        languageRepository.updateLanguages(dataSet)
    }

    fun updateDataSet(onError: () -> Unit = {}) {
        isLoading = true
        networkManager.service.getLibrary().enqueueCall(
            onSuccess = {
                dataSet = it
                isLoading = false
                dataStorageManager.songInfoCache = dataSet
                preferenceStorageManager.lastUpdateTimestamp = System.currentTimeMillis()
            },
            onFailure = {
                isLoading = false
                onError()
            })
    }

    fun getLibrarySongs() = dataSet

    companion object {
        private const val CACHE_VALIDITY_LIMIT = 1000 * 60 * 60 * 24
    }
}