package com.esgi.onebyone.infrastructure.repositories

import com.esgi.onebyone.application.accounts.repositories.IAccountsRepository
import com.esgi.onebyone.application.accounts.repositories.IRoomRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RoomRepositoryAdapter @Autowired constructor(private val repository: RoomRepository) : IRoomRepository {
}