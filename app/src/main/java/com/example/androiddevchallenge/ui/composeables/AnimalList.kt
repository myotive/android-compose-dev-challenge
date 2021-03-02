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

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.model.Animal

@Composable
fun AnimalList(
    animals: List<Animal>,
    onClick: (Int, Animal) -> Unit = { _, _ -> }
) =
    Scaffold(
        topBar = {
            AnimalToolbar(title = "Jetpack Compose Dev Challenge")
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            itemsIndexed(animals) { index, animal ->
                AnimalListItem(
                    animal = animal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onClick(index, animal)
                        }
                )
            }
        }
    }

@Composable
private fun AnimalListItem(animal: Animal, modifier: Modifier) =
    Card(modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = modifier
                .height(72.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Card(
                modifier = Modifier
                    .weight(1f),
                backgroundColor = colorResource(id = animal.iconBackgroundColor)
            ) {
                Image(
                    modifier = Modifier.padding(16.dp),
                    painter = painterResource(id = animal.imageRes),
                    contentDescription = animal.contentDescription,
                )
            }

            Column(
                modifier = Modifier
                    .weight(4f)
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = animal.name,

                    style = MaterialTheme.typography.subtitle1
                )
                Text(text = animal.breed, style = MaterialTheme.typography.subtitle2)
            }
        }
    }

private val sampleData = listOf(
    Animal.Dog.sample(),
    Animal.Cat.sample(),
    Animal.Dog.sample(),
    Animal.Cat.sample(),
    Animal.Dog.sample(),
    Animal.Cat.sample(),
    Animal.Dog.sample(),
    Animal.Cat.sample(),
    Animal.Dog.sample(),
    Animal.Cat.sample(),
)

@Preview(showBackground = true)
@Composable
fun AnimalListPreview() = AnimalList(animals = sampleData)
