package com.korett.ui.utils.adapter

interface DelegateItem {
    fun id(): Int
    fun content(): Any
    fun compareToOther(other: DelegateItem): Boolean
}