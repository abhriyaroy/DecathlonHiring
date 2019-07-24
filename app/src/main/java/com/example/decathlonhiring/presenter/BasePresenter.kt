package com.example.decathlonhiring.presenter

interface BasePresenter<T> {
  fun attachView(view: T)
  fun detachView()
}