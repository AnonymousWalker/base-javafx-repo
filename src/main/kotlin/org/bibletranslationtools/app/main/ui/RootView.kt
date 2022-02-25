package org.bibletranslationtools.app.main.ui

import javafx.geometry.Pos
import javafx.geometry.VPos
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import org.bibletranslationtools.app.main.viewmodel.ContributorListViewModel
import org.kordamp.ikonli.javafx.FontIcon
import org.kordamp.ikonli.material.Material
import tornadofx.*

class RootView : View() {
    private val viewModel: ContributorListViewModel by inject()
    private lateinit var txtInput: TextField
    private val projectId = 1

    init {
        importStylesheet(javaClass.getResource("/css/my.css").toExternalForm())
        workspace.header.removeFromParent()
    }

    override val root = vbox {
        addClass("contributor__region")

        vbox {
            label("License Information") {
                addClass("contributor__section-heading")
            }
            textflow{
                text("By exporting this project, you agree to release your work under a ")
                hyperlink("Creative Commons - Attribution-ShareAlike 4.0 International - CC BY-SA.4.0") {
                    isWrapText = true
                }
                text("licence")
            }
        }

        vbox {
            label("Contributor Information") {
                addClass("contributor__section-heading")
            }
            label("Please include the names or pseudonyms of everyone who contributed to this project") {
                isWrapText = true
            }
        }

        hbox {
            addClass("contributor__input-group")

            alignment = Pos.CENTER_LEFT
            textfield(viewModel.nameInputProperty) {
                txtInput = this
                addClass("contributor__name-input")

                setOnKeyPressed {
                    if (it.code == KeyCode.ENTER) {
                        viewModel.addContributor()
                    }
                }
            }
            button("Add") {
                addClass("contributor__add-btn")
                graphic = FontIcon(Material.ADD)
                setOnAction {
                    viewModel.addContributor()
                    txtInput.requestFocus()
                }
            }
        }

        listview(viewModel.contributorList) {
            addClass("wa-list-view","contributor-list")

            isMouseTransparent = true
            isFocusTraversable = false

            setCellFactory {
                ContributorListCell()
            }
        }
    }
}
