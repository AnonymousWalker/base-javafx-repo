package org.bibletranslationtools.app.main.ui.component

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import tornadofx.*

class RecycleNode : VBox() {

    val position = SimpleDoubleProperty()
    val nodeNumber = SimpleIntegerProperty()

    init {
        translateXProperty().bind(position)
        label(nodeNumber) {

        }
        button("||") {

        }
    }
}

class RecyclingPool : Pane() {

    private val pool = observableListOf<RecycleNode>()
    val slidingStart = SimpleIntegerProperty()
    val slidingEnd = SimpleIntegerProperty()
    private val visibleItems = observableListOf<RecycleNode>()

    init {
        slidingEnd.bind(slidingStart.plus(3))

        repeat(5) { index ->
            pool.add(RecycleNode())
        }

        bindChildren(visibleItems){ it }
    }

    fun renderNodes() {
        visibleItems.clear()
        val range = slidingStart.value .. slidingEnd.value
        for (i in range) {
            pool[i - range.start].apply {
                nodeNumber.set(i + 1)
                position.set(i*50.0)

                visibleItems.add(this)
            }
        }
    }

}