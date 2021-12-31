package com.eateasily.codewars.ui.userdetails.authoredchallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eateasily.codewars.databinding.FragmentAuthoredChallengeBinding
import dagger.hilt.android.AndroidEntryPoint


class AuthoredChallengeFragment : Fragment() {

    private var _binding: FragmentAuthoredChallengeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            userName = it.getString("userName")!!
//
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAuthoredChallengeBinding.inflate(inflater, container, false)
        return binding.root
    }



}