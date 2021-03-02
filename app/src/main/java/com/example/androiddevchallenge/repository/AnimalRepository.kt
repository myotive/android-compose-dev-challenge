/*
 * Copyright 2021 The Android Open Source Project
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
package com.example.androiddevchallenge.repository

import android.content.Context
import com.example.androiddevchallenge.database.AnimalDatabase
import com.example.androiddevchallenge.model.Animal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface IAnimalRepository {
    suspend fun getAnimals(): List<Animal>
}

class AnimalRepository private constructor(context: Context) : IAnimalRepository {

    private val animalDatabase: AnimalDatabase = AnimalDatabase.getDatabase(context)

    override suspend fun getAnimals(): List<Animal> =
        withContext(Dispatchers.IO) {
            val dogs = animalDatabase.animalDao().getAllDogs()
                .map {
                    Animal.Dog(it)
                }

            val cats = animalDatabase.animalDao().getAllCats()
                .map {
                    Animal.Cat(it)
                }

            // Cats and dogs living together
            cats.plus(dogs).sortedByDescending {
                it.createdOn
            }
        }

    private object HOLDER {
        lateinit var animalRepository: IAnimalRepository
        fun getInstance(context: Context): IAnimalRepository = if (::animalRepository.isInitialized)
            animalRepository
        else {
            animalRepository = AnimalRepository(context)
            animalRepository
        }
    }

    companion object {
        fun getInstance(context: Context): IAnimalRepository = HOLDER.getInstance(context)
    }
}
