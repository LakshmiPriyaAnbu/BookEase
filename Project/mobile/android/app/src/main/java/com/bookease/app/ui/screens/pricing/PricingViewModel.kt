package com.bookease.app.ui.screens.pricing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

enum class BillingCycle { MONTHLY, YEARLY }

class PricingViewModel : ViewModel() {

    var billingCycle: BillingCycle by mutableStateOf(BillingCycle.MONTHLY)
        private set

    fun toggleBillingCycle() {
        billingCycle = when (billingCycle) {
            BillingCycle.MONTHLY -> BillingCycle.YEARLY
            BillingCycle.YEARLY  -> BillingCycle.MONTHLY
        }
    }
}
