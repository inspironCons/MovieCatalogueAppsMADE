package made.dicoding.moviecatalogueapps.core.model

data class Genres(
    val id:Int?,
    val name:String?
){
    override fun toString(): String {
        return name?:""
    }
}