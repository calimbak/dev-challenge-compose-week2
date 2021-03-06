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
package com.example.androiddevchallenge.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds

@ExperimentalAnimationApi
@Composable
fun ScrollingAnimation(
    value: Long,
    previousValue: Long?,
    content: @Composable (Long) -> Unit
) {
    if (previousValue == null || value == previousValue) {
        content(value)
    } else {
        var isVisible by remember { mutableStateOf(false) }
        isVisible = (value % 2L) == 0L

        val firstValue = if (isVisible) {
            value
        } else {
            previousValue
        }
        val otherValue = if (!isVisible) {
            value
        } else {
            previousValue
        }

        Box(
            modifier = Modifier.clipToBounds(),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                initiallyVisible = !isVisible,
                visible = isVisible,
                enter = getEnterAnimation(isVisible),
                exit = getExitAnimation(isVisible),
                content = { content(firstValue) }
            )
            AnimatedVisibility(
                initiallyVisible = isVisible,
                visible = !isVisible,
                enter = getEnterAnimation(!isVisible),
                exit = getExitAnimation(!isVisible),
                content = { content(otherValue) }
            )
        }
    }
}

@ExperimentalAnimationApi
private fun getEnterAnimation(isVisible: Boolean): EnterTransition =
    if (isVisible) {
        slideInVertically(
            initialOffsetY = { -it }
        ) + fadeIn(initialAlpha = 0.8f)
    } else {
        fadeIn(initialAlpha = 1f)
    }

@ExperimentalAnimationApi
private fun getExitAnimation(isVisible: Boolean): ExitTransition =
    if (isVisible) {
        fadeOut(targetAlpha = 1f)
    } else {
        slideOutVertically(
            targetOffsetY = { it }
        ) + fadeOut(targetAlpha = 0.8f)
    }
