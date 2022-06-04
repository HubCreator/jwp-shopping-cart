package woowacourse.shoppingcart.dto;

import woowacourse.auth.dto.PhoneNumber;
import woowacourse.shoppingcart.domain.Customer;

public class CustomerResponse {

    private final long id;
    private final String account;
    private final String nickname;
    private final String address;
    private final PhoneNumber phoneNumber;

    public CustomerResponse(final long id, final String account, final String nickname, final String address, final PhoneNumber phoneNumber) {
        this.id = id;
        this.account = account;
        this.nickname = nickname;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public static CustomerResponse of(final Customer customer) {
        final PhoneNumber phoneNumber = PhoneNumber.of(customer.getPhoneNumber());
        return new CustomerResponse(customer.getId(), customer.getAccount().getValue(), customer.getNickname(), customer.getAddress(), phoneNumber);
    }

    public long getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAddress() {
        return address;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }
}