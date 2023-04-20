package org.bibletranslationtools.app.main.ui.model

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import java.beans.EventHandler

class Workbook(
    val name: String,
    val project: String,
    val progress: Double = 0.5,
) {
    val nameProp = SimpleStringProperty(name)
}

interface WorkbookAction {
    fun openBook(w: Workbook)
    fun deleteBook(w: Workbook)
}

class WorkbookActionDefault : WorkbookAction {

    override fun openBook(w: Workbook) {
        println("viewmodel opening book ${w.name}")
    }

    override fun deleteBook(w: Workbook) {
        println("viewmodel deleting book ${w.name}")
    }

}