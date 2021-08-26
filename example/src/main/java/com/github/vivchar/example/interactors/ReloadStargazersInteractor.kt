package com.github.vivchar.example.interactors

import com.github.vivchar.data.repositories.StargazersRepository

class ReloadStargazersInteractor(private val stargazersRepository: StargazersRepository): Interactor<Unit, Unit> {
	override fun execute(params: Unit, result: (Unit) -> Unit) {
		stargazersRepository.sendReloadRequest()
	}
}