package ayds.songinfo.moredetails.presentation

import ayds.songinfo.moredetails.domain.Card
import org.junit.Assert
import org.junit.Test

class CardDescriptionHelperTest {
    private val articleDescriptionHelper: CardDescriptionHelper = CardDescriptionHelperImpl()


    @Test
    fun `given a locally stored article it should return the marked article description`(){
        val card = Card("Gustavo Cerati", "Biography", "info", isLocallyStored = true)

        val result = articleDescriptionHelper.getDescription(card)

        val expectedResult = "<html><div width=400><font face=\"arial\">[*] Biography</font></div></html>"

        Assert.assertEquals(result, expectedResult)
    }

    @Test
    fun `given a non-locally stored article it should return the unmarked article description`(){
        val card = Card("Gustavo Cerati", "Biography", "info", isLocallyStored = false)

        val result = articleDescriptionHelper.getDescription(card)

        val expectedResult = "<html><div width=400><font face=\"arial\">Biography</font></div></html>"

        Assert.assertEquals(result, expectedResult)
    }

}