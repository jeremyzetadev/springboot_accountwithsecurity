

package com.vikingbank.repositories;


import com.vikingbank.entities.Account;

import org.springframework.stereotype.Repository;


import java.util.List;


@Repository

public interface CustomAccountRepository {

    List<Account> listAccounts(String name);

}
