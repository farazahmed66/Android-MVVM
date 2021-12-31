package com.eateasily.codewars.ui.userdetails.completedchallenge

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eateasily.codewars.databinding.NetworkStateItemBinding


class CompletedLoadingStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<CompletedLoadingStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

        if (loadState is LoadState.Loading) {
            holder.binding.progressBarItem.visibility = View.VISIBLE
            holder.binding.retyBtn.visibility = View.GONE
            holder.binding.errorMsgItem.visibility = View.GONE

        } else {
            holder.binding.progressBarItem.visibility = View.GONE
        }

        if (loadState is LoadState.Error) {
            holder.binding.retyBtn.visibility = View.VISIBLE
            holder.binding.errorMsgItem.visibility = View.VISIBLE
            holder.binding.errorMsgItem.text = loadState.error.localizedMessage
        }

        holder.binding.retyBtn.setOnClickListener {
            retry.invoke()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            NetworkStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    inner class LoadStateViewHolder(val binding: NetworkStateItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}