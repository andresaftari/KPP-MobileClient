package com.kppmining.core.data

import com.kppmining.core.data.remote.RemoteDataSource
import com.kppmining.core.domain.repo.IDomainRepository

class DataRepository(
    private val remote: RemoteDataSource
) : IDomainRepository {

}