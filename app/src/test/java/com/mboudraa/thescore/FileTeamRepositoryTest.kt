package com.mboudraa.thescore

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.mboudraa.thescore.core.FileTeamRepository
import com.mboudraa.thescore.core.Player
import com.mboudraa.thescore.core.Sort
import com.mboudraa.thescore.core.Team
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.squareup.burst.BurstJUnit4
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(BurstJUnit4::class)
class FileTeamRepositoryTest {

    @Test
    fun `should parse json data file`() {

        val teamRepository = FileTeamRepository(Gson(), FileTeamRepositoryTest::class.java.classLoader.getResourceAsStream("data.json"))
        val teams = runBlocking { teamRepository.getTeams(Sort.NAME) }

        assertThat(teams).containsExactly(
                Team(id = 1, wins = 45, losses = 20, name = "Boston Celtics", players = listOf(
                        Player(id = 37729, firstName = "Kadeem", lastName = "Allen", position = "SG", number = 45),
                        Player(id = 30508, firstName = "Aron", lastName = "Baynes", position = "C", number = 46),
                        Player(id = 30847, firstName = "Jabari", lastName = "Bird", position = "SG", number = 26)
                )),
                Team(id = 2, wins = 20, losses = 44, name = "Brooklyn Nets", players = listOf(
                        Player(id = 802, firstName = "Quincy", lastName = "Acy", position = "F", number = 13),
                        Player(id = 53393, firstName = "Jarrett", lastName = "Allen", position = "F-C", number = 31),
                        Player(id = 233, firstName = "DeMarre", lastName = "Carroll", position = "F", number = 9)
                ))
        )
    }

    @Test
    fun `should close json stream after calling getTeams()`() {
        val stream = spy(FileTeamRepositoryTest::class.java.classLoader.getResourceAsStream("data.json"))
        runBlocking { FileTeamRepository(Gson(), stream).getTeams(Sort.NAME) }
        verify(stream).close()
    }


    @Test
    fun `should sort teams accordingly`(sort: Sort) {
        val teamRepository = FileTeamRepository(Gson(), FileTeamRepositoryTest::class.java.classLoader.getResourceAsStream("data.json"))
        val teams = runBlocking { teamRepository.getTeams(sort) }
        val expected = when (sort) {
            Sort.NAME -> listOf(
                    Team(id = 1, wins = 45, losses = 20, name = "Boston Celtics", players = listOf(
                            Player(id = 37729, firstName = "Kadeem", lastName = "Allen", position = "SG", number = 45),
                            Player(id = 30508, firstName = "Aron", lastName = "Baynes", position = "C", number = 46),
                            Player(id = 30847, firstName = "Jabari", lastName = "Bird", position = "SG", number = 26)
                    )),
                    Team(id = 2, wins = 20, losses = 44, name = "Brooklyn Nets", players = listOf(
                            Player(id = 802, firstName = "Quincy", lastName = "Acy", position = "F", number = 13),
                            Player(id = 53393, firstName = "Jarrett", lastName = "Allen", position = "F-C", number = 31),
                            Player(id = 233, firstName = "DeMarre", lastName = "Carroll", position = "F", number = 9)
                    ))
            )
            Sort.WINS -> listOf(
                    Team(id = 1, wins = 45, losses = 20, name = "Boston Celtics", players = listOf(
                            Player(id = 37729, firstName = "Kadeem", lastName = "Allen", position = "SG", number = 45),
                            Player(id = 30508, firstName = "Aron", lastName = "Baynes", position = "C", number = 46),
                            Player(id = 30847, firstName = "Jabari", lastName = "Bird", position = "SG", number = 26)
                    )),
                    Team(id = 2, wins = 20, losses = 44, name = "Brooklyn Nets", players = listOf(
                            Player(id = 802, firstName = "Quincy", lastName = "Acy", position = "F", number = 13),
                            Player(id = 53393, firstName = "Jarrett", lastName = "Allen", position = "F-C", number = 31),
                            Player(id = 233, firstName = "DeMarre", lastName = "Carroll", position = "F", number = 9)
                    ))
            )
            Sort.LOSSES -> listOf(
                    Team(id = 2, wins = 20, losses = 44, name = "Brooklyn Nets", players = listOf(
                            Player(id = 802, firstName = "Quincy", lastName = "Acy", position = "F", number = 13),
                            Player(id = 53393, firstName = "Jarrett", lastName = "Allen", position = "F-C", number = 31),
                            Player(id = 233, firstName = "DeMarre", lastName = "Carroll", position = "F", number = 9)
                    )),
                    Team(id = 1, wins = 45, losses = 20, name = "Boston Celtics", players = listOf(
                            Player(id = 37729, firstName = "Kadeem", lastName = "Allen", position = "SG", number = 45),
                            Player(id = 30508, firstName = "Aron", lastName = "Baynes", position = "C", number = 46),
                            Player(id = 30847, firstName = "Jabari", lastName = "Bird", position = "SG", number = 26)
                    ))
            )
        }

        assertThat(teams).containsExactlyElementsIn(expected).inOrder()
    }
}