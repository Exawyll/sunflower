/*
 * Copyright 2018 Google LLC
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

package com.google.samples.apps.sunflower.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.samples.apps.sunflower.HomeViewPagerFragmentDirections
import com.google.samples.apps.sunflower.GolfListFragment
import com.google.samples.apps.sunflower.data.Golf
import com.google.samples.apps.sunflower.databinding.ListItemGolfBinding

/**
 * Adapter for the [RecyclerView] in [GolfListFragment].
 */
class GolfAdapter : ListAdapter<Golf, RecyclerView.ViewHolder>(GolfDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GolfViewHolder(ListItemGolfBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val golf = getItem(position)
        (holder as GolfViewHolder).bind(golf)
    }

    class GolfViewHolder(
        private val binding: ListItemGolfBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.golf?.let { golf ->
                    navigateToGolf(golf, it)
                }
            }
        }

        private fun navigateToGolf(
            golf: Golf,
            view: View
        ) {
            val direction =
                HomeViewPagerFragmentDirections.actionViewPagerFragmentToGolfDetailFragment(
                    golf.golfId
                )
            view.findNavController().navigate(direction)
        }

        fun bind(item: Golf) {
            binding.apply {
                golf = item
                executePendingBindings()
            }
        }
    }
}

private class GolfDiffCallback : DiffUtil.ItemCallback<Golf>() {

    override fun areItemsTheSame(oldItem: Golf, newItem: Golf): Boolean {
        return oldItem.golfId == newItem.golfId
    }

    override fun areContentsTheSame(oldItem: Golf, newItem: Golf): Boolean {
        return oldItem == newItem
    }
}