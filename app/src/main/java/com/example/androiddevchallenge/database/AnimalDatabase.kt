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

import android.content.Context
import androidx.room.Database
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.androiddevchallenge.database.converter.Converters
import io.github.serpro69.kfaker.Faker
import timber.log.Timber
import java.util.Date
import kotlin.random.Random

@Database(
    entities = [AnimalEntity::class, DogEntity::class, CatEntity::class],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class AnimalDatabase : RoomDatabase() {
    abstract fun animalDao(): AnimalDao

    suspend fun seedDatabaseWithData() {

        Timber.i("Seeding Database")

        val dao = INSTANCE.animalDao()
        val animals = mutableListOf<AnimalEntity>()
        val dogs = mutableListOf<DogEntity>()
        val cats = mutableListOf<CatEntity>()
        for (i in 1..50) {
            val randomDog = Random.nextBoolean()
            val (animal, entity) = if (randomDog) generateDog(i) else generateCat(i)

            animals += animal
            when (entity) {
                is DogEntity -> dogs += entity
                is CatEntity -> cats += entity
            }
        }

        Timber.i("Loading ${animals.size} Animals...")
        dao.insertAllAnimals(*animals.toTypedArray())
        Timber.i("Animals Loaded.")
        Timber.i("Loading ${dogs.size} Dogs...")
        dao.insertAllDogs(*dogs.toTypedArray())
        Timber.i("Dogs Loaded.")
        Timber.i("Loading ${cats.size} Cats...")
        dao.insertAllCats(*cats.toTypedArray())
        Timber.i("Cats Loaded.")
    }

    private fun generateDog(id: Int): Pair<AnimalEntity, DogEntity> {
        val faker = Faker()
        val dogName = faker.name.neutralFirstName()
        val dogAge = faker.dog.age()
        val cuteness = Random.nextInt(50, 100)
        val hairType = getRandomHairType()
        val animal = AnimalEntity(
            name = dogName,
            animalId = id,
            breed = faker.dog.breed(),
            hairType = hairType,
            cuteness = cuteness,
            age = dogAge,
            color = faker.color.name(),
            adoptionContent = adoptionContent(
                dogName,
                faker.dog.breed(),
                faker.dog.size(),
                dogAge
            ),
            contentDescription = "Dog with name $dogName"
        )
        val dog = DogEntity(
            dogId = id,
            dogAnimalId = animal.animalId,
            happiness = Random.nextInt(50, 100),
            coatLength = faker.dog.coatLength(),
            size = faker.dog.size()
        )
        return animal to dog
    }

    private fun generateCat(id: Int): Pair<AnimalEntity, CatEntity> {
        val faker = Faker()
        val catName = faker.name.neutralFirstName()
        val cuteness = Random.nextInt(50, 100)
        val catAge = if (Random.nextBoolean())
            "kitten"
        else
            "full grown cat"
        val hairType = getRandomHairType()
        val animal = AnimalEntity(
            name = catName,
            animalId = id,
            breed = faker.cat.breed(),
            hairType = hairType,
            cuteness = cuteness,
            age = catAge,
            color = faker.color.name(),
            adoptionContent = adoptionContent(catName, faker.cat.breed(), "small", catAge),
            contentDescription = "Cat with name $catName"
        )
        val cat = CatEntity(
            catId = id,
            catAnimalId = animal.animalId,
            laziness = Random.nextInt(50, 100),
            curiosity = Random.nextInt(50, 100)
        )
        return animal to cat
    }

    private fun getRandomHairType() = HairType.values()[Random.nextInt(HairType.values().size)]

    private fun adoptionContent(
        animalName: String,
        animalType: String,
        animalSize: String,
        age: String
    ) = "Hi, I'm $animalName and I'm a $animalType. I am $age, $animalSize $animalType and full of energy. Please adopt me!"

    companion object {
        private lateinit var INSTANCE: AnimalDatabase
        private const val DB_NAME = "animals.db"

        fun getDatabase(context: Context): AnimalDatabase {
            if (::INSTANCE.isInitialized) return INSTANCE

            synchronized(AnimalDatabase::class.java) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AnimalDatabase::class.java,
                    DB_NAME
                )
                    // .createFromAsset("database/animals.db")
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Timber.d("Animal Database Created")
                        }
                    }).build()
            }

            return INSTANCE
        }
    }
}

@Entity
data class AnimalEntity(
    @PrimaryKey val animalId: Int,
    val name: String,
    val cuteness: Int,
    val breed: String,
    val hairType: HairType,
    val adoptionContent: String,
    val contentDescription: String,
    val age: String,
    val color: String,
    val createdOn: Date = Date()
)

enum class HairType {
    Short, Long
}

@Entity
data class DogEntity(
    @PrimaryKey val dogId: Int,
    val dogAnimalId: Int,
    val happiness: Int,
    val coatLength: String,
    val size: String
)

data class DogAndAnimal(
    @Embedded val animal: AnimalEntity,
    @Relation(
        parentColumn = "animalId",
        entityColumn = "dogAnimalId"
    )
    val dogEntity: DogEntity
)

@Entity
data class CatEntity(
    @PrimaryKey val catId: Int,
    val catAnimalId: Int,
    val laziness: Int,
    val curiosity: Int
)

data class CatAndAnimal(
    @Embedded val animal: AnimalEntity,
    @Relation(
        parentColumn = "animalId",
        entityColumn = "catAnimalId"
    )
    val catEntity: CatEntity
)
