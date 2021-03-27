package com.esgi.onebyone.infrastructure

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "account")
class Account(
    @Id
    @Type(type = "uuid-char")
    @Column(name = "id") var id: UUID,

    @Column(name = "username") var username: String,

    @Column(name = "email") var email: String,

    @Column(name = "password") var password: String
) {


}