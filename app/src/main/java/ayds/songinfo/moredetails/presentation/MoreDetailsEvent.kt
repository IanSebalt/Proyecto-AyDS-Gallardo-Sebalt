package ayds.songinfo.moredetails.presentation

sealed class MoreDetailsEvent {
    object GetArticle : MoreDetailsEvent()
    object OpenArticle : MoreDetailsEvent()
}