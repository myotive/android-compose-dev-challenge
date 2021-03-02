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
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.androiddevchallenge.R

@Composable
fun AnimalToolbar(
    title: String,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
    elevation: Dp = 0.dp,
    navController: NavHostController? = null
) =
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.body1
            )
        },
        elevation = elevation,
        modifier = modifier,
        backgroundColor = colorResource(id = R.color.toolbarColor),
        navigationIcon = if (showBackButton)
            buildNavigation(navController)
        else
            null
    )

@Composable
fun buildNavigation(navController: NavHostController? = null): @Composable () -> Unit {
    return {
        IconButton(
            onClick = { navController?.popBackStack() }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Go back to home."
            )
        }
    }
}
