package com.neonusa.tanciku.presentation.iap

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.google.common.collect.ImmutableList
import com.neonusa.tanciku.ui.theme.TancikuTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun IAPScreen() {
    var billingClient = BillingClient.newBuilder(LocalContext.current)
        .setListener(purchasesUpdatedListener)
        .enablePendingPurchases()
        // Configure other settings.
        .build()

    // Current Purchases
    val _purchases =
        MutableStateFlow<List<Purchase>>(listOf())
    val purchases = _purchases.asStateFlow()


    billingClient.startConnection(object : BillingClientStateListener {
        override fun onBillingSetupFinished(billingResult: BillingResult) {
            if (billingResult.responseCode ==  BillingClient.BillingResponseCode.OK) {
                // pastikan sudah login ke google play
                Log.d("Test", "onBillingSetupFinished: The BillingClient is ready. You can query purchases here.")
                val queryProductDetailsParams =
                    QueryProductDetailsParams.newBuilder()
                        .setProductList(
                            ImmutableList.of(
                                QueryProductDetailsParams.Product.newBuilder()
                                    .setProductId("tanciku_plus_yearly")
                                    .setProductType(BillingClient.ProductType.SUBS)
                                    .build()))
                        .build()

                billingClient.queryProductDetailsAsync(queryProductDetailsParams) {
                        billingResult,
                        productDetailsList ->
                    // check billingResult
                    // process returned productDetailsList
                    Log.d("Test", "IAPScreen: $productDetailsList")
                    Log.d("Test", "billingResult: $billingResult")
                    Log.d("Test", "billingResult responseCode: ${billingResult.responseCode}")
                }
            }
        }
        override fun onBillingServiceDisconnected() {
            Log.d("Test", "onBillingSetupFinished: Try to restart the connection on the next request to.")
            Log.d("Test", "onBillingSetupFinished: Google Play by calling the startConnection() method.")

        }
    })

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center,
            text = "Status")
        Button(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            onClick = {}){
            Text(text = "Berlangganan")
        }

        Button(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            onClick = {}){
            Text(text = "Consumable")
        }
    }
}

val purchasesUpdatedListener =
    PurchasesUpdatedListener { billingResult, purchases ->
        // To be implemented in a later section.
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            // jika berhasil
            Log.d("Test", "OK")
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.BILLING_UNAVAILABLE && purchases != null) {
            Log.d("Test", "BILLING_UNAVAILABLE")
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED && purchases != null) {
            Log.d("Test", "ITEM_ALREADY_OWNED")
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.DEVELOPER_ERROR && purchases != null) {
            Log.d("Test", "DEVELOPER_ERROR")
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.NETWORK_ERROR && purchases != null) {
            Log.d("Test", "NETWORK_ERROR")
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.SERVICE_DISCONNECTED && purchases != null) {
            Log.d("Test", "SERVICE_DISCONNECTED")
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED && purchases != null) {
            Log.d("Test", "FEATURE_NOT_SUPPORTED")
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED && purchases != null) {
            Log.d("Test", "USER_CANCELED")
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.ITEM_NOT_OWNED && purchases != null) {
            Log.d("Test", "ITEM_NOT_OWNED")
        } else {
            Log.d("Test", billingResult.debugMessage)
        }
    }

// Function to query product details
private fun queryProductDetails(billingClient: BillingClient) {
    val queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
        .setProductList(
            listOf(
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId("tanciku_plus_yearly")
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build()
            )
        )
        .build()

    billingClient.queryProductDetailsAsync(queryProductDetailsParams) { billingResult, productDetailsList ->
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && productDetailsList.isNotEmpty()) {
            Log.d("IAPScreen", "Product details retrieved: $productDetailsList")
        } else {
            Log.d("IAPScreen", "Failed to retrieve product details. Code: ${billingResult.responseCode}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IAPScreenPreview(){
    TancikuTheme {
        IAPScreen()
    }
}