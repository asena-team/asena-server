package com.syntax.asena.context

enum class PremiumType{
    LIMITED,
    PERMANENT;

    fun toInt(): Int = this.ordinal

}