package com.example.weather.common.extension

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.weather.common.appComponent

fun Fragment.showMessage(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.getNavController() = Navigation.findNavController(requireView())

fun Fragment.getViewModelFactory() = requireActivity().appComponent.factory