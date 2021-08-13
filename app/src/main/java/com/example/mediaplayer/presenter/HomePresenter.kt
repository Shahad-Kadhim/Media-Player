package com.example.mediaplayer.presenter

import com.example.mediaplayer.model.repository.Repository
import com.example.mediaplayer.ui.fragments.IHomeView

class HomePresenter(private val view: IHomeView) {

    fun getFilm() {
        view.onGetFilms(Repository.getFilms())
    }

}