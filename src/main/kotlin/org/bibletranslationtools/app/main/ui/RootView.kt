package org.bibletranslationtools.app.main.ui

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.Node
import javafx.scene.control.ListView
import javafx.scene.control.ScrollBar
import javafx.scene.control.skin.ListViewSkin
import javafx.scene.control.skin.VirtualFlow
import org.bibletranslationtools.app.main.ui.component.RecyclingPool
import tornadofx.*

class RootView : View() {

    val obsList = observableListOf<Int>(0,1,2,3,4,5,6,7,8,9,10)

    private val windowValue = SimpleIntegerProperty()
    private val poolContainer = RecyclingPool().apply {
        slidingStart.bind(windowValue)
    }

    override val root = vbox {
        paddingAll = 10.0
        maxHeight = 100.0

        vbox {
            add(poolContainer)

            slider(0, 50) {
                windowValue.bind(valueProperty().integerBinding { it?.toInt()?:0 })
            }
            label(windowValue)

            windowValue.onChange {
                poolContainer.renderNodes()
            }
        }


    }

    init {
        importStylesheet(javaClass.getResource("/css/my.css").toExternalForm())
        importStylesheet(resources["/css/custom.css"])
        workspace.header.removeFromParent()
    }

    override fun onDock() {
        super.onDock()
    }
}

fun <T> ListView<T>.virtualFlow(): VirtualFlow<*> {
    return (this.skin as ListViewSkin<*>).children.first() as VirtualFlow<*>
}

