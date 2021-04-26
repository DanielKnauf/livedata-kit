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

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData

@MainThread
fun <T> MutableLiveData<List<T>>.add(
    element: T,
    index: Int = UNSPECIFIED_INDEX
) {
    val list = value?.toMutableList() ?: mutableListOf()

    val isInvalidIndex = !list.validateIndex(index)
    if (isInvalidIndex) {
        list.add(element)

        value = list

        return
    }

    list.add(
        index = index,
        element = element
    )

    value = list
}

@MainThread
fun <T> MutableLiveData<List<T>>.addAll(
    vararg elements: T,
    index: Int = UNSPECIFIED_INDEX
) {
    val list = value?.toMutableList() ?: mutableListOf()

    val isInvalidIndex = !list.validateIndex(index)
    if (isInvalidIndex) {
        list.addAll(elements)

        value = list

        return
    }

    list.addAll(
        index = index,
        elements = elements.toList()
    )

    value = list
}

@MainThread
fun <T> MutableLiveData<List<T>>.addAll(
    elements: List<T>,
    index: Int = UNSPECIFIED_INDEX
) {
    val list = value?.toMutableList() ?: mutableListOf()

    val isInvalidIndex = !list.validateIndex(index)
    if (isInvalidIndex) {
        list.addAll(elements)

        value = list

        return
    }

    list.addAll(
        index = index,
        elements = elements
    )

    value = list
}

@MainThread
fun <T> MutableLiveData<List<T>>.remove(index: Int) {
    val list = value?.toMutableList() ?: return

    val isInvalidIndex = !list.validateIndex(index)
    if (isInvalidIndex) return

    list.removeAt(index)

    value = list
}

@MainThread
fun <T> MutableLiveData<List<T>>.clear() {
    value = emptyList()
}

@MainThread
fun <T> MutableLiveData<List<T>>.forEachIndexed(
    action: (index: Int, item: T) -> Unit
) {
    value?.forEachIndexed(action)
}

@MainThread
fun <T> MutableLiveData<List<T>>.isEmpty(): Boolean = value?.isEmpty() ?: true

@MainThread
fun <T> MutableLiveData<List<T>>.lastIndex(): Int =
    if (isEmpty()) DEFAULT_LAST_INDEX else value?.lastIndex ?: DEFAULT_LAST_INDEX

private const val UNSPECIFIED_INDEX = -1
private const val DEFAULT_LAST_INDEX = 0

private fun Collection<*>.validateIndex(index: Int): Boolean = index in this.indices
