package com.chemin.lumappstest.utils

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import kotlinx.coroutines.CoroutineDispatcher
import kotlin.coroutines.CoroutineContext

suspend fun <T : Any> PagingData<T>.toList(context: CoroutineContext): List<T> {
    val differ = AsyncPagingDataDiffer(
        diffCallback = object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
                oldItem == newItem
        },
        updateCallback = object : ListUpdateCallback {
            override fun onInserted(position: Int, count: Int) = Unit
            override fun onRemoved(position: Int, count: Int) = Unit
            override fun onMoved(fromPosition: Int, toPosition: Int) = Unit
            override fun onChanged(position: Int, count: Int, payload: Any?) = Unit
        },
        mainDispatcher = context,
        workerDispatcher = context,
    )
    differ.submitData(this)
    return differ.snapshot().items
}

