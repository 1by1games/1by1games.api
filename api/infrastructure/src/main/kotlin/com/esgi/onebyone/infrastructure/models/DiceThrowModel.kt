package com.esgi.onebyone.infrastructure.models

import org.hibernate.annotations.Type
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table( name = "dice_throw")
class DiceThrowModel (

    @Id
    @Type(type = "uuid-char")
    @Column(name = "id")
    var id: UUID?,

    @Column(name = "size")
    var size: Int,

    @Column(name = "amount")
    var amount: Int,

    @Column(name = "result")
    var result: Int,

    @Column(name = "throw_date")
    var throwDate : LocalDateTime,

    @ManyToOne
    @Type(type = "uuid-char")
    @JoinColumn(name = "member_id")
//    @MapsId("memberId")
    var member: MemberModel
)