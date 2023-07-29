package com.mufiid.composestockmarketapp.data.mapper

import com.mufiid.composestockmarketapp.data.local.CompanyListingEntity
import com.mufiid.composestockmarketapp.domain.model.CompanyListing

fun CompanyListingEntity.toDomain() = CompanyListing(name, symbol, exchange)

fun CompanyListing.toEntity() =
    CompanyListingEntity(name = name, symbol = symbol, exchange = exchange)