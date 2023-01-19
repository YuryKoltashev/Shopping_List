package com.example.shoppinglist.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var til_name: TextInputLayout
    private lateinit var til_count: TextInputLayout

    private lateinit var et_name: TextInputEditText
    private lateinit var et_count: TextInputEditText

    private lateinit var button_Save: Button

    private var modeType: String = MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParam()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews(view)
        addTextChangeListeners()
        setupModeType()
        observeViewModel()
    }

    private fun addTextChangeListeners() {
        et_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        et_count.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun observeViewModel() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.errorInputName)
            } else {
                null
            }
            til_name.error = message
        }

        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.errorInputCount)
            } else {
                null
            }
            til_count.error = message
        }

        viewModel.shouldFinishActivity.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }
    }

    private fun setupModeType() {
        when (modeType) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(viewLifecycleOwner) {
            et_name.setText(it.name)
            et_count.setText(it.count.toString())
        }
        button_Save.setOnClickListener {
            viewModel.editShopItem(et_name.text.toString(), et_count.text.toString())
        }
    }

    private fun launchAddMode() {
        button_Save.setOnClickListener {
            viewModel.addShopItem(et_name.text.toString(), et_count.text.toString())
        }
    }

    private fun parseParam() {
        val args = requireArguments()
        if (!args.containsKey(MODE_TYPE)) {
            throw RuntimeException("Mode type is absent")
        }
        val mode = args.getString(MODE_TYPE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown type of mode: $mode")
        }
        modeType = mode
        if (modeType == MODE_EDIT) {
            if (args.getInt(SHOP_ITEM_ID) == ShopItem.UNDEFINED_ID) {
                throw RuntimeException("ShopItem Id is absent")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun initViews(view: View) {
        with(view) {
            til_name = findViewById(R.id.til_name)
            til_count = findViewById(R.id.til_count)
            et_name = findViewById(R.id.et_name)
            et_count = findViewById(R.id.et_count)
            button_Save = findViewById(R.id.button_Save)
        }
    }

    companion object {

        private const val MODE_TYPE = "extra_mode_type"
        private const val SHOP_ITEM_ID = "extra_shopItem_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(MODE_TYPE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(MODE_TYPE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }
}
