package ayds.songinfo.moredetails.presentation

import ayds.songinfo.moredetails.domain.Article
import org.junit.Assert
import org.junit.Test

class ArtistArticleDescriptionHelperTest {
    private val articleDescriptionHelper: ArtistArticleDescriptionHelper = ArtistArticleDescriptionHelperImpl()


    @Test
    fun `given a locally stored article it should return the marked article description`(){
        val artistArticle = Article.ArtistArticle("Gustavo Cerati", "Biography", "info", true)

        val result = articleDescriptionHelper.getDescription(artistArticle)

        val expectedResult = "<html><div width=400><font face=\"arial\">[*] Biography</font></div></html>"

        Assert.assertEquals(result, expectedResult)
    }

    @Test
    fun `given a non-locally stored article it should return the unmarked article description`(){
        val artistArticle = Article.ArtistArticle("Gustavo Cerati", "Biography", "info", false)

        val result = articleDescriptionHelper.getDescription(artistArticle)

        val expectedResult = "<html><div width=400><font face=\"arial\">Biography</font></div></html>"

        Assert.assertEquals(result, expectedResult)
    }

}