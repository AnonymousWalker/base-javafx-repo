package org.bibletranslationtools.app.main.ui

import javafx.collections.ObservableList
import javafx.scene.layout.HBox
import javafx.scene.paint.Paint
import tornadofx.*

class BreadCrumbs<T>(private val breadcrumbs: ObservableList<T>): HBox() {

    init {
        spacing = 5.0

        style {
            padding = box(10.px)
            borderWidth += box(1.px)
            borderColor += box(Paint.valueOf("#ccc"))
        }

        breadcrumbs.onChange {
            children.clear()

            it.list.forEach { item ->
                val crumb = item as? BreadcrumbComponent
                crumb?.let { _crumb ->
                    add(
                        BreadCrumb().apply {
                            iconProperty.set(_crumb.graphic)
                            titleProperty.set(_crumb.name)
                            activeTitleProperty.set(_crumb.defaultName)
                            isActiveProperty.set(isActive(_crumb))
                            setOnMouseClicked {
                                _crumb.onClick()
                            }
                        }
                    )
                }
            }
        }
    }

    private fun isActive(breadcrumb: BreadcrumbComponent): Boolean {
        return breadcrumbs.indexOf(breadcrumb) == (breadcrumbs.size - 1)
    }
}