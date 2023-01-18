package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

//    private lateinit var viewModel: ShopItemViewModel
//
//    private lateinit var til_name: TextInputLayout
//    private lateinit var til_count: TextInputLayout
//
//    private lateinit var et_name: TextInputEditText
//    private lateinit var et_count: TextInputEditText
//
//    private lateinit var button_Save: Button
//
//    private var modeType = MODE_UNKNOWN
//    private var shopItemId = ShopItem.UNDEFINED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
//        parseIntent()
//        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
//        initViews()
//        addTextChangeListeners()
//        setupModeType()
//        observeViewModel()
    }

//    private fun addTextChangeListeners() {
//        et_name.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                viewModel.resetErrorInputName()
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//            }
//        })
//
//        et_count.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                viewModel.resetErrorInputCount()
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//            }
//        })
//    }
//
//    private fun observeViewModel() {
//        viewModel.errorInputName.observe(this) {
//            val message = if (it) {
//                getString(R.string.errorInputName)
//            } else {
//                null
//            }
//            til_name.error = message
//        }
//
//        viewModel.errorInputCount.observe(this) {
//            val message = if (it) {
//                getString(R.string.errorInputCount)
//            } else {
//                null
//            }
//            til_count.error = message
//        }
//
//        viewModel.shouldFinishActivity.observe(this) {
//            finish()
//        }
//    }
//
//    private fun setupModeType() {
//        when (modeType) {
//            MODE_ADD -> launchAddMode()
//            MODE_EDIT -> launchEditMode()
//        }
//    }
//
//    private fun launchEditMode() {
//        viewModel.getShopItem(shopItemId)
//        viewModel.shopItem.observe(this) {
//            et_name.setText(it.name)
//            et_count.setText(it.count.toString())
//        }
//        button_Save.setOnClickListener {
//            viewModel.editShopItem(et_name.text.toString(), et_count.text.toString())
//        }
//    }
//
//    private fun launchAddMode() {
//        button_Save.setOnClickListener {
//            viewModel.addShopItem(et_name.text.toString(), et_count.text.toString())
//        }
//    }
//
//    private fun parseIntent() {
//        if (!intent.hasExtra(EXTRA_MODE_TYPE)) {
//            throw RuntimeException("Mode type is absent")
//        }
//        val modeFromIntent = intent.getStringExtra(EXTRA_MODE_TYPE)
//        if (modeFromIntent != MODE_ADD && modeFromIntent != MODE_EDIT) {
//            throw RuntimeException("Unknown type of mode: $modeFromIntent")
//        }
//        modeType = modeFromIntent
//        if (modeType == MODE_EDIT) {
//            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
//                throw RuntimeException("ShopItem Id is absent")
//            }
//            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
//        }
//    }
//
//    private fun initViews() {
//        til_name = findViewById(R.id.til_name)
//        til_count = findViewById(R.id.til_count)
//        et_name = findViewById(R.id.et_name)
//        et_count = findViewById(R.id.et_count)
//        button_Save = findViewById(R.id.button_Save)
//    }

    companion object {

        private const val EXTRA_MODE_TYPE = "extra_mode_type"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shopItem_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE_TYPE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE_TYPE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }
}