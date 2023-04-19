package org.bibletranslationtools.app.main.ui.model

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import java.beans.EventHandler

class Workbook(
    val name: String,
    val project: String,
    val progress: Double = 0.5,
) {
    val action: WorkbookAction = WorkbookActionDefault(this)
    val nameProp = SimpleStringProperty(name)
}

interface WorkbookAction {
    fun openBook()
}

class WorkbookActionDefault(private val workbook: Workbook) : WorkbookAction {
    override fun openBook() {
        println("opening book ${workbook.name}")
    }
}