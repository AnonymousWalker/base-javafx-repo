package org.bibletranslationtools.app.main.persistence

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.bibletranslationtools.app.main.entity.Contributor
import java.io.File
import java.util.concurrent.TimeUnit

class ContributorRepository {
    private val jsonFilePath = "/your/json/file.json"

    fun getAll(): List<Contributor> {
        return jacksonObjectMapper().readValue(getResourceAsString())
    }

    fun add(contributor: Contributor) {
        val list = getAll().toMutableList()
        list.add(contributor)
        val json = jacksonObjectMapper().writeValueAsString(list)
        writeJsonToResource(json)
    }

    fun addAll(contributors: List<Contributor>) {
        val currentList = getAll()
        val json = jacksonObjectMapper().writeValueAsString(currentList + contributors)
        writeJsonToResource(json)
    }

    fun setAll(contributors: List<Contributor>): Observable<Int> {
        return Observable
            .fromCallable {
                val json = jacksonObjectMapper().writeValueAsString(contributors)
                writeJsonToResource(json)
                0
            }
            .subscribeOn(Schedulers.computation())
    }

    private fun getResourceAsString(): String {
        return File(jsonFilePath).readText()
    }

    private fun writeJsonToResource(json: String) {
        File(jsonFilePath).writeText(json)
    }
}