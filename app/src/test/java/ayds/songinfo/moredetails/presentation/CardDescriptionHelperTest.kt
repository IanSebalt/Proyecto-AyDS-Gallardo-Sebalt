package ayds.songinfo.moredetails.presentation

import ayds.songinfo.moredetails.domain.Card
import org.junit.Assert
import org.junit.Test

class CardDescriptionHelperTest {
    private val cardDescriptionHelper: CardDescriptionHelper = CardDescriptionHelperImpl()


    @Test
    fun `given a locally stored article it should return the marked article description`(){
        val card = Card("Gustavo Cerati", "Biography", "info", isLocallyStored = true)

        val result = cardDescriptionHelper.getDescription(card)

        val expectedResult = "<html><div width=400><font face=\"arial\">[*] Biography</font></div></html>"

        Assert.assertEquals(result, expectedResult)
    }

    @Test
    fun `given a non-locally stored article it should return the unmarked article description`(){
        val card = Card("Gustavo Cerati", "Biography", "info", isLocallyStored = false)

        val result = cardDescriptionHelper.getDescription(card)

        val expectedResult = "<html><div width=400><font face=\"arial\">Biography</font></div></html>"

        Assert.assertEquals(result, expectedResult)
    }

    @Test
    fun `should remove apostrophes`() {
        val artistBiography = Card("artist", "biography'n", "url", isLocallyStored=false)

        val result = cardDescriptionHelper.getDescription(artistBiography)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography n</font></div></html>",
            result
        )
    }

    @Test
    fun `should fix on double slash`() {
        val artistBiography = Card("artist", "biography\\n", "url", isLocallyStored = false)

        val result = cardDescriptionHelper.getDescription(artistBiography)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography<br></font></div></html>",
            result
        )
    }

    @Test
    fun `should map break lines`() {
        val artistBiography = Card("artist", "biography\n", "url", isLocallyStored = false)

        val result = cardDescriptionHelper.getDescription(artistBiography)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography<br></font></div></html>",
            result
        )
    }
    @Test
    fun `should set artist name bold`() {
        val artistBiography = Card("artist", "biography artist", "url", isLocallyStored = false)

        val result = cardDescriptionHelper.getDescription(artistBiography)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography <b>ARTIST</b></font></div></html>",
            result
        )
    }

}