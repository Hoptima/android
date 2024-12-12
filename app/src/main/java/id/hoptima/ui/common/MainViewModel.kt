package id.hoptima.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hoptima.data.local.entity.PropertyEntity
import id.hoptima.data.repository.PropertyRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val propertyRepository: PropertyRepository) :
    ViewModel() {
    val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    var isChatGuideShown = false

    val recommendations = query.switchMap {
        propertyRepository.getRecommendations(it)
    }

    fun setQuery(query: String) {
        _query.value = query
    }

    fun getProperties() = propertyRepository.getProperties()

    fun getRecentProperties() = propertyRepository.getRecentProperties()

    fun getBookmarkedProperties() = propertyRepository.getBookmarkedProperties()

    fun getPropertyById(id: Int) = propertyRepository.getPropertyById(id)

    fun setPropertyBookmark(property: PropertyEntity, isBookmarked: Boolean) =
        viewModelScope.launch {
            propertyRepository.setPropertyBookmark(property, isBookmarked)
        }
}