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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.viewmodel.CountdownViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun CountDown() {
    val vm = CountdownViewModel()
    val model: CountdownViewModel.Model by vm.data.observeAsState(vm.data.value!!)

    Surface(color = MaterialTheme.colors.background) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.countdown_title),
                style = MaterialTheme.typography.h4
            )
            Spacer(
                modifier = Modifier.padding(
                    vertical = 16.dp
                )
            )

            val currentTime = model.current
            if (currentTime != null) {
                CountDownTicking(currentTime, model.previous)
            } else {
                CountDownInput { hours, minutes, seconds ->
                    vm.start(hours, minutes, seconds)
                }
            }
        }
    }
}
