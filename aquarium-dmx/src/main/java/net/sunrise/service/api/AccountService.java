package net.sunrise.service.api;

import net.sunrise.domain.entity.sales.Account;
import net.sunrise.exceptions.ObjectNotFoundException;

public interface AccountService {

    /**
     * Finds the account with the provided account number.
     * 
     * @param number The account number
     * @return The account
     * @throws ObjectNotFoundException If no such account exists.
     */
    Account findOne(String number) throws ObjectNotFoundException;

    /**
     * Creates a new account.
     * 
     * @param number
     * @return created account
     */
    Account createAccountByNumber(String number);
}
