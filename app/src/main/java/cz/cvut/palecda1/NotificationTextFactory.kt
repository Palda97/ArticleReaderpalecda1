package cz.cvut.palecda1

import com.google.gson.Gson
import cz.cvut.palecda1.model.RoomArticle

class NotificationTextFactory {

    val gson = Gson()

    fun fromArticleListToMap(list: List<RoomArticle>): Map<String, Int> {
        val feedMap: MutableMap<String, Int> = HashMap()
        list.forEach {
            feedMap[it.feed] = feedMap[it.feed]?.plus(1) ?: 1
        }
        return feedMap
    }

    fun fromMapToString(feedMap: Map<String, Int>): String {
        val stringBuilder = StringBuilder()
        var first = true
        feedMap.forEach{
            if(!first){
                stringBuilder.append(", ")
            } else {
                first = false
            }
            stringBuilder.append("${it.key} (${it.value})")
        }
        return stringBuilder.toString()
    }

    fun fromJsonToMap(str: String): Map<String, Int> {
        val gsonsStupidDoubleMap = gson.fromJson<Map<String, Double>>(str, Map::class.java) ?: HashMap()
        return gsonsStupidDoubleMap.mapValues { it.value.toInt() }
    }

    fun fromMapToJson(feedMap: Map<String, Int>): String {
        return gson.toJson(feedMap)
    }

    fun mergeMaps(firstMap: Map<String, Int>, secondMap: Map<String, Int>): Map<String, Int> {
        val res: MutableMap<String, Int> = mutableMapOf()
        firstMap.forEach {
            res[it.key] = it.value + (secondMap[it.key] ?: 0)
        }
        return secondMap + res
    }
}