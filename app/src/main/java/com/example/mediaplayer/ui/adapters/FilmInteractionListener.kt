package com.example.mediaplayer.ui.adapters

import com.example.mediaplayer.model.response.Item


interface FilmInteractionListener {
 fun onClickItem(film: Item)
}
