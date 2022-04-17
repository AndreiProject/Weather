package com.example.weather.days.dialogsearch

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.*
import com.example.weather.R
import com.example.weather.databinding.FragmentSearchDialogBinding

private val TAG = DialogSearchFragment::class.simpleName

class DialogSearchFragment : DialogFragment() {
    private var _binding: FragmentSearchDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentSearchDialogBinding.inflate(layoutInflater)
        val dialog = buildFragmentDialog()

        dialog.setOnShowListener {
            val button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            button.setOnClickListener { onClick(dialog, binding) }
        }
        addOnTextChanged()
        return dialog
    }

    private fun buildFragmentDialog() = AlertDialog
        .Builder(requireContext())
        .setTitle(getString(R.string.fragment_search_dialog_title))
        .setMessage(getString(R.string.fragment_search_dialog_title_input))
        .setView(binding.root)
        .setPositiveButton(getString(R.string.fragment_search_dialog_positive_button)) { _, _ -> }
        .setNegativeButton(getString(R.string.fragment_search_dialog_negative_button)) { _, _ -> }
        .create()

    private fun onClick(dialog: AlertDialog, binding: FragmentSearchDialogBinding) = with(binding) {
        textTown.text?.let {
            if (it.isNotBlank()) {
                sendResultParentFragment(it.toString())
                dialog.dismiss()
            } else {
                textTownLayout.error = getString(R.string.fragment_search_dialog_text_warning)
            }
        }
    }

    private fun sendResultParentFragment(result: String) {
        setFragmentResult(REQUEST_KEY, Bundle().apply {
            putString(TOWN_KEY, result)
        })
    }

    private fun addOnTextChanged() = with(binding) {
        textTown.doOnTextChanged { _, _, _, _ ->
            if (textTownLayout.isErrorEnabled) {
                textTownLayout.isErrorEnabled = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val REQUEST_KEY = "dialog.search.fragment.request.key"
        const val TOWN_KEY = "dialog.search.fragment.town.key"

        fun showDialog(root: Fragment) {
            DialogSearchFragment().show(root.childFragmentManager, TAG)
        }
    }
}