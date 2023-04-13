package org.bibletranslationtools.app.main.ui

import javafx.collections.ObservableList
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import tornadofx.*

class RootView : View() {

    private lateinit var lv: ListView<Int>
    val listItems = observableListOf(1,2,3)

    val selectedList = observableListOf<Int>()

    override val root = vbox {
        paddingAll = 10.0

        vbox {
//            alignment = Pos.CENTER
            addClass("source-pane")
            spacing = 10.0

            checkbox("select all") {
                setOnAction {
                    if (isSelected) selectedList.setAll(listItems)
                    else selectedList.clear()
                    lv.refresh()
                }
            }
            hbox {
                label("selected:  ")
                label {
                    selectedList.onChange { text = it.list.toString() }
                }
            }

            listview(listItems) {
                lv = this
                setCellFactory {
                    CheckBoxCell(selectedList)
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

class CheckBoxCell(private val selectedList: ObservableList<Int>) : ListCell<Int>() {
    val box = checkbox {
        setOnAction {
            if (this.isSelected) selectedList.add(index + 1)
            else selectedList.remove(index + 1)
        }
    }

    override fun updateItem(item: Int?, empty: Boolean) {
        super.updateItem(item, empty)

        if (item == null || empty) {
            graphic = null
            return
        }

        graphic = box.apply {
            text = index.toString()
            this.isSelected = (index + 1) in selectedList
        }

    }
}