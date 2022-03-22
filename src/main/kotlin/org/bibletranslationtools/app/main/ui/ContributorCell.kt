package org.bibletranslationtools.app.main.ui

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.ListCell
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox
import org.bibletranslationtools.app.main.entity.Contributor
import tornadofx.*

/** List cell combines UI and data to render */
class ContributorListCell : ListCell<Contributor>() {
    private val cellGraphic = ContributorCell()
    var onEdit = SimpleObjectProperty<EventHandler<ActionEvent>>()

    override fun updateItem(item: Contributor?, empty: Boolean) {
        super.updateItem(item, empty)

        if (empty || item == null) {
            graphic = null
            return
        }

        graphic = cellGraphic.apply {
            name.set(item.name)
            index.set(this@ContributorListCell.index)
            onEditActionProperty.bind(this@ContributorListCell.onEdit)
        }
    }
}

/** Cell's UI element */
class ContributorCell : HBox() {
    val name = SimpleStringProperty()
    val index = SimpleIntegerProperty()
    val onEditActionProperty = SimpleObjectProperty<EventHandler<ActionEvent>>()

    init {
        addClass("contributor-list__cell")

        textfield(name) {
            focusedProperty().addListener(
                ChangeListener { observable, oldValue, newValue ->
                    if (oldValue == true && newValue == false) {
                        val data = ContributorCellData(index.value, name.value)
                        onEditActionProperty.value?.handle(ActionEvent(data, null))
                    }
                }
            )

            setOnKeyReleased {
                if (it.code == KeyCode.ENTER) {
                    this@ContributorCell.requestFocus()
                }
            }
        }
    }
}