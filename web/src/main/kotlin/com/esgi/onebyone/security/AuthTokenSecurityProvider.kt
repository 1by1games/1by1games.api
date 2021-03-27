package com.esgi.onebyone.security


import com.esgi.onebyone.application.AccountsService
import com.esgi.onebyone.application.ApplicationException
import com.esgi.onebyone.domain.account.Role
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service


@Service
class AuthTokenSecurityProvider(private val accountsService: AccountsService) : AuthenticationProvider {
    @Throws(AuthenticationException::class)
    override fun authenticate(auth: Authentication?): Authentication? {
        if (auth == null) {
            return null
        }
        val name: String = auth.name
        if (name.isEmpty()) {
            return null
        }
        val grantedAuths: MutableList<GrantedAuthority> = ArrayList()
        try {
            val userResume = accountsService.getResumeByUsername(name)
            userResume?.let {
                grantedAuths.clear()
                when (userResume.role) {
                    Role.USER -> grantedAuths.add(SimpleGrantedAuthority("ROLE_USER"))
                    Role.ADMIN -> grantedAuths.add(SimpleGrantedAuthority("ROLE_ADMIN"))
                }
                val retVal = UsernamePasswordAuthenticationToken(
                    name, "UserAuthenticated", grantedAuths
                )
                println("Add Auth - User Name: " + retVal.name)
                println("Add Auth - Roles Count: " + retVal.authorities?.size ?: 0)
                return retVal
            }
            return null
        } catch (e: ApplicationException) {
            return null
        }
    }

    override fun supports(tokenClass: Class<*>): Boolean {
        return tokenClass == UsernamePasswordAuthenticationToken::class.java
    }
}