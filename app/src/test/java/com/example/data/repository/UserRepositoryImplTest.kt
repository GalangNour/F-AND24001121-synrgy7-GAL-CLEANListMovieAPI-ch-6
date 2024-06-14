package com.example.data.repository

import com.example.data.database.UserDao
import com.example.data.database.UserEntity
import com.example.domain.model.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {

    private lateinit var dao : UserDao
    private lateinit var repository : UserRepositoryImpl
    private lateinit var user : User
    private lateinit var userEntity : UserEntity
    private val username = String()
    private val password = String()
    private val email = String()


    @Before
    fun setUp() {
        dao = mockk()
        user = mockk()
        userEntity = mockk()
        repository = UserRepositoryImpl(dao)
    }

    @Test
    fun register() : Unit = runBlocking {

        coEvery {
            dao.register(username, password, email)
        } returns Unit

        repository.register(username, password, email)

        coVerify {
            dao.register(username, password, email)
        }


    }

    @Test
    fun getUser() : Unit = runBlocking {

        coEvery {
            dao.getUser(username)
        } returns userEntity

        coEvery {
            userEntity.toDomain()
        } returns user

        repository.getUser(username)

        coVerify {
            dao.getUser(username)
        }
    }

    @Test
    fun updateUser() : Unit = runBlocking {

        coEvery {
            dao.updateUser(username, password, email, username)
        } returns Unit

        repository.updateUser(username, password, email, username)

        coVerify {
            dao.updateUser(username, password, email, username)
        }
    }

}