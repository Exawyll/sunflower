/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * The Data Access Object for the Score class.
 */
@Dao
interface ScoringDao {
    @Query("SELECT * FROM scoring")
    fun getScores(): LiveData<List<Scoring>>

    @Query("""SELECT * FROM scoring 
        WHERE hole_id IN (SELECT DISTINCT(hole_id) 
        FROM golfs WHERE id = :golfId)""")
    fun getGolfScoring(golfId: Long): LiveData<List<Scoring>>

    @Query("SELECT * FROM scoring WHERE hole_id = :holeId")
    fun getHoleScoring(holeId: String): LiveData<List<Scoring>>

    @Query("SELECT * FROM scoring WHERE id = :scoringId")
    fun getScore(scoringId: String): LiveData<Scoring>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(scoring: List<Scoring>)
}
