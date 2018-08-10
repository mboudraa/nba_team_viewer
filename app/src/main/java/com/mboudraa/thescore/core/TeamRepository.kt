package com.mboudraa.thescore.core

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mboudraa.thescore.R
import java.io.InputStream
import java.io.InputStreamReader

interface TeamRepository {
    suspend fun getTeams(sort: Sort): List<Team>
}

class FileTeamRepository(gson: Gson, inputStream: InputStream) : TeamRepository {
    constructor(gson: Gson, context: Context) : this(gson, context.resources.openRawResource(R.raw.data))

    private val teams by lazy {
        InputStreamReader(inputStream).use {
            gson.fromJson(it, object : TypeToken<ArrayList<Team>>() {}.type) as List<Team>
        }
    }

    override suspend fun getTeams(sort: Sort): List<Team> = teams.sort(sort)
}

fun Iterable<Team>.sort(sort: Sort) = when (sort) {
    Sort.NAME -> sortedBy { it.name }
    Sort.WINS -> sortedByDescending { it.wins }
    Sort.LOSSES -> sortedByDescending { it.losses }
}