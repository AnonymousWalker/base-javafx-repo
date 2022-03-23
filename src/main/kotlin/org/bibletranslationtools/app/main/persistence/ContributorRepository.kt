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
    private val jsonFilePath = "D:\\misc\\temp\\contributors.json"
    private val licenseHtml = javaClass.classLoader.getResource("license.html").file

    fun getAll(): List<Contributor> {
        return jacksonObjectMapper().readValue(getResourceAsString(jsonFilePath))
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

    fun setAll(contributors: List<Contributor>) {
        val json = jacksonObjectMapper().writeValueAsString(contributors)
        writeJsonToResource(json)
    }

    fun getLicense(): String {
        return getResourceAsString(licenseHtml)
    }

    private fun getResourceAsString(path: String): String {
        return File(path).readText()
    }

    private fun writeJsonToResource(json: String) {
        File(jsonFilePath).writeText(json)
    }
}