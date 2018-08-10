package com.mboudraa.thescore.core

import com.google.gson.annotations.SerializedName

data class Team(val id: Int,
                @SerializedName("full_name")val name: String,
                val wins: Int,
                val losses: Int,
                val players: List<Player>){
    override fun toString(): String="Team(id=$id, name=$name, wins=$wins, losses=$losses, players=[...])"
}

data class Player(val id: Int,
                  @SerializedName("first_name")val firstName: String,
                  @SerializedName("last_name")val lastName: String,
                  val number: Int,
                  val position: String)

enum class Sort {
    NAME, WINS, LOSSES
}