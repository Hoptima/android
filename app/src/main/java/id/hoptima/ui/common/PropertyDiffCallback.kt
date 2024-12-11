package id.hoptima.ui.common

import androidx.recyclerview.widget.DiffUtil
import id.hoptima.data.local.entity.PropertyEntity

object PropertyDiffCallback : DiffUtil.ItemCallback<PropertyEntity>() {
    override fun areItemsTheSame(
        oldItem: PropertyEntity,
        newItem: PropertyEntity
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: PropertyEntity,
        newItem: PropertyEntity
    ): Boolean {
        return oldItem == newItem
    }
}