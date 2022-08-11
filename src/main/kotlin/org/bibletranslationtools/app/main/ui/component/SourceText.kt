package org.bibletranslationtools.app.main.ui.component

import javafx.scene.control.ScrollPane
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.*

class SourceText : VBox() {
    lateinit var sp: ScrollPane
    init {
        addClass("source-popup-content")
        focusedProperty().onChange {
            println("popup content focus: $it")
            if (it) sp.requestFocus()
        }
        scrollpane {
            sp = this
            vgrow = Priority.ALWAYS

            focusedProperty().onChange {
                println("popup scroll focus: $it")
            }
            addClass("source-popup-content__scroll")
            vbox {
                vgrow = Priority.ALWAYS
                maxWidthProperty().bind(this@scrollpane.widthProperty().minus(20))
                label("Genesis") {
                }
                label(ScriptureText.firstGenesis) {
                    isWrapText = true
                }
                region { vgrow = Priority.ALWAYS }
                label("license content here...") {

                }
            }

        }
    }
}