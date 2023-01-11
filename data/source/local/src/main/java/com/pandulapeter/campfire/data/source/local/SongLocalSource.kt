package com.pandulapeter.campfire.data.source.local

import com.pandulapeter.campfire.data.model.domain.Song

interface SongLocalSource {

    suspend fun loadSongs(sheetUrl: String): List<Song>

    suspend fun saveSongs(sheetUrl: String, songs: List<Song>)
}