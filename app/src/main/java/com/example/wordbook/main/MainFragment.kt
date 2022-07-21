package com.example.wordbook.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.example.wordbook.BaseActivity
import com.example.wordbook.R
import com.example.wordbook.databinding.FragmentMainBinding
import com.example.wordbook.study.StudyFragment
import com.example.wordbook.test.TestFragment
import com.example.wordbook.vocalist.VocaListBaseFragment
import com.example.wordbook.vocalist.VocaListFragment
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding
    private lateinit var supportFragmentManager: FragmentManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.mMoveToStudy.observe(viewLifecycleOwner) {
            if (it) {
                lifecycleScope.launch {
                    if (viewModel.moveToStudyEnabled()) {
                        addStudyFragment()
                        viewModel.moveToStudyDone()
                    } else {
                        Toast.makeText(context, R.string.error_limit_1_word, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        viewModel.mMoveToTest.observe(viewLifecycleOwner) {
            if (it) {
                lifecycleScope.launch {
                    if (viewModel.moveToTestEnabled()) {
                        addTestFragment()
                        viewModel.moveToTestDone()
                    } else {
                        Toast.makeText(context, R.string.error_limit_4_word, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        viewModel.mMoveToVocaList.observe(viewLifecycleOwner) {
            if (it) {
                addVocaListFragment()
                viewModel.moveToVocaListDone()
            }
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        supportFragmentManager = requireActivity().supportFragmentManager
    }

    private fun addVocaListFragment() {
        supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(BaseActivity.FRAGMENT_CONTAINER_ID, VocaListBaseFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    private fun addTestFragment() {
        supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(BaseActivity.FRAGMENT_CONTAINER_ID, TestFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    private fun addStudyFragment() {
        supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(BaseActivity.FRAGMENT_CONTAINER_ID, StudyFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }
}