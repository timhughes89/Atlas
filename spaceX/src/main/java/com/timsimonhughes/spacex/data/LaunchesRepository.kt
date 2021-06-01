package com.timsimonhughes.spacex.data

import com.timsimonhughes.spacex.model.Capsule

class LaunchesRepository {

    fun findItemByid(id: Long): Capsule? {
        return capsulesArray.find {
            it._id == id
        }
    }

    val capsules = listOf(
        Capsule(0, "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/pia22768.jpg"),
        Capsule(1, "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/pia22768.jpg"),
        Capsule(2, "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/pia22768.jpg"),
        Capsule(3, "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/pia22768.jpg"),
        Capsule(4, "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/pia22768.jpg"),
        Capsule(5, "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/pia22768.jpg"),
        Capsule(6, "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/pia22768.jpg"),
        Capsule(7, "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/pia22768.jpg"),
        Capsule(8, "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/pia22768.jpg"),
        Capsule(9, "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/pia22768.jpg")
    )

    val capsulesArray = arrayListOf(
        Capsule(0, "https://images.unsplash.com/photo-1494022299300-899b96e49893?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2850&q=80.jpg"),
        Capsule(1, "https://images.unsplash.com/photo-1507499739999-097706ad8914?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2158&q=80.jpg"),
        Capsule(2, "https://images.unsplash.com/photo-1451187580459-43490279c0fa?ixlib=rb-1.2.1&auto=format&fit=crop&w=2852&q=80.jpg"),
        Capsule(3, "https://images.unsplash.com/photo-1446776811953-b23d57bd21aa?ixlib=rb-1.2.1&auto=format&fit=crop&w=1504&q=80.jpg"),
        Capsule(4, "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/pia22768.jpg"),
        Capsule(5, "https://images.unsplash.com/photo-1484589065579-248aad0d8b13?ixlib=rb-1.2.1&auto=format&fit=crop&w=792&q=80.jpg")
    )
}