package org.bibletranslationtools.app.main.ui

import javafx.scene.layout.VBox
import org.bibletranslationtools.app.main.viewmodel.ContributorListViewModel
import tornadofx.*

class LicenseModal: View() {

    private val viewModel: ContributorListViewModel by inject()

    override val root = webview {
        engine.loadContent(viewModel.licenseInfo())
    }
}