package org.bibletranslationtools.app.main.ui.component

import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.PopupControl
import javafx.scene.control.ScrollPane
import javafx.scene.control.Skin
import javafx.scene.layout.GridPane
import javafx.scene.layout.Region
import javafx.scene.layout.VBox
import tornadofx.*


class ChapterGridMenu : PopupControl() {

    val chapterGridItemList = (1..150).toList()
    private val chapterGrid = ChapterGrid(chapterGridItemList)

    init {
        addClass("chapter-grid-context-menu")
    }

    override fun createDefaultSkin(): Skin<*> {
        return ChapterMenuSkin(this, chapterGrid)
    }
}

class ChapterMenuSkin(
    val control: ChapterGridMenu,
    chapterGrid: ChapterGrid
) : Skin<ChapterGridMenu> {

    private val root = VBox().apply {
        addClass("popup-root")
        add(
            ScrollPane(chapterGrid).apply {
                addClass("chapter-grid-context-menu__scroll-pane")
                maxHeight = 300.0
                isFitToWidth = true
            }
        )
    }

    override fun getSkinnable(): ChapterGridMenu {
        return control
    }

    override fun getNode(): Node {
        return root
    }

    override fun dispose() {
        root.idProperty().unbind()
        root.styleProperty().unbind()
    }
}


class ChapterGrid(val list: List<Int>) : GridPane() {
    init {
        addClass("chapter-grid")

        list.forEachIndexed { index, element ->
            val node = Button("c$element").apply {
                prefWidth = 100.0
            }
            this.add(node, index % 5, index / 5)
        }
    }
}