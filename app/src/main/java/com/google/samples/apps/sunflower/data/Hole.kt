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

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "holes",
    foreignKeys = [
    ForeignKey(entity = com.google.samples.apps.sunflower.data.Scoring::class,
            parentColumns = ["id"], childColumns = ["scoring_id"])
    ],
        indices = [Index("scoring_id")]
)
data class Hole(
    @PrimaryKey @ColumnInfo(name = "id") val holeId: String,
    @ColumnInfo(name = "scoring_id") val scoringId: Long?,
    val name: String,
    val description: String,
    val par: Int
)
