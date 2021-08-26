package com.github.vivchar.example.interactors

interface Interactor<Params, Result> {
	fun execute(params: Params, result: (Result) -> Unit)
}
interface Interactor2<Params, Result> {
	fun execute(params: Params): Result
}