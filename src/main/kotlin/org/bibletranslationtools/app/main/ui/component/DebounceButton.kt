package org.bibletranslationtools.app.main.ui.component

import javafx.animation.PauseTransition
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.control.Button
import javafx.util.Duration
import tornadofx.*

class DebouncedButton : Button() {
    private val isCoolingDownProperty = SimpleBooleanProperty(false)
    private val isCoolingDown by isCoolingDownProperty
    private val pauseTransition = PauseTransition(Duration.millis(1000.0))

    init {
        pauseTransition.setOnFinished {
            isCoolingDownProperty.set(false)
        }
    }


    fun setOnAction(op: () -> Unit) {
        super.setOnAction {
            if (!isCoolingDown) {
                op()
                isCoolingDownProperty.set(true)
                pauseTransition.playFromStart()
            }
        }
    }
}