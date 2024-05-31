package ayds.songinfo.moredetails.domain

interface CardRepository {

    fun getCardsByArtistName(artistName: String): List<Card>

}