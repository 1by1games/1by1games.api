package com.esgi.onebyone.security

import com.esgi.onebyone.application.AccountsService
import com.esgi.onebyone.application.ApplicationException
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class AuthService(private val accountsService: AccountsService) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        try {
            accountsService.getCredentialByUsername(username)
                .let { credential -> return User(credential.username, credential.password, emptyList()) }
        } catch (e: ApplicationException) {
            throw ApplicationException(username)
        }
    }

}