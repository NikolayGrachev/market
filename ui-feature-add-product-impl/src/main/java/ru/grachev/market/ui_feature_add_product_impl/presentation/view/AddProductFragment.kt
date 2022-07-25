package ru.grachev.market.ui_feature_add_product_impl.presentation.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.LiveData
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import ru.grachev.market.core_utils.model.NetworkState
import ru.grachev.market.core_utils.presentation.fragment.BaseFragment
import ru.grachev.market.ui_feature_add_product_api.AddProductNavigationApi
import ru.grachev.market.ui_feature_add_product_impl.R
import ru.grachev.market.ui_feature_add_product_impl.databinding.FragmentAddProductBinding
import ru.grachev.market.ui_feature_add_product_impl.presentation.view_model.AddProductViewModel
import javax.inject.Inject


class AddProductFragment @Inject constructor(
    private val vm: AddProductViewModel,
    override val navigation: AddProductNavigationApi):
    BaseFragment<FragmentAddProductBinding>(FragmentAddProductBinding::inflate) {

    override val networkState: LiveData<NetworkState> = vm.networkState

    private var etName: TextInputEditText? = null
    private var etImage: TextInputEditText? = null
    private var etPrice: TextInputEditText? = null
    private var btnAddProduct: MaterialButton? = null

    private var isNameValid = false
        set(value) {
            field = value
            validate()
        }
    private var isImageValid = false
        set(value) {
            field = value
            validate()
        }

    private var isPriceValid = false
        set(value) {
            field = value
            validate()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etName = view.findViewById(R.id.etName)
        etImage = view.findViewById(R.id.etImage)
        etPrice = view.findViewById(R.id.etPrice)
        btnAddProduct = view.findViewById(R.id.btnAddProduct)

        addDefaultImage()

        addOnAddButtonClickListener()
        addTextWatchers()
        observeProductAdd()
    }

    private fun observeProductAdd() {
        vm.productCreated.observe(this.viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun addDefaultImage() {
        etImage?.setText("https://cdn0.ozone.ru/s3/multimedia-s/c1200/6065265220.jpg")
        isImageValid = true
    }

    private fun addOnAddButtonClickListener() {
        view?.findViewById<MaterialButton>(R.id.btnAddProduct)?.setOnClickListener {
            vm.createProduct(
                etName?.text.toString(),
                etImage?.text.toString(),
                etPrice?.text.toString(),
            )
        }
    }

    private fun addTextWatchers() {
        etName?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isNameValid = s?.length ?: 0 > 0
            }
        })

        etImage?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isImageValid = s?.length ?: 0 > 0
            }
        })

        etPrice?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isPriceValid = s?.length ?: 0 > 0
            }
        })
    }

    private fun validate() {
        val isValid = isNameValid && isImageValid && isPriceValid
        btnAddProduct?.isEnabled = isValid
    }
}
