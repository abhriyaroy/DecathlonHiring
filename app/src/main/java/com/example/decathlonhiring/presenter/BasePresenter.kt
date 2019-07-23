package com.example.maticnetwork.presenter

interface BasePresenter<T> {
  fun attachView(view: T)
  fun detachView()
}