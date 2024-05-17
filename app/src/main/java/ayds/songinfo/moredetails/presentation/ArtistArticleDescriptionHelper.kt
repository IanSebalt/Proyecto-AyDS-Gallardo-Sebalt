package ayds.songinfo.moredetails.presentation

import ayds.songinfo.moredetails.domain.Article.ArtistArticle
import java.util.Locale

interface ArtistBiographyDescriptionHelper {
    fun getDescription(artistBiography: ArtistArticle): String
}

private const val HEADER = "<html><div width=400><font face=\"arial\">"
private const val FOOTER = "</font></div></html>"

internal class ArtistBiographyDescriptionHelperImpl : ArtistBiographyDescriptionHelper {

    override fun getDescription(artistBiography: ArtistArticle): String {
        val text = getTextBiography(artistBiography)
        return textToHtml(text, artistBiography.artistName)
    }

    private fun getTextBiography(artistBiography: ArtistArticle): String {
        val prefix = if (artistBiography.isLocallyStored) "[*] " else ""
        val text = artistBiography.biography.replace("\\n", "\n")
        return "$prefix$text"
    }

    private fun textToHtml(text: String, term: String): String {
        val builder = StringBuilder()
        builder.append(HEADER)
        val textWithBold = text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace(
                "(?i)$term".toRegex(),
                "<b>" + term.uppercase(Locale.getDefault()) + "</b>"
            )
        builder.append(textWithBold)
        builder.append(FOOTER)
        return builder.toString()
    }
}