package com.github.vivchar.example.interactors

interface Interactor<Params, Result> {
	fun execute(params: Params): Result

	companion object {
		fun <Result> Interactor<Unit, Result>.execute() = execute(Unit)
	}
}