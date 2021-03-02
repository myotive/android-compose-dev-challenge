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
package com.example.androiddevchallenge

    import android.app.Application
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.androiddevchallenge.model.Animal
import com.example.androiddevchallenge.repository.AnimalRepository
import com.example.androiddevchallenge.ui.composeables.AnimalDetail
import com.example.androiddevchallenge.ui.composeables.AnimalList
import com.example.androiddevchallenge.ui.theme.MyTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp(myAppViewModel: MyAppViewModel = viewModel()) {
    val navController = rememberNavController()

    val animals by myAppViewModel.animals.observeAsState()
    val coroutineScope = rememberCoroutineScope()

    Surface(color = MaterialTheme.colors.background) {
        NavHost(navController, startDestination = "animal-list") {
            composable("animal-list") {

                coroutineScope.launch {
                    myAppViewModel.getAnimals()
                }

                AnimalList(animals = animals ?: listOf()) { pos: Int, animal: Animal ->
                    navController.navigate(route = "animal-detail/$pos") {
                        launchSingleTop = true
                    }
                }
            }

            composable(
                route = "animal-detail/{id}",
                arguments = listOf(navArgument("id") { defaultValue = 0 })
            ) {
                val pos = it.arguments?.getInt("id") ?: 0
                val animal = animals?.get(pos)
                if (animal != null)
                    AnimalDetail(animal = animal, navController = navController)
                else {
                    Toast.makeText(
                        LocalContext.current,
                        "Something went wrong. Please try again...",
                        Toast.LENGTH_LONG
                    ).show()
                    navController.popBackStack()
                }
            }
        }
    }
}


class MyAppViewModel(application: Application) : AndroidViewModel(application) {

    private val animalRepository by lazy { AnimalRepository.getInstance(application) }

    private val _animals = MutableLiveData<List<Animal>>()
    val animals: LiveData<List<Animal>> = _animals

    suspend fun getAnimals() = withContext(Dispatchers.IO) {
            val animals = animalRepository.getAnimals()
            withContext(Dispatchers.Main) {
                _animals.value = animals
            }
        }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
