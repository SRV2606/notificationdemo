package com.example.basehelpers.base

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class RoundedBottomSheetBindingDialog<B : ViewDataBinding> :
  BottomSheetDialogFragment() {
  private var _binding: B? = null
  private val DIALOG_DIM_AMOUNT = 0.55f

  protected val binding by lazy { _binding!! }

  override fun getTheme(): Int = com.google.android.material.R.style.Widget_Material3_BottomSheet

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
    BottomSheetDialog(requireContext(), theme).apply {
      window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    readArguments(arguments)
  }

  abstract fun createBinding(inflater: LayoutInflater): B
  abstract fun readArguments(extras: Bundle?)
  abstract fun setupUi()
  abstract fun observeData()
  abstract fun setListener()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = createBinding(inflater)
    return binding.root
  }

  protected fun adjustBottomSheetHeight(rootViewGroup: ViewGroup) {
    dialog?.let { dialog ->
      dialog.window?.statusBarColor = Color.TRANSPARENT
      rootViewGroup.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
      dialog.window?.setDimAmount(DIALOG_DIM_AMOUNT)
      view?.let { view ->
        view.post {
          val parent = view.parent as View
          val params = parent.layoutParams as CoordinatorLayout.LayoutParams
          val bottomSheetBehavior = params.behavior as BottomSheetBehavior<*>
          bottomSheetBehavior.setPeekHeight(
            view.measuredHeight + 100,
            true
          )
        }
      }
      dialog.setOnDismissListener {
        dismissAllowingStateLoss()
      }
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupUi()
    setListener()
    observeData()
  }
}
