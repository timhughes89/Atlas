package com.timsimonhughes.discover.data

import com.timsimonhughes.discover.model.Planet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalCoroutinesApi
class DiscoverRepository  {

    private val planets = listOf(
        Planet(0, "Earth - The Big Blue", "https://images.unsplash.com/photo-1451187580459-43490279c0fa?ixlib=rb-1.2.1&auto=format&fit=crop&w=2852&q=80.jpg",  "Our home planet is the third planet from the Sun, and the only place we know of so far that’s inhabited by living things. While Earth is only the fifth largest planet in the solar system, it is the only world in our solar system with liquid water on the surface. \n" +
                "\n" +
                "Just slightly larger than nearby Venus, Earth is the biggest of the four planets closest to the Sun, all of which are made of rock and metal.  \n" +
                "\n" +
                "The name Earth is at least 1,000 years old. All of the planets, except for Earth, were named after Greek and Roman gods and goddesses. However, the name Earth is a Germanic word, which simply means “the ground.", "", "", "",  2.00f, ""),
        Planet(1, "Mars - The Red Planet", "https://www.somagnews.com/wp-content/uploads/2020/07/61331-e1595883407968.jpg",  "The fourth planet from the Sun, Mars is a dusty, cold, desert world with a very thin atmosphere. This dynamic planet has seasons, polar ice caps, extinct volcanoes, canyons and weather. Mars is one of the most explored bodies in our solar system, and it's the only planet where we've sent rovers to roam the alien landscape. NASA missions have found lots of evidence that Mars was much wetter and warmer, with a thicker atmosphere, billions of years ago. \n" +
                "\n" +
                "Mars was named by the Romans for their god of war because its reddish color was reminiscent of blood. Other civilizations also named the planet for this attribute; for example, the Egyptians called it Her Desher, meaning the red one. Even today, it is frequently called the Red Planet because iron minerals in the Martian dirt oxidize, or rust, causing the surface to look red.", "", "", "",  2.00f, ""),
        Planet(2, "Saturn", "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/pia22768.jpg",  "", "", "", "",  2.00f, ""),
        Planet(3, "Saturn", "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/pia22768.jpg",  "", "", "", "",  2.00f, ""),
        Planet(4, "Saturn", "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/pia22768.jpg",  "", "", "", "",  2.00f, ""),
        Planet(5, "Saturn", "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/pia22768.jpg",  "", "", "", "",  2.00f, "")
    )

     val examples = arrayListOf(
        Planet(0, "Earth - The Big Blue", "https://images.unsplash.com/photo-1451187580459-43490279c0fa?ixlib=rb-1.2.1&auto=format&fit=crop&w=2852&q=80.jpg",  "Our home planet is the third planet from the Sun, and the only place we know of so far that’s inhabited by living things. While Earth is only the fifth largest planet in the solar system, it is the only world in our solar system with liquid water on the surface. \n" +
                "\n" +
                "Just slightly larger than nearby Venus, Earth is the biggest of the four planets closest to the Sun, all of which are made of rock and metal.  \n" +
                "\n" +
                "The name Earth is at least 1,000 years old. All of the planets, except for Earth, were named after Greek and Roman gods and goddesses. However, the name Earth is a Germanic word, which simply means “the ground.", "", "", "",  2.00f, ""),
        Planet(1, "Mars - The Red Planet", "https://www.somagnews.com/wp-content/uploads/2020/07/61331-e1595883407968.jpg",  "The fourth planet from the Sun, Mars is a dusty, cold, desert world with a very thin atmosphere. This dynamic planet has seasons, polar ice caps, extinct volcanoes, canyons and weather. Mars is one of the most explored bodies in our solar system, and it's the only planet where we've sent rovers to roam the alien landscape. NASA missions have found lots of evidence that Mars was much wetter and warmer, with a thicker atmosphere, billions of years ago. \n" +
                "\n" +
                "Mars was named by the Romans for their god of war because its reddish color was reminiscent of blood. Other civilizations also named the planet for this attribute; for example, the Egyptians called it Her Desher, meaning the red one. Even today, it is frequently called the Red Planet because iron minerals in the Martian dirt oxidize, or rust, causing the surface to look red.", "", "", "",  2.00f, ""),
        Planet(2, "Saturn", "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/pia22768.jpg",  "", "", "", "",  2.00f, ""),
        Planet(3, "Saturn", "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/pia22768.jpg",  "", "", "", "",  2.00f, ""),
        Planet(4, "Saturn", "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/pia22768.jpg",  "", "", "", "",  2.00f, ""),
        Planet(5, "Saturn", "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/pia22768.jpg",  "", "", "", "",  2.00f, "")
    )


    fun getItems() : MutableStateFlow<List<Planet>> {
        val planetList = MutableStateFlow<List<Planet>>(emptyList())
        planetList.value = planets
        return planetList
    }

    fun getItem(itemId: Int) : Planet? {
        return planets.find {
            it.id == itemId
        }
    }
}