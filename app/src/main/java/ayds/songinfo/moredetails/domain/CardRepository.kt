package ayds.songinfo.moredetails.domain

interface CardRepository {

    fun getCardByArtistName(artistName: String): Card

}