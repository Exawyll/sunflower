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
 * The Data Access Object for the Hole class.
 */
@Dao
interface HoleDao {
    @Query("SELECT * FROM holes ORDER BY name")
    fun getHoles(): LiveData<List<Hole>>

    @Query("SELECT * FROM holes WHERE id = :holeId")
    fun getHole(holeId: String): LiveData<Hole>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(holes: List<Hole>)
}
