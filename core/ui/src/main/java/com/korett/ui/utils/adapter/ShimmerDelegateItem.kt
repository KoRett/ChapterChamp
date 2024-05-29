package com.korett.ui.utils.adapter

class ShimmerDelegateItem(
    private val id: Int
) : DelegateItem {
    override fun content() = id

    override fun id() = id

    override fun compareToOther(other: DelegateItem): Boolean {
        return (other as ShimmerDelegateItem).content() == content()
    }
}