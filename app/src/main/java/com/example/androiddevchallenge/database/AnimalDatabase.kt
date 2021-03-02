package com.example.androiddevchallenge.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.androiddevchallenge.database.converter.Converters
import io.github.serpro69.kfaker.Faker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import kotlin.random.Random

@Database(
    entities = [AnimalEntity::class, DogEntity::class, CatEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AnimalDatabase : RoomDatabase() {
    abstract fun animalDao(): AnimalDao

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
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Timber.d("Animal Database Created")

                            GlobalScope.launch(Dispatchers.IO) {
                                seedDatabaseWithData()
                            }

                        }
                    }).build()
            }

            return INSTANCE
        }

        private suspend fun seedDatabaseWithData() {

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

        private val hairValues = HairType.values()
        private fun getRandomHairType() = HairType.values()[Random.nextInt(HairType.values().size)]

        private fun adoptionContent(
            animalName: String,
            animalType: String,
            animalSize: String,
            age: String
        ) =
            "Hi, I'm $animalName and I'm a $animalType. I am $age, $animalSize $animalType and full of energy. Please adopt me!"


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