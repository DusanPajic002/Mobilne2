package com.example.mobilne2.catProfile

import com.example.mobilne2.catProfile.profile.model.CatProfileUI

data class CatProfileState (
    val catId: String,
    val fetching: Boolean = false,
    val cat: CatProfileUI? = null,
    val image: String? = null,
    val error: DetailsError? = null,
) {
    sealed class DetailsError {
        data class DataUpdateFailed(val cause: Throwable? = null) : DetailsError()
    }
}
