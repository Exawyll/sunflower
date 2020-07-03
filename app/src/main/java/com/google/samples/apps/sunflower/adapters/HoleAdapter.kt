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
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.samples.apps.sunflower.data.Hole
import com.google.samples.apps.sunflower.databinding.ListItemHoleBinding

/**
 * Adapter for the [RecyclerView] in [HoleListFragment].
 */
class HoleAdapter : ListAdapter<Hole, RecyclerView.ViewHolder>(HoleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HoleViewHolder(ListItemHoleBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val hole = getItem(position)
//        (holder as HoleViewHolder).bind(hole)
    }

    class HoleViewHolder(
        private val binding: ListItemHoleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.hole?.let { hole ->
                    navigateToHole(hole, it)
                }
            }
        }

        private fun navigateToHole(
            hole: Hole,
            view: View
        ) {
//            val direction =
//                HomeViewPagerFragmentDirections.actionViewPagerFragmentToHoleDetailFragment(
//                    hole.holeId
//                )
//            view.findNavController().navigate(direction)
        }

//        fun bind(item: Hole) {
//            binding.apply {
//                hole = item
//                executePendingBindings()
//            }
//        }
    }
}

private class HoleDiffCallback : DiffUtil.ItemCallback<Hole>() {

    override fun areItemsTheSame(oldItem: Hole, newItem: Hole): Boolean {
        return oldItem.holeId == newItem.holeId
    }

    override fun areContentsTheSame(oldItem: Hole, newItem: Hole): Boolean {
        return oldItem == newItem
    }
}