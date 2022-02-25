package org.bibletranslationtools.app.main.viewmodel

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import org.bibletranslationtools.app.main.entity.Contributor
import org.bibletranslationtools.app.main.persistence.ContributorRepository
import tornadofx.*

class ContributorListViewModel: ViewModel() {
    private val contributorRepo: ContributorRepository = ContributorRepository()

    val nameInputProperty = SimpleStringProperty("")
    val projectIdProperty = SimpleIntegerProperty(1)
    val contributorList = observableListOf<Contributor>()

    init {
        contributorList.setAll(contributorRepo.getAll())
        contributorList.onChange {
            contributorRepo.setAll(contributorList)
        }
    }

    // use case to ADD a contributor
    fun addContributor() {
        nameInputProperty.value.let {
            if (it.isNotBlank()) {
                val contributor = Contributor(
                    it.trim(),
                    projectIdProperty.value
                )
                contributorList.add(0, contributor)
                nameInputProperty.set("")
            }
        }
    }
}