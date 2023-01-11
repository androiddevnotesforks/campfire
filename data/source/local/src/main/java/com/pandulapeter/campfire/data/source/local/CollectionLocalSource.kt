package com.pandulapeter.campfire.data.source.local

import com.pandulapeter.campfire.data.model.domain.Collection

interface CollectionLocalSource {

    suspend fun loadCollections(sheetUrl: String): List<Collection>

    suspend fun saveCollections(sheetUrl: String, collections: List<Collection>)
}