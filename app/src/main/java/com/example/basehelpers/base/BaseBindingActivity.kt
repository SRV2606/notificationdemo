package com.example.basehelpers.base

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.basehelpers.R
import com.jaeger.library.StatusBarUtil

abstract class BaseBindingActivity<B : ViewDataBinding> : AppCompatActivity() {

  private var _binding: B? = null

  protected val binding by lazy { _binding!! }
  private val noPadding = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = DataBindingUtil.setContentView(this, getLayoutResource())
    readArguments(intent)
    observeData()
    setListener()
    setupUi()
  }

  fun setStatusBarColor(@ColorRes color: Int) {
    StatusBarUtil.setColorNoTranslucent(
      this,
      ContextCompat.getColor(this, color)
    )
  }

  fun setStatusBarIcons(shouldChangeStatusBarTintToDark: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      val decor: View = window.decorView
      if (shouldChangeStatusBarTintToDark) {
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
      } else {
        decor.systemUiVisibility = 0
      }
    }
  }

  fun clearStatusBarIconsColor() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      val decor: View = window.decorView
      decor.systemUiVisibility = decor.systemUiVisibility xor
              View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
  }

  @LayoutRes
  abstract fun getLayoutResource(): Int
  abstract fun readArguments(extras: Intent)
  abstract fun setupUi()
  abstract fun observeData()
  abstract fun setListener()

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }

  fun isDarkTheme(): Boolean {
    return this.resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
  }

  fun showErrorToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
  }

  fun setNavigationBarColor(@ColorRes color: Int) {
    window?.navigationBarColor = ContextCompat.getColor(this, color)
  }

  fun setLightStatusBarColor(@ColorRes color: Int = R.color.white) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
      window.statusBarColor = ContextCompat.getColor(this, color)
    } else {
      setStatusBarColor(color)
    }
    setDarkNavIcons()
  }

  fun setDarkStatusBarColor(@ColorRes color: Int = R.color.black) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      window.statusBarColor = ContextCompat.getColor(this, color)
    } else {
      setStatusBarColor(color)
    }
    setDarkNavIcons()
  }

  fun setWindowInsets(rootLayout: View) {
    rootLayout.setOnApplyWindowInsetsListener { view, insets ->
      view.setPadding(
        noPadding,
        insets.systemWindowInsetTop,
        noPadding,
        insets.systemWindowInsetBottom
      )
      insets
    }
  }

  fun hideNavBar() {
    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_FULLSCREEN
//                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            )
  }

  fun setOnlyDarkNavIcons() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      window.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    }
  }

  fun setDarkNavIcons() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      window.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
  }

}
