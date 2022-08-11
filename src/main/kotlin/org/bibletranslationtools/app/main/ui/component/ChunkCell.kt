package org.bibletranslationtools.app.main.ui.component

import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.layout.HBox
import javafx.scene.text.Text
import tornadofx.*

class ChunkCell() : ListCell<String>() {

    override fun updateItem(item: String?, empty: Boolean) {
        super.updateItem(item, empty)

        if (item == null || empty) {
            graphic = Text()
            return
        }

        graphic = Label(item).apply {
            addClass("item-node")
            isWrapText = true
            prefWidthProperty().bind(listView.widthProperty().minus(50))
        }
    }
}