/*
 * MIT License
 *
 * Copyright (c) 2021 Daniel Knauf
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@file:Suppress("unused")

package danielknauf.livedatakit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import danielknauf.livedatakit.internal.subscribeTo

/**
 * A MergerLiveData is a [MediatorLiveData] which [subscribeTo] changes of one or multiple
 * sources when first observer is attached as well as removes all sources when last observer
 * detaches. New values are determined by merging all source values into one value of
 * [TargetType] and posted asynchronously.
 */
sealed class MergerLiveData<TargetType> : MediatorLiveData<TargetType>() {
    /**
     * A [MergerLiveData] with one source.
     *
     * @param source [LiveData] added as source
     * @param SourceType type of data hold by [source]
     * @param TargetType type of data hold by MergerLiveData
     * @param distinctUntilChanged if true [mapping] results equal to current value are dropped
     * @param mapping function used to determine the new value based on value of [source]
     */
    class One<SourceType, TargetType>(
        private val source: LiveData<SourceType>,
        private val distinctUntilChanged: Boolean = true,
        private val mapping: (SourceType) -> TargetType
    ) : MergerLiveData<TargetType>() {
        override fun onActive() {
            super.onActive()

            subscribeTo(
                source = source,
                distinctUntilChanged = distinctUntilChanged,
                mapping = mapping
            )
        }

        override fun onInactive() {
            removeSource(source)

            super.onInactive()
        }
    }

    /**
     * A [MergerLiveData] with two sources.
     *
     * @param firstSource [LiveData] added as first source
     * @param FirstSourceType type of data hold by [firstSource]
     * @param secondSource [LiveData] added as second source
     * @param SecondSourceType type of data hold by [secondSource]
     * @param TargetType type of data hold by MergerLiveData
     * @param distinctUntilChanged if true [merging] results equal to current value are dropped
     * @param merging function used to determine the new value based on source values
     */
    class Two<FirstSourceType, SecondSourceType, TargetType>(
        private val firstSource: LiveData<FirstSourceType>,
        private val secondSource: LiveData<SecondSourceType>,
        private val distinctUntilChanged: Boolean = true,
        private val merging: (FirstSourceType, SecondSourceType) -> TargetType
    ) : MediatorLiveData<TargetType>() {
        override fun onActive() {
            super.onActive()

            subscribeTo(
                firstSource = firstSource,
                secondSource = secondSource,
                distinctUntilChanged = distinctUntilChanged,
                merging = merging
            )
        }

        override fun onInactive() {
            removeSource(firstSource)
            removeSource(secondSource)

            super.onInactive()
        }
    }

    /**
     * A [MergerLiveData] with three sources.
     *
     * @param firstSource [LiveData] added as first source
     * @param FirstSourceType type of data hold by [firstSource]
     * @param secondSource [LiveData] added as second source
     * @param SecondSourceType type of data hold by [secondSource]
     * @param thirdSource [LiveData] added as third source
     * @param ThirdSourceType type of data hold by [thirdSource]
     * @param TargetType type of data hold by MergerLiveData
     * @param distinctUntilChanged if true [merging] results equal to current value are dropped
     * @param merging function used to determine the new value based source values
     */
    class Three<FirstSourceType, SecondSourceType, ThirdSourceType, TargetType>(
        private val firstSource: LiveData<FirstSourceType>,
        private val secondSource: LiveData<SecondSourceType>,
        private val thirdSource: LiveData<ThirdSourceType>,
        private val distinctUntilChanged: Boolean = true,
        private val merging: (FirstSourceType, SecondSourceType, ThirdSourceType) -> TargetType
    ) : MediatorLiveData<TargetType>() {
        override fun onActive() {
            super.onActive()

            subscribeTo(
                firstSource = firstSource,
                secondSource = secondSource,
                thirdSource = thirdSource,
                distinctUntilChanged = distinctUntilChanged,
                merging = merging
            )
        }

        override fun onInactive() {
            removeSource(firstSource)
            removeSource(secondSource)
            removeSource(thirdSource)

            super.onInactive()
        }
    }
}
