package com.mufiid.composestockmarketapp.data.repository

import com.mufiid.composestockmarketapp.data.local.dao.StockDao
import com.mufiid.composestockmarketapp.data.mapper.toDomain
import com.mufiid.composestockmarketapp.data.remote.StockApi
import com.mufiid.composestockmarketapp.domain.model.CompanyListing
import com.mufiid.composestockmarketapp.domain.repository.StockRepository
import com.mufiid.composestockmarketapp.utils.FlowResource
import com.mufiid.composestockmarketapp.utils.Resource
import com.opencsv.CSVReader
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val dao: StockDao
): StockRepository {
    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String,
    ): FlowResource<List<CompanyListing>> {
        return flow {
            emit(Resource.Loading())
            val localListings = dao.searchCompanyListings(query)
            emit(Resource.Success(
                data = localListings.map { it.toDomain() }
            ))

            val isLocalEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isLocalEmpty && !fetchFromRemote

            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try {
                val response = api.getListings()
                val csvReader = CSVReader(InputStreamReader(response.byteStream()))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            }
        }
    }
}