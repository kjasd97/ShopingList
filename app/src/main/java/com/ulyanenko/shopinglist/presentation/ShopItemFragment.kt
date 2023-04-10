package com.ulyanenko.shopinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.ulyanenko.shopinglist.R

class ShopItemFragment : Fragment() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonSave: Button

    private var screeMode: String = ""
    private var shopItemId: Int = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity doesn't implement interface OnEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
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

        viewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)

        initViews(view)

        etNameListener()

        etCountListener()

        chooseScreenMode()

        observeFromViewModel()
    }


    private fun observeFromViewModel() {
        viewModel.inputCountError.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.errorCount)
            } else {
                null
            }
            tilCount.error = message
        }

        viewModel.inputNameError.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.errorName)
            } else {
                null
            }
            tilName.error = message
        }

        viewModel.canClose.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingListener()
        }
    }

    private fun chooseScreenMode() {
        when (screeMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun etCountListener() {
        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetInputCountError()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun etNameListener() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetInputNameError()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        buttonSave.setOnClickListener {
            viewModel.editShopItem(etName.text?.toString(), etCount.text?.toString())
        }
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            viewModel.addShopItem(etName.text?.toString(), etCount.text?.toString())
        }
    }

    private fun parseParams() {

        val args = requireArguments()

        if (!args.containsKey(EXTRA_SCREEN_MOD)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(EXTRA_SCREEN_MOD)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Param screen mode is wrong")
        }
        screeMode = mode
        if (screeMode == MODE_EDIT) {
            if (!args.containsKey(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = args.getInt(EXTRA_SHOP_ITEM_ID, -1)
        }
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        buttonSave = view.findViewById(R.id.save_button)
    }

    interface OnEditingFinishedListener {
        fun onEditingListener()
    }

    companion object {
        private const val EXTRA_SCREEN_MOD = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"

        fun newFragmentAdd(): ShopItemFragment {
            val args = Bundle()
            args.putString(EXTRA_SCREEN_MOD, MODE_ADD)
            val fragment = ShopItemFragment()
            fragment.arguments = args
            return fragment
        }

        fun newFragmentEdit(id: Int): ShopItemFragment {
            val args = Bundle()
            args.putInt(EXTRA_SHOP_ITEM_ID, id)
            args.putString(EXTRA_SCREEN_MOD, MODE_EDIT)
            val fragment = ShopItemFragment()
            fragment.arguments = args
            return fragment
        }

    }

}