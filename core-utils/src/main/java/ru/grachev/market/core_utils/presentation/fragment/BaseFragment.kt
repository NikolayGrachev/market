package ru.grachev.market.core_utils.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import ru.grachev.market.core_utils.model.NetworkState
import ru.grachev.market.core_utils.navigate.BaseNavigationApi
import androidx.viewbinding.ViewBinding

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB: ViewBinding>(private val inflate: Inflate<VB>): Fragment() {
    abstract val networkState: LiveData<NetworkState>?
    abstract val navigation: BaseNavigationApi

    private var _binding: VB? = null
    val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNetworkState()
        setOnBackPressedDispatcher()
    }

    override fun onPause() {
        navigation.resetComponent(this)
        super.onPause()
    }

    private fun setOnBackPressedDispatcher() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            isEnabled = false
            navigation.navigateBack(this@BaseFragment)
        }
    }

    private fun observeNetworkState() {
        networkState?.observe(this.viewLifecycleOwner) { state ->
            val tvNetworkState = requireActivity()
                .findViewById<TextView>(dev.grachev.core_resources.R.id.tvNetworkState)

            when (state) {
                NetworkState.Connected -> {
                    tvNetworkState?.isVisible = false
                }
                NetworkState.Disconnected -> {
                    tvNetworkState?.isVisible = true
                }
            }
        }
    }
}