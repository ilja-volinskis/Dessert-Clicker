package com.example.dessertclicker.ui

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource.dessertList
import com.example.dessertclicker.data.DessertUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DessertUiState())
    val uiState: StateFlow<DessertUiState> = _uiState.asStateFlow()

    fun onDessertClicked() {
        _uiState.update { inUiState ->
            val nextIndex = getNextDessertIndex(inUiState.dessertsSold)

            inUiState.copy(
                revenue = inUiState.revenue + inUiState.currentDessertPrice,
                dessertsSold = inUiState.dessertsSold + 1,
                currentDessertIndex = nextIndex,

                currentDessertPrice = dessertList[nextIndex].price,
                currentDessertImageId = dessertList[nextIndex].imageId
            )
        }
    }

    private fun getNextDessertIndex(totalSold: Int): Int {
        var dessertIndex = 0
        for (index in dessertList.indices) {
            if (totalSold >= dessertList[index].startProductionAmount) {
                dessertIndex = index
            } else {
                // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
                // you'll start producing more expensive desserts as determined by startProductionAmount
                // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
                // than the amount sold.
                break
            }
        }

        return dessertIndex
    }
}