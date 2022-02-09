package com.example.basehelpers.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class BaseBindingDialogFragment<B : ViewDataBinding> :
  DialogFragment() {

  private var _binding: B? = null

  protected val binding by lazy { _binding!! }

  override fun getTheme(): Int =
    com.google.android.material.R.style.Base_Theme_Material3_Dark_BottomSheetDialog

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    readArguments(arguments)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = createBinding(inflater)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    observeData()
    setListener()
    setupUi()
  }

  abstract fun createBinding(inflater: LayoutInflater): B
  abstract fun readArguments(extras: Bundle?)
  abstract fun setupUi()
  abstract fun observeData()
  abstract fun setListener()

  override fun onDetach() {
    super.onDetach()
    _binding = null
  }
}
