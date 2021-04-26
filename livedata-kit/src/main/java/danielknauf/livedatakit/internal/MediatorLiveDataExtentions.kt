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

package danielknauf.livedatakit.internal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * [MediatorLiveData] receiver listens to changes of [source]. New values are determined by
 * [mapping] [SourceType] to [TargetType] and posted asynchronously.
 *
 * @param source [LiveData] added as source
 * @param SourceType type of data hold by [source]
 * @param TargetType type of data hold by target
 * @param distinctUntilChanged if true [mapping] results equal to current target value are dropped
 * @param mapping function used to determine the new value based on value of [source]
 */
internal fun <SourceType, TargetType> MediatorLiveData<TargetType>.subscribeTo(
    source: LiveData<SourceType>,
    distinctUntilChanged: Boolean = true,
    mapping: (SourceType) -> TargetType
) {
    addSource(source) { value ->
        postValue(
            distinctUntilChanged = distinctUntilChanged,
            newValue = mapping(value)
        )
    }
}

/**
 * [MediatorLiveData] receiver listens to changes of [firstSource] and [secondSource]. New values
 * are determined by [merging] [FirstSourceType] and [SecondSourceType] to [TargetType] and are
 * posted asynchronously.
 *
 * @param firstSource [LiveData] added as first source
 * @param FirstSourceType type of data hold by [firstSource]
 * @param secondSource [LiveData] added as second source
 * @param SecondSourceType type of data hold by [secondSource]
 * @param TargetType type of data hold by target
 * @param distinctUntilChanged if true [merging] results equal to current target value are dropped
 * @param merging function used to determine the new value based on source values
 */
internal fun <FirstSourceType, SecondSourceType, TargetType> MediatorLiveData<TargetType>.subscribeTo(
    firstSource: LiveData<FirstSourceType>,
    secondSource: LiveData<SecondSourceType>,
    distinctUntilChanged: Boolean = true,
    merging: (FirstSourceType, SecondSourceType) -> TargetType
) {
    addSource(firstSource) { value ->
        val newValue =
            merging(
                value,
                secondSource.value ?: return@addSource
            )

        postValue(
            distinctUntilChanged = distinctUntilChanged,
            newValue = newValue
        )
    }

    addSource(secondSource) { value ->
        val newValue =
            merging(
                firstSource.value ?: return@addSource,
                value
            )

        postValue(
            distinctUntilChanged = distinctUntilChanged,
            newValue = newValue
        )
    }
}

/**
 * [MediatorLiveData] receiver listens to changes of [firstSource], [secondSource] and [thirdSource].
 * New values are determined by [merging] [FirstSourceType], [SecondSourceType] and [ThirdSourceType]
 * to [TargetType] and posted asynchronously.
 *
 * @param firstSource [LiveData] added as first source
 * @param FirstSourceType type of data hold by [firstSource]
 * @param secondSource [LiveData] added as second source
 * @param SecondSourceType type of data hold by [secondSource]
 * @param thirdSource [LiveData] added as third source
 * @param ThirdSourceType type of data hold by [thirdSource]
 * @param TargetType type of data hold by target
 * @param distinctUntilChanged if true [merging] results equal to current target value are dropped
 * @param merging function used to determine the new value based on source values
 */
internal fun <FirstSourceType, SecondSourceType, ThirdSourceType, TargetType> MediatorLiveData<TargetType>.subscribeTo(
    firstSource: LiveData<FirstSourceType>,
    secondSource: LiveData<SecondSourceType>,
    thirdSource: LiveData<ThirdSourceType>,
    distinctUntilChanged: Boolean = true,
    merging: (FirstSourceType, SecondSourceType, ThirdSourceType) -> TargetType
) {
    addSource(firstSource) { value ->
        val newValue =
            merging(
                value,
                secondSource.value ?: return@addSource,
                thirdSource.value ?: return@addSource
            )

        postValue(
            distinctUntilChanged = distinctUntilChanged,
            newValue = newValue
        )
    }

    addSource(secondSource) { value ->
        val newValue =
            merging(
                firstSource.value ?: return@addSource,
                value,
                thirdSource.value ?: return@addSource
            )

        postValue(
            distinctUntilChanged = distinctUntilChanged,
            newValue = newValue
        )
    }

    addSource(thirdSource) { value ->
        val newValue =
            merging(
                firstSource.value ?: return@addSource,
                secondSource.value ?: return@addSource,
                value
            )

        postValue(
            distinctUntilChanged = distinctUntilChanged,
            newValue = newValue
        )
    }
}

private fun <T> MediatorLiveData<T>.postValue(
    distinctUntilChanged: Boolean,
    newValue: T
) {
    val value = value ?: postValue(newValue)

    if (distinctUntilChanged && value == newValue) return

    postValue(newValue)
}
