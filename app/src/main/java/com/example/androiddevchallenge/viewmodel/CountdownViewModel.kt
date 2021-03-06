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
package com.example.androiddevchallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.data.TimeHolder
import com.example.androiddevchallenge.repository.CountDownRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class CountdownViewModel(
    private val countDownRepository: CountDownRepository = CountDownRepository()
) : ViewModel() {

    private var currentCountDown: Job? = null

    data class Model(
        val current: TimeHolder? = null,
        val previous: TimeHolder? = null
    )

    private val _data: MutableLiveData<Model> = MutableLiveData(Model())
    val data: LiveData<Model>
        get() = _data

    fun start(hours: Long, minutes: Long, seconds: Long) {
        currentCountDown?.cancel()
        currentCountDown = viewModelScope.launch {
            val flow: Flow<TimeHolder> = countDownRepository.countDown(
                TimeHolder(hours = hours, minutes = minutes, seconds = seconds)
            )
            flow.onCompletion {
                _data.value = Model(
                    current = null,
                    previous = null
                )
            }.collect {
                _data.value = _data.value?.copy(
                    current = it,
                    previous = _data.value?.current
                )
            }
        }
    }
}
