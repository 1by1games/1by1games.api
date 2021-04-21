package com.esgi.onebyone.security

import com.esgi.onebyone.application.security.parse_token.ParseTokenQuery
import com.esgi.onebyone.security.SecurityConstants.HEADER_STRING
import com.esgi.onebyone.security.SecurityConstants.TOKEN_PREFIX
import io.jkratz.mediator.core.Mediator
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JWTAuthorizationFilter(private val authManager: AuthenticationManager, private val mediator : Mediator) : GenericFilterBean() {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val req = request as? HttpServletRequest
        val res = response as? HttpServletResponse
        req?.let {
            val header = req.getHeader(HEADER_STRING)
            if (header == null || !header.startsWith(TOKEN_PREFIX)) {
                chain?.doFilter(req, res)
                return
            }
            val authentication = getAuthentication(req)
            if(authentication == null){
                res?.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                return
            }
            val authCool = authManager.authenticate(authentication)
            SecurityContextHolder.getContext().authentication = authCool
            chain?.doFilter(req, res)
        }
    }


    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader(HEADER_STRING)
        if (token != null) {
            // parse the token.
            try {
                val user = mediator.dispatch(ParseTokenQuery(token))
                return UsernamePasswordAuthenticationToken(user, null, ArrayList())
            } catch (e: Exception) {
                return null
            }

        }
        return null
    }


}