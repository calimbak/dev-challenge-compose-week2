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
package com.example.androiddevchallenge.ui.screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.ScrollingAnimation

@ExperimentalAnimationApi
@Composable
fun CountDownInput(onCountDownStartClick: (Long, Long, Long) -> Unit) {
    var numberHours: Long by remember { mutableStateOf(0L) }
    var numberMinutes: Long by remember { mutableStateOf(0L) }
    var numberSeconds: Long by remember { mutableStateOf(0L) }
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
            vertical = 2.dp
        )
    ) {
        NumberInput(numberHours) { numberHours = it }
        NumberInputSemiColumn()
        NumberInput(numberMinutes) { numberMinutes = it }
        NumberInputSemiColumn()
        NumberInput(numberSeconds) { numberSeconds = it }
    }
    Spacer(
        modifier = Modifier.padding(
            vertical = 16.dp
        )
    )
    Button(onClick = { onCountDownStartClick(numberHours, numberMinutes, numberSeconds) }) {
        Text(text = stringResource(R.string.count_down_cta))
    }
}

@Composable
private fun NumberInputSemiColumn() {
    Text(text = stringResource(R.string.semi_column))
}

@Composable
@ExperimentalAnimationApi
private fun NumberInput(number: Long, onValueChanged: (Long) -> Unit) {
    var previousNumber: Long? by remember { mutableStateOf(null) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_arrow_up),
            contentDescription = "",
            modifier = Modifier.clickable {
                previousNumber = number
                onValueChanged((number + 1) % 60)
            },
            colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
        )
        ScrollingAnimation(number, previousNumber) { TextForInput(it) }
        Image(
            painter = painterResource(R.drawable.ic_arrow_down),
            contentDescription = "",
            modifier = Modifier.clickable {
                previousNumber = number
                onValueChanged(if (number - 1 < 0) 59 else number - 1)
            },
            colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
        )
    }
}

@Composable
private fun TextForInput(value: Long) =
    Text(text = value.toString().padStart(2, '0'), style = MaterialTheme.typography.body1)
