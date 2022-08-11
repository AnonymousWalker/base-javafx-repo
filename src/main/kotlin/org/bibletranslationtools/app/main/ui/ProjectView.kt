package org.bibletranslationtools.app.main.ui

import javafx.animation.*
import javafx.beans.property.SimpleStringProperty
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.text.FontWeight
import javafx.util.Duration
import org.kordamp.ikonli.javafx.FontIcon
import org.kordamp.ikonli.materialdesign.MaterialDesign
import tornadofx.*

class ProjectView : View() {

    private val nameProperty = SimpleStringProperty()

    private val breadCrumb = BreadCrumb().apply {
        titleProperty.bind(this@ProjectView.nameProperty)
        activeTitleProperty.set("Project")
        iconProperty.set(FontIcon(MaterialDesign.MDI_BOOK))
        onClickAction {
            navigator.dock<ProjectView>()
        }
    }
    private val navigator: Navigator by inject()
    private val takeList = //listOf(ListItemView("take 1"))
        listOf("take 1", "take 2", "take 3", "take 4", "take 5", "take 6")

    private val mainViewModel = find<MainViewModel>()
    private val listData = takeList.toObservable()

    override val root = vbox {
        spacing = 20.0
        paddingAll = 20.0
        alignment = Pos.CENTER

        label("ListView example:") {
            style {
                fontWeight = FontWeight.BOLD
            }
        }

        listview(listData) {

            prefHeight = 500.0
//            setCellFactory {
//                ListItemView()
//            }

            onMouseClicked = EventHandler {
                val index = this.selectionModel.selectedIndex
                val selectedItem = this.selectedItem

                listData.forEach {
                    if (listData.indexOf(it) < index) {
                        moveNodeDown(it as Node) { }
                    }
                }

                this.selectionModel.select(-1)

                moveNodeToTop(selectedItem as Node) {
                    listData.removeAt(index)
                    listData.add(0, selectedItem)
                    this.selectionModel.select(0)
                    this.refresh()
                }


            }
        }

        button("Reset").apply {
            graphic = FontIcon(MaterialDesign.MDI_FILE)
            setOnAction {
//                mainViewModel.activeBookProperty.set("Genesis")
//                navigator.dock<ChapterView>()
                listData.clear()
                listData.addAll(takeList)
            }
        }
    }

    init {
        nameProperty.bind(mainViewModel.activeBookProperty)
    }

    private fun fadeTransition(node: Node, onFinish: () -> Unit) {
        val ft = FadeTransition(Duration.millis(800.0), node)
        ft.fromValue = 1.0
        ft.toValue = 0.0
        ft.cycleCount = 2
        ft.isAutoReverse = true
        ft.onFinished = EventHandler {
            onFinish()
//            ft.stop()
        }
        ft.play()
    }



    private fun moveNodeToTop(node: Node, onFinish: () -> Unit) {
//        node.toFront()
        node.viewOrder = 0.0

        val parentY = node.parent.layoutY
        val tt = TranslateTransition(Duration.millis(700.0), node)
        tt.cycleCount = 1
        tt.toY = -parentY   // this will move to the top edge of the parent (list view)
        tt.isAutoReverse = false
        tt.onFinished = EventHandler {
            onFinish()
            revertTranslateTransition(node) { }
        }
        tt.play()
    }

    private fun moveNodeDown(node: Node, onFinish: () -> Unit) {
        node.toFront()
        val distance = 40.0
        val tt = TranslateTransition(Duration.millis(600.0), node)
        tt.cycleCount = 1
        tt.byY = distance
        tt.onFinished = EventHandler {
//            onFinish()
            revertTranslateTransition(node) { }
        }
        tt.play()
    }


    private fun revertTranslateTransition(node: Node, onFinish: () -> Unit) {
        val distance = node.translateY
        val tt = TranslateTransition(Duration.millis(1.0), node)
        tt.cycleCount = 1
        tt.byY = -distance     // revert the distance moved...
        tt.onFinished = EventHandler {
//            node.toBack()
            node.viewOrder = 1.0
            onFinish()
        }
        tt.play()
    }
}
