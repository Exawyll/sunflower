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

/**
 * Repository module for handling data operations.
 */
class HoleRepository private constructor(private val holeDao: HoleDao) {

    fun getHoles() = holeDao.getHoles()

    fun getHole(holeId: String) = holeDao.getHole(holeId)

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: HoleRepository? = null

        fun getInstance(holeDao: HoleDao) =
                instance ?: synchronized(this) {
                    instance ?: HoleRepository(holeDao).also { instance = it }
                }
    }
}
