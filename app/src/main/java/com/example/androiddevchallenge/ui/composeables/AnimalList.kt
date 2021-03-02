package com.example.androiddevchallenge.ui.composeables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
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
        LazyColumn(modifier = Modifier
            .fillMaxWidth()) {
            itemsIndexed(animals) { index, animal ->
                AnimalListItem(animal = animal, modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick(index, animal)
                    })
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
                Text(text = animal.name,

                    style = MaterialTheme.typography.subtitle1)
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