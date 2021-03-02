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
package com.example.androiddevchallenge.ui.composeables

import androidx.annotation.ColorRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.model.Animal

@Composable
fun AnimalDetail(
    animal: Animal,
    navController: NavHostController = rememberNavController()
) {

    Scaffold(
        topBar = {
            AnimalToolbar(
                title = animal.name,
                showBackButton = true,
                navController = navController
            )
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                HeaderRow(animal = animal)
            }
            item {

                Row {
                    AttributeColumn(animal)

                    Text(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(alignment = Alignment.Top)
                            .padding(20.dp),
                        text = "\"${animal.adoptionContent}\"",
                        style = MaterialTheme.typography.h6
                    )
                }
            }

            item {
                Row(modifier = Modifier.padding(4.dp)) {
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = colorResource(id = R.color.primaryColor))
                    ) {
                        Text(
                            text = "Schedule an Appointment",
                            modifier = Modifier.padding(16.dp),
                            color = colorResource(id = R.color.primaryTextColor)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AttributeColumn(animal: Animal) = Column {
    AttributeCard(
        icon = painterResource(id = R.drawable.ic_age),
        text = "Age: ${animal.age}",
        animal = animal
    )
    AttributeCard(
        icon = painterResource(id = R.drawable.ic_color),
        text = "Color: ${animal.color}",
        animal = animal
    )
    if (animal is Animal.Cat) {
        AttributeCard(
            icon = painterResource(id = R.drawable.ic_hair),
            text = "Hair: ${animal.hairType.name}",
            animal = animal
        )
    }

    if (animal is Animal.Dog) {
        AttributeCard(
            icon = painterResource(id = R.drawable.ic_size),
            text = "Size: ${animal.size}",
            animal = animal
        )
        AttributeCard(
            icon = painterResource(id = R.drawable.ic_happy),
            text = "Happy: ${animal.happiness}%",
            animal = animal
        )
    }
}

@Composable
fun AttributeCard(
    icon: Painter,
    text: String,
    animal: Animal,
    @ColorRes backgroundColor: Int = if (animal is Animal.Cat) R.color.primaryDarkColor else R.color.secondaryColor
) = Column(modifier = Modifier.padding(8.dp)) {
    Card(
        backgroundColor = colorResource(id = backgroundColor),
        modifier = Modifier.clickable { }
    ) {
        Column(
            modifier = Modifier.width(130.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = icon,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                contentDescription = "Icon",
            )
            Text(
                text = text,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.subtitle2,
                color = colorResource(id = R.color.primaryTextColor)
            )
        }
    }
}

@Composable
fun HeaderRow(animal: Animal) {
    val cardBackground = if (animal is Animal.Cat)
        R.color.primaryColor
    else
        R.color.secondaryColor

    return Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(colorResource(id = cardBackground)),
    ) {
        Image(
            painter = painterResource(id = animal.imageRes),
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.Center),
            contentDescription = animal.contentDescription
        )

        Text(
            text = animal.breed,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(8.dp),
            style = MaterialTheme.typography.subtitle1,
            color = colorResource(id = R.color.primaryTextColor)
        )
    }
}

val catSample = Animal.Cat.sample()
val dogSample = Animal.Dog.sample()

@Preview
@Composable
fun AnimalDetailPreview() = AnimalDetail(animal = catSample)
