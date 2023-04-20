package org.bibletranslationtools.app.main.ui

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ObservableValue
import javafx.geometry.Pos
import javafx.geometry.Side
import javafx.scene.control.Button
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.scene.control.TableRow
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.input.ContextMenuEvent
import javafx.util.Callback
import org.bibletranslationtools.app.main.ui.model.Workbook
import org.bibletranslationtools.app.main.ui.model.WorkbookAction
import org.bibletranslationtools.app.main.ui.model.WorkbookActionDefault
import org.kordamp.ikonli.javafx.FontIcon
import tornadofx.View
import tornadofx.addClass
import tornadofx.button
import tornadofx.column
import tornadofx.importStylesheet
import tornadofx.label
import tornadofx.observableListOf
import tornadofx.onSelectionChange
import tornadofx.paddingAll
import tornadofx.progressbar
import tornadofx.removeFromParent
import tornadofx.tableview
import tornadofx.toProperty
import tornadofx.useMaxWidth
import tornadofx.vbox

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
    private val actionCallback: WorkbookAction = WorkbookActionDefault()

    override val root = vbox {
        paddingAll = 10.0

        vbox {
            alignment = Pos.CENTER

            tableview(listItems) {
                columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY
                addClass("wa-table-view")
                column("Book", Workbook::nameProp).apply {
                    addClass("wa-table-column")
                    setCellValueFactory {
                        it.value.nameProp
                    }
                    setCellFactory { TableHeaderCell() }
                }
                column("Anthology", Workbook::project.getter).apply {
                    setCellValueFactory {
                        it.value.project.toProperty()
                    }
                    setCellFactory { TableTextCell() }
                }
                column("Progress", Workbook::progress.getter).apply {
                    setCellValueFactory { SimpleDoubleProperty(it.value.progress) as ObservableValue<Double> }
                    setCellFactory { TableProgressCell() }
                }
                columns.add(
                    TableColumn<Workbook, Workbook>("Action").apply {
                        setCellValueFactory { SimpleObjectProperty(it.value) }
                        setCellFactory {
                            TableActionCell(actionCallback)
                        }
                    }
                )

                setRowFactory {
                    val row = TableRow<Workbook>()
                    row.setOnMouseClicked {
                        actionCallback.openBook(row.item)
                    }
                    row
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

fun setUpPopupMenu(
    openCallback: () -> Unit,
    deleteCallback: () -> Unit
): ContextMenu {
    val openItem = MenuItem("Open Book").apply {
        setOnAction {
            openCallback()
        }
    }
    val deleteItem = MenuItem("Delete Book").apply {
        setOnAction {
            deleteCallback()
        }
    }
    return ContextMenu(openItem, deleteItem)
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
//        super.updateItem(item, empty)
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

class TableActionCell(
    val callback: WorkbookAction
) : TableCell<Workbook, Workbook>() {
    private val actionButton = button {
        graphic = FontIcon("mdi-dots-horizontal").apply {
            addClass("table-view__action-icon")
        }

    }
    private lateinit var popupMenu: ContextMenu

    override fun updateItem(item: Workbook?, empty: Boolean) {
        super.updateItem(item, empty)
        if (item == null || empty) {
            graphic = null
            return
        }

        popupMenu = setUpPopupMenu(
            {
                callback.openBook(item)
            },
            {
                callback.deleteBook(item)
            })

        graphic = actionButton.apply {
            setOnAction {
                popupMenu.show(actionButton, Side.BOTTOM, -40.0, 5.0)
            }
        }
    }
}

