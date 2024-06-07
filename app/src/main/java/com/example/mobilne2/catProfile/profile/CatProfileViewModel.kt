package com.example.mobilne2.catProfile.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilne2.catProfile.mapper.asCatUiModel
import com.example.mobilne2.catProfile.profile.CatProfileState
import com.example.mobilne2.catProfile.repository.CatProfileRepository
import com.example.mobilne2.navigation.catId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CatProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CatProfileRepository,
) : ViewModel() {

    private val catId: String = savedStateHandle.catId
    private val _state = MutableStateFlow(CatProfileState())
    val state = _state.asStateFlow()
    private fun setState(reducer: CatProfileState.() -> CatProfileState) =
        _state.getAndUpdate(reducer)

    init {
        fetchCat()
    }
    private fun fetchCat() {
        viewModelScope.launch {
            setState { copy(fetching = true) }
            try {
                val catt = withContext(Dispatchers.IO) {repository.getCats(catId)}
                //setState { copy(catId = catt.id )}
                setState { copy(cat = catt.asCatUiModel()) }

//                val image = withContext(Dispatchers.IO) {
//                    repository.fetchCatImages(catt.reference_image_id)
               // setState { copy(image = image.url ) }
            } catch (error: Exception) {
                setState { copy(catId = "") }
                setState { copy(error = CatProfileState.DetailsError.DataUpdateFailed(cause = error)) }
            } finally {
                setState { copy(fetching = false) }
            }
        }
    }


}