package com.example.androiddevchallenge.ui.composeables

import androidx.annotation.ColorRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
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
                title = "${animal.name}",
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