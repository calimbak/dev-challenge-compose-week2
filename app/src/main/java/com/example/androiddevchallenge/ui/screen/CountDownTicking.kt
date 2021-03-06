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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.TimeHolder
import com.example.androiddevchallenge.ui.ScrollingAnimation

@ExperimentalAnimationApi
@Composable
fun CountDownTicking(current: TimeHolder, previous: TimeHolder?) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(
            vertical = 2.dp
        )
    ) {
        ScrollingAnimation(current.hours, previous?.hours) {
            TextForAnimation(
                it,
                MaterialTheme.colors.onSurface
            )
        }
        TickingSemiColumn()
        ScrollingAnimation(current.minutes, previous?.minutes) {
            TextForAnimation(
                it,
                MaterialTheme.colors.secondary
            )
        }
        TickingSemiColumn()
        ScrollingAnimation(current.seconds, previous?.seconds) {
            TextForAnimation(
                it,
                MaterialTheme.colors.primary
            )
        }
    }
}

@Composable
private fun TextForAnimation(value: Long, color: Color) =
    Text(
        text = value.toString().padStart(2, '0'),
        style = MaterialTheme.typography.h1,
        color = color
    )

@Composable
private fun TickingSemiColumn() =
    Text(text = stringResource(R.string.semi_column), style = MaterialTheme.typography.h1)
