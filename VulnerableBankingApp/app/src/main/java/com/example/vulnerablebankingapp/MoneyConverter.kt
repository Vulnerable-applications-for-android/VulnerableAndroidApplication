package com.example.vulnerablebankingapp

class MoneyConverter {

    companion object {
        fun penniesToPounds(amount: Int): Float {
            return  amount.toFloat() / 100
        }

        fun poundsToPennies(amount: Float): Int {
            return (amount * 100).toInt()
        }
    }
}