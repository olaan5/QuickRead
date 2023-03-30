package com.miniweam.quickread

import java.util.ArrayList

data class ItemsWithCategories(
    val id: Int? = null,
    val category: String = "",
    val source: String = "",
    val content: String = "",
    val timeStamp: Int? = null
)

object DummyItem {
    fun getCategories():List<String>{
        return getData().map {
            it.category
        }.toList()
    }

    fun getData(): ArrayList<ItemsWithCategories> {
        val items = ArrayList<ItemsWithCategories>()
        return items.apply {
            add(
                ItemsWithCategories(
                    1,
                    "Sports",
                    source = "Punch.NG",
                    content = "Ronaldo and Messi to have a face off at Old Trafford",
                    timeStamp = 3
                )
            )
            add(
                ItemsWithCategories(
                    2,
                    "Entertainment",
                    source = "Guardian",
                    content = "So we have a grand plan of destroying the world but we need more funds for the plan to succeed",
                    timeStamp = 2
                )
            )
            add(
                ItemsWithCategories(
                    3,
                    "Gossip",
                    source = "Amebo",
                    content = "Apparently there are lotta rich kids at L.S.E.I",
                    timeStamp = 10
                )
            )
            add(
                ItemsWithCategories(
                    3,
                    "Irrelevant",
                    source = "Anonymous",
                    content = "So we have a grand plan of destroying the world but we need more funds for the plan to succeed",
                    timeStamp = 21
                )
            )
            add(
                ItemsWithCategories(
                    4,
                    "Tech",
                    source = "Facebook",
                    content = "Mark Zuckerberg plans to buy twitter for $100 billion, WHY?",
                    timeStamp = 20
                )
            )
            add(
                ItemsWithCategories(
                    5,
                    "Fashion",
                    source = "Vogue",
                    content = "So we have a grand plan of destroying the world but we need more funds for the plan to succeed",
                    timeStamp = 12
                )
            )
            add(
                ItemsWithCategories(
                    6,
                    "Business",
                    source = "Forbes",
                    content = "So we have a grand plan of destroying the world but we need more funds for the plan to succeed",
                    timeStamp = 14
                )
            )
            add(
                ItemsWithCategories(
                    7,
                    "Politics",
                    source = "Punch.NG",
                    content = "So lets just hope abnormal candidates  don't get elected...chai",
                    timeStamp = 16
                )
            )
            add(
                ItemsWithCategories(
                    8,
                    "Health",
                    source = "Punch.NG",
                    content = "So we have a grand plan of destroying the world but we need more funds for the plan to succeed",
                    timeStamp = 16
                )
            )
            add(
                ItemsWithCategories(
                    9,
                    "Politics",
                    source = "Business Insider",
                    content = "So we have another grand plan of selling the country to the highest bidder",
                    timeStamp = 13
                )
            )
            add(
                ItemsWithCategories(
                    10,
                    "Tech",
                    source = "SpaceX",
                    content = "So we have a grand plan of destroying the world but we need more funds for the plan to succeed",
                    timeStamp = 11
                )
            )
        }
    }
}