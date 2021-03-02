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
package com.example.androiddevchallenge.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface AnimalDao {

    @Query("Select * from AnimalEntity")
    suspend fun getAllAnimals(): List<AnimalEntity>

    @Transaction
    @Query("Select * from AnimalEntity a inner join CatEntity c on a.animalId = c.catAnimalId")
    suspend fun getAllCats(): List<CatAndAnimal>

    @Transaction
    @Query("Select * from AnimalEntity a inner join DogEntity d on d.dogAnimalId = a.animalId")
    suspend fun getAllDogs(): List<DogAndAnimal>

    @Insert
    suspend fun insertAnimal(animal: AnimalEntity)

    @Insert
    @Transaction
    suspend fun insertAllAnimals(vararg animal: AnimalEntity)

    @Insert
    suspend fun insertCat(catEntity: CatEntity)

    @Insert
    @Transaction
    suspend fun insertAllCats(vararg catEntity: CatEntity)

    @Insert
    suspend fun insertDog(dogEntity: DogEntity)

    @Insert
    @Transaction
    suspend fun insertAllDogs(vararg dogEntity: DogEntity)

    @Query("Select * from AnimalEntity a inner join CatEntity c on a.animalId = c.catAnimalId where a.animalId=:animalId")
    suspend fun getCatByAnimalId(animalId: Int): CatAndAnimal

    @Query("Select * from AnimalEntity a inner join DogEntity d on a.animalId = d.dogAnimalId where a.animalId=:animalId")
    suspend fun getDogByAnimalId(animalId: Int): DogAndAnimal

    @Delete
    suspend fun deleteAnimal(animal: AnimalEntity)

    @Delete
    suspend fun deleteCat(catEntity: CatEntity)

    @Delete
    suspend fun deleteDog(dogEntity: DogEntity)
}
