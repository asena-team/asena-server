package com.syntax.asena.context

enum class PremiumStatus{
    CONTINUES,
    FINISHED,
    CANCELED;

    fun toInt(): Int = this.ordinal

}