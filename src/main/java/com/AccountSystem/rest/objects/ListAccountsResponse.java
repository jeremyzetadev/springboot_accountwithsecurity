

package com.vikingbank.rest.objects;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;


import java.util.List;


@JsonSerialize

public class ListAccountsResponse {

    private final List<RestAccount> accounts;


    public ListAccountsResponse(List<RestAccount> restAccountList) {

        this.accounts = restAccountList;

    }


    public List<RestAccount> getAccounts() {

        return accounts;

    }

}
