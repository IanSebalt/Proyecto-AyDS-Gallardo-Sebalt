package ayds.songinfo.utils.view

interface DateConversor {
    fun convertDate(releaseDate: String, releaseDatePrecision: String): String
}

internal class DateConversorImpl : DateConversor {

    override fun convertDate(releaseDate: String, releaseDatePrecision: String) =
        when (releaseDatePrecision) {
            "year" -> releaseDate + if (itsLeapYear(releaseDate.toInt())) " (Leap year)" else " (Not a leap year)"
            "month" -> releaseDate.split("-").let {
                convertToMonth(it[1]) + ", " + it[0]
            }

            "day" -> releaseDate.split("-").let {
                it[2] + "/" + it[1] + "/" + it[0]
            }

            else -> "Unknown"
        }

    private fun itsLeapYear(year: Int): Boolean {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
    }

    private fun convertToMonth(mes:String):String{
        return when(mes){
            "01" -> "January"
            "02" -> "February"
            "03" -> "March"
            "04" -> "April"
            "05" -> "May"
            "06" -> "June"
            "07" -> "July"
            "08" -> "August"
            "09" -> "September"
            "10" -> "October"
            "11" -> "November"
            "12" -> "December"
            else -> "Unknown"
        }
    }
}