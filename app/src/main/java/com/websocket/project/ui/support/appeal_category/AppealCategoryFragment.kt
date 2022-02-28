package com.websocket.project.ui.support.appeal_category

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.websocket.project.databinding.FragmentAppealCategoryBinding
import com.websocket.project.ui.base.BaseFragment
import com.websocket.project.ui.main.MainActivity
import com.websocket.project.ui.support.SupportFragment.Companion.APPEAL_RESULT_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppealCategoryFragment : BaseFragment<FragmentAppealCategoryBinding>(),
    AppealCategoryAdapter.AppealCategoryActionListener, AppealCategoryActionListener {

    private var currentCheckedPosition = -1
    private val appealCategoryAdapter by lazy(LazyThreadSafetyMode.NONE) {
        AppealCategoryAdapter(this, currentCheckedPosition)
    }

    override fun initViewBinding(): FragmentAppealCategoryBinding =
        FragmentAppealCategoryBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: AppealCategoryFragmentArgs by navArgs()
        currentCheckedPosition = args.position
        with(binding) {
            appealCategoryActionListenerBinding = this@AppealCategoryFragment
            appealCategoryRecycler.adapter = appealCategoryAdapter
        }
    }

    override fun onAppealCategorySelected(appealCategory: AppealCategory) {
        val appealCategoryPosition = AppealCategory.valueOf(appealCategory.name).ordinal
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            APPEAL_RESULT_KEY,
            appealCategoryPosition
        )
        currentCheckedPosition = appealCategoryPosition
        appealCategoryAdapter.onAppealCategorySelected(appealCategoryPosition)
    }

    override fun onAppealCategoryBackBtnClicked() {
        (activity as MainActivity).onBackPressed()
    }
}