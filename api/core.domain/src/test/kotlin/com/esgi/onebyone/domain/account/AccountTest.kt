package com.esgi.onebyone.domain.account

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class AccountTest {

    private lateinit var adminAccount: Account
    private lateinit var firstAccount: Account
    private lateinit var similarAccountInName: Account
    private lateinit var similarAccountInEmail: Account
    private lateinit var differentAccount: Account
    lateinit var alreadyCreatedAccount: MutableList<Account>

    @BeforeEach
    fun init() {
        val accountID = AccountID(UUID.randomUUID())
        firstAccount = Account(
            accountID,
            username = "jojo",
            email = "jo@jo.fr",
            password = "moukey",
            role = Role.USER,
        )

        val similarAccountInNameId = AccountID(UUID.randomUUID())
        similarAccountInName = Account(
            similarAccountInNameId,
            username = "jojo",
            email = "jo@ja.fr",
            password = "wiwiwiwiw",
            role = Role.USER,
        )

        val similarAccountInPasswordId = AccountID(UUID.randomUUID())
        similarAccountInEmail = Account(
            similarAccountInPasswordId,
            username = "jaja",
            email = "jo@jo.fr",
            password = "wiwiwiwiw",
            role = Role.USER,
        )

        val differentAccountId = AccountID(UUID.randomUUID())
        differentAccount = Account(
            differentAccountId,
            username = "jajaja",
            email = "ja@ja.fr",
            password = "wiwiwiwiw",
            role = Role.USER,
        )

        val adminAccountId = AccountID(UUID.randomUUID())
        adminAccount = Account(
            adminAccountId,
            username = "jujujuadmin",
            email = "juju@ja.fr",
            password = "wiwiwiwiw",
            role = Role.USER,
        )

        alreadyCreatedAccount = mutableListOf(firstAccount)
    }

    //region create account

    @Test
    fun can_create_account() {
        assertTrue(differentAccount.isUnique(alreadyCreatedAccount))
    }

    @Test
    fun can_not_create_account_if_username_is_identique() {
        assertFalse(similarAccountInName.isUnique(alreadyCreatedAccount))
    }

    @Test
    fun can_not_create_account_if_email_is_identique() {
        assertFalse(similarAccountInEmail.isUnique(alreadyCreatedAccount))
    }

    @Test
    fun should_change_id_to_admin_if_no_admin_yet() {
        adminAccount.setRole(alreadyCreatedAccount)
        assertTrue(adminAccount.role == Role.ADMIN)
    }

    @Test
    fun should_set_id_to_user_if_admin_already_set_in_accounts() {
        adminAccount.setRole(alreadyCreatedAccount)
        alreadyCreatedAccount.add(adminAccount)

        differentAccount.setRole(alreadyCreatedAccount)
        assertTrue(differentAccount.role == Role.USER)


    }



    //endregion
}