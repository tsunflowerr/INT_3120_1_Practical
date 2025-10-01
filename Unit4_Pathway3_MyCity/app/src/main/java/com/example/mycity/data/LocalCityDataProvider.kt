package com.example.mycity.data

import com.example.mycity.R
import com.example.mycity.model.Category
import com.example.mycity.model.Place

/**
 * Local sample data for My City app (Hà Nội sample)
 */
object LocalCityDataProvider {
    val defaultCategory = getCategories()[0]

    val defaultPlace = getPlacesData().firstOrNull() ?: Place(
        id = 0,
        categoryId = defaultCategory.id,
        titleResourceId = R.string.app_name,
        shortDescriptionResourceId = R.string.app_name,
        detailsResourceId = R.string.app_name,
        imageResourceId = R.drawable.ic_placeholder,
        bannerImageResourceId = R.drawable.ic_placeholder
    )

    fun getCategories(): List<Category> {
        return listOf(
            Category(
                id = 1,
                titleResourceId = R.string.category_coffee,
                subtitleResourceId = R.string.category_subtitle,
                imageResourceId = R.drawable.ic_category_coffee
            ),
            Category(
                id = 2,
                titleResourceId = R.string.category_restaurant,
                subtitleResourceId = R.string.category_subtitle,
                imageResourceId = R.drawable.ic_category_restaurant
            ),
            Category(
                id = 3,
                titleResourceId = R.string.category_kids,
                subtitleResourceId = R.string.category_subtitle,
                imageResourceId = R.drawable.ic_category_kids
            ),
            Category(
                id = 4,
                titleResourceId = R.string.category_park,
                subtitleResourceId = R.string.category_subtitle,
                imageResourceId = R.drawable.ic_category_park
            ),
            Category(
                id = 5,
                titleResourceId = R.string.category_mall,
                subtitleResourceId = R.string.category_subtitle,
                imageResourceId = R.drawable.ic_category_mall
            )
        )
    }

    fun getPlacesData(): List<Place> {
        return listOf(
            Place(
                id = 101,
                categoryId = 1,
                titleResourceId = R.string.cafe_the_coffee_house,
                shortDescriptionResourceId = R.string.cafe_the_coffee_house_short,
                detailsResourceId = R.string.cafe_the_coffee_house_details,
                imageResourceId = R.drawable.cafe_coffee_house,
                bannerImageResourceId = R.drawable.cafe_coffee_house_banner
            ),
            Place(
                id = 102,
                categoryId = 1,
                titleResourceId = R.string.cafe_tranquil_books,
                shortDescriptionResourceId = R.string.cafe_tranquil_books_short,
                detailsResourceId = R.string.cafe_tranquil_books_details,
                imageResourceId = R.drawable.cafe_tranquil_books,
                bannerImageResourceId = R.drawable.cafe_tranquil_books_banner
            ),
            Place(
                id = 103,
                categoryId = 1,
                titleResourceId = R.string.cafe_giang,
                shortDescriptionResourceId = R.string.cafe_giang_short,
                detailsResourceId = R.string.cafe_giang_details,
                imageResourceId = R.drawable.cafe_giang,
                bannerImageResourceId = R.drawable.cafe_giang_banner
            ),

            Place(
                id = 201,
                categoryId = 2,
                titleResourceId = R.string.restaurant_quan_an_ngon,
                shortDescriptionResourceId = R.string.restaurant_quan_an_ngon_short,
                detailsResourceId = R.string.restaurant_quan_an_ngon_details,
                imageResourceId = R.drawable.restaurant_quan_an_ngon,
                bannerImageResourceId = R.drawable.restaurant_quan_an_ngon_banner
            ),
            Place(
                id = 202,
                categoryId = 2,
                titleResourceId = R.string.restaurant_bun_cha_huong_lien,
                shortDescriptionResourceId = R.string.restaurant_bun_cha_huong_lien_short,
                detailsResourceId = R.string.restaurant_bun_cha_huong_lien_details,
                imageResourceId = R.drawable.restaurant_bun_cha,
                bannerImageResourceId = R.drawable.restaurant_bun_cha_banner
            ),
            Place(
                id = 203,
                categoryId = 2,
                titleResourceId = R.string.restaurant_pizza_4ps,
                shortDescriptionResourceId = R.string.restaurant_pizza_4ps_short,
                detailsResourceId = R.string.restaurant_pizza_4ps_details,
                imageResourceId = R.drawable.restaurant_pizza_4ps,
                bannerImageResourceId = R.drawable.restaurant_pizza_4ps_banner
            ),

            Place(
                id = 301,
                categoryId = 3,
                titleResourceId = R.string.kids_kizciti,
                shortDescriptionResourceId = R.string.kids_kizciti_short,
                detailsResourceId = R.string.kids_kizciti_details,
                imageResourceId = R.drawable.kids_kizciti,
                bannerImageResourceId = R.drawable.kids_kizciti_banner
            ),
            Place(
                id = 302,
                categoryId = 3,
                titleResourceId = R.string.kids_times_city_vinke,
                shortDescriptionResourceId = R.string.kids_times_city_vinke_short,
                detailsResourceId = R.string.kids_times_city_vinke_details,
                imageResourceId = R.drawable.kids_times_city,
                bannerImageResourceId = R.drawable.kids_times_city_banner
            ),
            Place(
                id = 303,
                categoryId = 3,
                titleResourceId = R.string.kids_ho_tay_waterpark,
                shortDescriptionResourceId = R.string.kids_ho_tay_waterpark_short,
                detailsResourceId = R.string.kids_ho_tay_waterpark_details,
                imageResourceId = R.drawable.kids_waterpark,
                bannerImageResourceId = R.drawable.kids_waterpark_banner
            ),

            Place(
                id = 401,
                categoryId = 4,
                titleResourceId = R.string.park_thong_nhat,
                shortDescriptionResourceId = R.string.park_thong_nhat_short,
                detailsResourceId = R.string.park_thong_nhat_details,
                imageResourceId = R.drawable.park_thong_nhat,
                bannerImageResourceId = R.drawable.park_thong_nhat_banner
            ),
            Place(
                id = 402,
                categoryId = 4,
                titleResourceId = R.string.park_hoa_binh,
                shortDescriptionResourceId = R.string.park_hoa_binh_short,
                detailsResourceId = R.string.park_hoa_binh_details,
                imageResourceId = R.drawable.park_hoa_binh,
                bannerImageResourceId = R.drawable.park_hoa_binh_banner
            ),
            Place(
                id = 403,
                categoryId = 4,
                titleResourceId = R.string.park_bach_thao,
                shortDescriptionResourceId = R.string.park_bach_thao_short,
                detailsResourceId = R.string.park_bach_thao_details,
                imageResourceId = R.drawable.park_bach_thao,
                bannerImageResourceId = R.drawable.park_bach_thao_banner
            ),

            Place(
                id = 501,
                categoryId = 5,
                titleResourceId = R.string.mall_vincom,
                shortDescriptionResourceId = R.string.mall_vincom_short,
                detailsResourceId = R.string.mall_vincom_details,
                imageResourceId = R.drawable.mall_vincom,
                bannerImageResourceId = R.drawable.mall_vincom_banner
            ),
            Place(
                id = 502,
                categoryId = 5,
                titleResourceId = R.string.mall_lotte,
                shortDescriptionResourceId = R.string.mall_lotte_short,
                detailsResourceId = R.string.mall_lotte_details,
                imageResourceId = R.drawable.mall_lotte,
                bannerImageResourceId = R.drawable.mall_lotte_banner
            ),
            Place(
                id = 503,
                categoryId = 5,
                titleResourceId = R.string.mall_aeon,
                shortDescriptionResourceId = R.string.mall_aeon_short,
                detailsResourceId = R.string.mall_aeon_details,
                imageResourceId = R.drawable.mall_aeon,
                bannerImageResourceId = R.drawable.mall_aeon_banner
            )
        )
    }

    fun getPlacesForCategory(categoryId: Int): List<Place> {
        return getPlacesData().filter { it.categoryId == categoryId }
    }

    fun getPlaceById(placeId: Int): Place? {
        return getPlacesData().find { it.id == placeId }
    }
}
