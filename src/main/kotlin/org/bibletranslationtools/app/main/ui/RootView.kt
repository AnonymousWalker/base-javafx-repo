package org.bibletranslationtools.app.main.ui

import com.jfoenix.controls.JFXPopup
import javafx.geometry.Pos
import javafx.scene.control.ListView
import javafx.scene.control.PopupControl
import javafx.stage.Popup
import javafx.stage.PopupWindow
import org.bibletranslationtools.app.main.ui.component.SourceText
import org.kordamp.ikonli.javafx.FontIcon
import org.kordamp.ikonli.materialdesign.MaterialDesign
import tornadofx.*

class RootView : View() {

    override val root = vbox {
        paddingAll = 10.0

        vbox {
            alignment = Pos.CENTER
            addClass("source-pane")

            label("Source content pane")
            button("Toggle text") {
                graphic = FontIcon(MaterialDesign.MDI_COMMENT_TEXT_OUTLINE)

                val popUp = Popup().apply {
                    val c = SourceText()
                    content.add(c)
                    isAutoHide = true

                    focusedProperty().onChange {
                        println("popup focused: $it")
                        if (it) c.requestFocus()
                    }
                }

                setOnAction {
                    val bound = this.boundsInLocal
                    val screenBound = this.localToScreen(bound)

                    popUp.show(
                        this,
                        0.0,
                        0.0
                    )
                    popUp.x = screenBound.minX - popUp.width
                    popUp.y = screenBound.minY - popUp.height
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
