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