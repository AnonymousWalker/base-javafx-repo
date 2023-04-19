package org.bibletranslationtools.app.main.ui

import javafx.geometry.Pos
import javafx.scene.control.TableCell
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.Priority
import org.bibletranslationtools.app.main.ui.model.Workbook
import org.bibletranslationtools.app.main.ui.model.WorkbookAction
import org.kordamp.ikonli.javafx.FontIcon
import tornadofx.*

class RootView : View() {

    private val listItems = observableListOf<Workbook>(
        Workbook("Jude", "New Testament"),
        Workbook("2 Peter", "New Testament"),
        Workbook("Genesis", "Old Testament"),
        Workbook("Romans", "New Testament"),
        Workbook("Psalms", "Old Testament"),
        Workbook("Leviticus", "Old Testament"),
        Workbook("Mark", "New Testament")
    )


    override val root = vbox {
        paddingAll = 10.0

        vbox {
            alignment = Pos.CENTER

            tableview(listItems) {
                addClass("wa-table-view")
                column("Book", Workbook::nameProp).apply {
                    addClass("wa-table-column")
                    setCellValueFactory {
                        it.value.nameProp
                    }
                    setCellFactory { TableHeaderCell() }
                    usePrefWidth = true
                    remainingWidth()
                }
                column("Anthology", Workbook::project.getter).apply {
                    setCellValueFactory {
                        it.value.project.toProperty()
                    }
                    setCellFactory { TableTextCell() }
                    remainingWidth()

                }
                column("Progress", Workbook::progress.getter).apply {
                    cellValueFactory = PropertyValueFactory(Workbook::progress.name)
                    setCellFactory { TableProgressCell() }
                    usePrefWidth = true
                    remainingWidth()

                }
                column("Action", Workbook::action.getter).apply {
                    setCellValueFactory {
                        it.value.action.toProperty()
                    }
                    setCellFactory { TableActionCell() }
                    usePrefWidth = true
                    remainingWidth()

                }
            }
        }
        add(workspace)
    }

    init {
        importStylesheet(javaClass.getResource("/css/my.css").toExternalForm())
        importStylesheet(resources["/css/custom.css"])
        workspace.header.removeFromParent()
    }
}

open class TableTextCell : TableCell<Workbook, String>() {
    override fun updateItem(item: String?, empty: Boolean) {
        super.updateItem(item, empty)
        if (item == null || empty) {
            graphic = null
            return
        }

        graphic = label(item)
    }
}

class TableHeaderCell : TableTextCell() {
    override fun updateItem(item: String?, empty: Boolean) {
        super.updateItem(item, empty)
        if (item == null || empty) {
            graphic = null
            return
        }

        graphic = label(item) {
            addClass("table-view__title-cell")
        }
    }
}

class TableProgressCell : TableCell<Workbook, Double>() {
    override fun updateItem(item: Double?, empty: Boolean) {
        super.updateItem(item, empty)
        if (item == null || empty) {
            graphic = null
            return
        }

        graphic = progressbar(item) {
            useMaxWidth = true
        }
    }
}

class TableActionCell : TableCell<Workbook, WorkbookAction>() {
    private val actionButton = button {
        graphic = FontIcon("mdi-dots-horizontal").apply {
            addClass("table-view__action-icon")
        }
    }

    override fun updateItem(item: WorkbookAction?, empty: Boolean) {
        super.updateItem(item, empty)
        if (item == null || empty) {
            graphic = null
            return
        }

        graphic = actionButton.apply {
            setOnAction {
                item.openBook()
            }
        }
    }
}