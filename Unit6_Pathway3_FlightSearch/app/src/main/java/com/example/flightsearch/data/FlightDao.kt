package com.example.flightsearch.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDao {

    // Tìm kiếm sân bay theo tên hoặc IATA code
    @Query("""
        SELECT * FROM airport 
        WHERE name LIKE '%' || :searchQuery || '%' 
        OR iata_code LIKE '%' || :searchQuery || '%'
        ORDER BY passengers DESC
    """)
    fun searchAirports(searchQuery: String): Flow<List<Airport>>

    // Lấy tất cả sân bay trừ sân bay hiện tại (để hiển thị các điểm đến)
    @Query("""
        SELECT * FROM airport 
        WHERE iata_code != :departureCode
        ORDER BY passengers DESC
    """)
    fun getDestinationAirports(departureCode: String): Flow<List<Airport>>

    // Lấy thông tin sân bay theo IATA code
    @Query("SELECT * FROM airport WHERE iata_code = :iataCode")
    suspend fun getAirportByCode(iataCode: String): Airport?

    // Lấy tất cả chuyến bay yêu thích
    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<List<Favorite>>

    // Kiểm tra xem chuyến bay đã được yêu thích chưa
    @Query("""
        SELECT * FROM favorite 
        WHERE departure_code = :departureCode 
        AND destination_code = :destinationCode
    """)
    fun getFavorite(departureCode: String, destinationCode: String): Flow<Favorite?>

    // Thêm chuyến bay yêu thích
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favorite: Favorite)

    // Xóa chuyến bay yêu thích
    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    // Xóa chuyến bay yêu thích theo departure và destination code
    @Query("""
        DELETE FROM favorite 
        WHERE departure_code = :departureCode 
        AND destination_code = :destinationCode
    """)
    suspend fun deleteFavoriteByCode(departureCode: String, destinationCode: String)
}