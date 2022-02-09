package com.example.basehelpers.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseBindingFragment<B : ViewDataBinding> : Fragment() {
    private var _binding: B? = null

    protected val binding by lazy { _binding!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readArguments(arguments)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, getLayoutResource(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setListener()
        setupUi()
    }

    @LayoutRes
    abstract fun getLayoutResource(): Int
    abstract fun readArguments(extras: Bundle?)
    abstract fun setupUi()
    abstract fun observeData()
    abstract fun setListener()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun showToast(textToShow: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), textToShow, length).show()
    }
}
