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
package com.example.androiddevchallenge.repository

import com.example.androiddevchallenge.data.TimeHolder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.TimeUnit
import kotlin.concurrent.fixedRateTimer

@ExperimentalCoroutinesApi
class CountDownRepository {

    suspend fun countDown(time: TimeHolder): Flow<TimeHolder> =
        callbackFlow {
            val startTime = System.currentTimeMillis()
            offer(time) // initial emission
            val timer = fixedRateTimer(period = 1000L) {
                var diffTime = time.toMillis() - (System.currentTimeMillis() - startTime)
                if (diffTime <= 0) {
                    cancel()
                    close()
                    return@fixedRateTimer
                }

                val hours = TimeUnit.MILLISECONDS.toHours(diffTime)
                diffTime %= TimeUnit.HOURS.toMillis(1)
                val minutes = TimeUnit.MILLISECONDS.toMinutes(diffTime)
                diffTime %= TimeUnit.MINUTES.toMillis(1)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(diffTime)
                offer(
                    TimeHolder(
                        hours = hours,
                        minutes = minutes,
                        seconds = seconds
                    )
                )
            }
            awaitClose {
                timer.cancel()
            }
        }
}
