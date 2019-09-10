package net.sunrise.repository.sales;

import org.springframework.data.domain.*;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.sales.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

  Account findByNumber(String number);

}
