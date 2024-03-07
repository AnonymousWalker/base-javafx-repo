package org.bibletranslationtools.app.main.ui.component

import org.kordamp.ikonli.AbstractIkonHandler
import org.kordamp.ikonli.Ikon
import org.kordamp.ikonli.IkonProvider
import java.io.InputStream
import java.net.URL


enum class MyIcon(private val description: String, private val code: Int) : Ikon {
    CONSUME("icon-consume", 59393);

    override fun getDescription(): String {
        return description
    }

    override fun getCode(): Int {
        return code
    }

    companion object {
        fun findByDescription(description: String): MyIcon {
            for (font in values()) {
                if (font.getDescription() == description) {
                    return font
                }
            }
            throw IllegalArgumentException("Icon description '$description' is invalid!")
        }
    }
}

class MyIconIkonHandler : AbstractIkonHandler() {
    override fun supports(description: String): Boolean {
        return description != null && description.startsWith("icon-")
    }

    override fun resolve(description: String): Ikon {
        return MyIcon.findByDescription(description)
    }

    override fun getFontResource(): URL {
        return javaClass.getResource(FONT_RESOURCE)
    }

    override fun getFontResourceAsStream(): InputStream {
        return javaClass.getResourceAsStream(FONT_RESOURCE)
    }

    override fun getFontFamily(): String {
        return "MyIcon-Font"
    }

    companion object {
        private const val FONT_RESOURCE = "/custom-font-ikonli/font/orature-icons.ttf"
    }
}


class MyIconIkonProvider : IkonProvider<MyIcon> {
    override fun getIkon(): Class<MyIcon> {
        return MyIcon::class.java
    }
}