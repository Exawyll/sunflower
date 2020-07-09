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

package com.google.samples.apps.sunflower

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ShareCompat
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.samples.apps.sunflower.adapters.HoleAdapter
import com.google.samples.apps.sunflower.data.Golf
import com.google.samples.apps.sunflower.databinding.FragmentGolfDetailBinding
import com.google.samples.apps.sunflower.utilities.InjectorUtils
import com.google.samples.apps.sunflower.viewmodels.GolfDetailViewModel


/**
 * A fragment representing a single Golf detail screen.
 */
class GolfDetailFragment : Fragment() {

    private val args: GolfDetailFragmentArgs by navArgs()

    private val golfDetailViewModel: GolfDetailViewModel by viewModels {
        InjectorUtils.provideGolfDetailViewModelFactory(requireActivity(), args.golfId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentGolfDetailBinding>(
            inflater, R.layout.fragment_golf_detail, container, false
        ).apply {
            viewModel = golfDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            callback = object : Callback {
                override fun add(golf: Golf?) {
//                    TODO("Not yet implemented")
                    val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
                    builder.setTitle("Score")

                    val input = EditText(requireActivity())
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.inputType = InputType.TYPE_CLASS_NUMBER
                    builder.setView(input)
                    var result:String = ""

                    // Set up the buttons
                    builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        result = input.text.toString()
                        Snackbar.make(root, "You entered the score : $result", Snackbar.LENGTH_LONG)
                                .show()
                    })
                    builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

                    builder.show()

                }
            }

            var isToolbarShown = false

            // scroll change listener begins at Y = 0 when image is fully collapsed
            golfDetailScrollview.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->

                    // User scrolled past image to height of toolbar and the title text is
                    // underneath the toolbar, so the toolbar should be shown.
                    val shouldShowToolbar = scrollY > toolbar.height

                    // The new state of the toolbar differs from the previous state; update
                    // appbar and toolbar attributes.
                    if (isToolbarShown != shouldShowToolbar) {
                        isToolbarShown = shouldShowToolbar

                        // Use shadow animator to add elevation if toolbar is shown
                        appbar.isActivated = shouldShowToolbar

                        // Show the plant name if toolbar is shown
                        toolbarLayout.isTitleEnabled = shouldShowToolbar
                    }
                }
            )

            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }

            toolbar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_share -> {
                        createShareIntent()
                        true
                    }
                    else -> false
                }
            }
        }

        val adapter = HoleAdapter()
        binding.holeList.adapter = adapter
        golfDetailViewModel.holes.observe(viewLifecycleOwner) { holes ->
            adapter.submitList(holes)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    // Helper function for calling a share functionality.
    // Should be used when user presses a share button/menu item.
    @Suppress("DEPRECATION")
    private fun createShareIntent() {
        val shareText = golfDetailViewModel.golf.value.let { golf ->
            if (golf == null) {
                ""
            } else {
                getString(R.string.share_text_plant, golf.name)
            }
        }
        val shareIntent = ShareCompat.IntentBuilder.from(activity)
            .setText(shareText)
            .setType("text/plain")
            .createChooserIntent()
            .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        startActivity(shareIntent)
    }

    // FloatingActionButtons anchored to AppBarLayouts have their visibility controlled by the scroll position.
    // We want to turn this behavior off to hide the FAB when it is clicked.
    //
    // This is adapted from Chris Banes' Stack Overflow answer: https://stackoverflow.com/a/41442923
    private fun hideAppBarFab(fab: FloatingActionButton) {
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = false
        fab.hide()
    }

    interface Callback {
        fun add(golf: Golf?)
    }
}
