package jp.cordea.libros.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import jp.cordea.libros.BR
import jp.cordea.libros.viewmodel.MainListItemViewModel

/**
 * Created by Yoshihiro Tanaka on 2016/12/31.
 */
class MainListAdapter : BaseAdapter {

    constructor(context: Context, layout: Int, items: List<MainListItemViewModel> = emptyList())
            : super() {
        this.context = context
        this.layout = layout
        this.items = items
    }

    val context: Context

    val layout: Int

    var items: List<MainListItemViewModel>
        get
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItem(position: Int): MainListItemViewModel {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val binding: ViewDataBinding
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), layout, parent, false)
            view = binding.root
        } else {
            view = convertView
            binding = DataBindingUtil.getBinding(view)
        }

        binding.setVariable(BR.vm, getItem(position))

        return view
    }
}