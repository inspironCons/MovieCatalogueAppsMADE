package made.dicoding.moviecatalogueapps.core.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import made.dicoding.moviecatalogueapps.core.model.Companies
import made.dicoding.moviecatalogueapps.core.model.Genres

class Converters {
    @TypeConverter
    fun listToJsonGenres(list:List<Genres>):String = Gson().toJson(list)

    @TypeConverter
    fun jsonToListGenres(value: String): List<Genres> {
        val listType = object : TypeToken<List<Genres?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun listToJsonCompanies(list:List<Companies>):String = Gson().toJson(list)

    @TypeConverter
    fun jsonToListCompanies(value: String): ArrayList<Companies> {
        val listType = object : TypeToken<List<Companies?>?>() {}.type
        return Gson().fromJson(value, listType)
    }
}