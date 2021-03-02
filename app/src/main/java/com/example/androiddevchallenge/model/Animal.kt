package com.example.androiddevchallenge.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.material.MaterialTheme
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.database.CatAndAnimal
import com.example.androiddevchallenge.database.DogAndAnimal
import com.example.androiddevchallenge.database.DogEntity
import com.example.androiddevchallenge.database.HairType
import java.util.*

sealed class Animal(
    val name: String,
    @DrawableRes val imageRes: Int,
    @ColorRes val iconBackgroundColor: Int,
    val contentDescription: String,
    val breed: String,
    val cuteness: Int,
    val age: String,
    val color: String,
    val hairType: HairType,
    val adoptionContent: String,
    val createdOn: Date
) {
    class Dog private constructor(
        val happiness: Int,
        val size: String,
        val coatLength: String,
        name: String,
        createdOn: Date,
        contentDescription: String,
        cuteness: Int,
        breed: String,
        age: String,
        hairType: HairType,
        adoptionContent: String,
        color: String,
    ) : Animal(
        name = name,
        imageRes = R.drawable.ic_dog,
        iconBackgroundColor = R.color.secondaryColor,
        contentDescription = contentDescription,
        createdOn = createdOn,
        cuteness = cuteness,
        breed = breed,
        age = age,
        hairType = hairType,
        adoptionContent = adoptionContent,
        color = color
    ) {
        constructor(dogAndAnimal: DogAndAnimal) : this(
            happiness = dogAndAnimal.dogEntity.happiness,
            name = dogAndAnimal.animal.name,
            createdOn = dogAndAnimal.animal.createdOn,
            breed = dogAndAnimal.animal.breed,
            cuteness = dogAndAnimal.animal.cuteness,
            contentDescription = dogAndAnimal.animal.contentDescription,
            age = dogAndAnimal.animal.age,
            hairType = dogAndAnimal.animal.hairType,
            color = dogAndAnimal.animal.color,
            adoptionContent = dogAndAnimal.animal.adoptionContent,
            size = dogAndAnimal.dogEntity.size,
            coatLength = dogAndAnimal.dogEntity.coatLength
        )

        companion object {
            fun sample() =
                Dog(
                    contentDescription = "Sample",
                    cuteness = 100,
                    breed = "Great Dane",
                    createdOn = Date(),
                    name = "Mark",
                    happiness = 100,
                    age = "young",
                    hairType = HairType.Short,
                    adoptionContent = "Lorem Ipsum",
                    color = "Brown",
                    size = "Large",
                    coatLength = "Medium"
                )
        }
    }

    class Cat private constructor(
        val laziness: Int,
        val curiosity: Int,
        name: String,
        createdOn: Date = Date(),
        contentDescription: String = "",
        cuteness: Int,
        breed: String,
        age: String,
        hairType: HairType,
        adoptionContent: String,
        color: String
    ) : Animal(
        name = name,
        imageRes = R.drawable.ic_cat,
        iconBackgroundColor = R.color.primaryColor,
        contentDescription = contentDescription,
        createdOn = createdOn,
        cuteness = cuteness,
        breed = breed,
        age = age,
        hairType = hairType,
        adoptionContent = adoptionContent,
        color = color
    ) {
        constructor(catAndAnimal: CatAndAnimal) : this(
            laziness = catAndAnimal.catEntity.laziness,
            name = catAndAnimal.animal.name,
            createdOn = catAndAnimal.animal.createdOn,
            breed = catAndAnimal.animal.breed,
            cuteness = catAndAnimal.animal.cuteness,
            contentDescription = catAndAnimal.animal.contentDescription,
            age = catAndAnimal.animal.age,
            hairType = catAndAnimal.animal.hairType,
            color = catAndAnimal.animal.color,
            adoptionContent = catAndAnimal.animal.adoptionContent,
            curiosity = catAndAnimal.catEntity.curiosity
        )

        companion object {
            fun sample() =
                Cat(
                    contentDescription = "Sample",
                    cuteness = 100,
                    breed = "Fancy Cat",
                    createdOn = Date(),
                    name = "Rollins",
                    laziness = 100,
                    age = "young",
                    curiosity = 50,
                    hairType = HairType.Short,
                    adoptionContent = "Lorem Ipsum",
                    color = "Brown",
                )
        }
    }
}