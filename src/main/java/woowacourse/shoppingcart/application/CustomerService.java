package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.DeleteCustomerRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.SignUpResponse;
import woowacourse.shoppingcart.dto.UpdatePasswordRequest;
import woowacourse.shoppingcart.exception.DuplicateEmailException;
import woowacourse.shoppingcart.exception.DuplicateUsernameException;
import woowacourse.shoppingcart.exception.InvalidPasswordException;
import woowacourse.shoppingcart.exception.NoSuchCustomerException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    public SignUpResponse addCustomer(SignUpRequest signUpRequest) {
        validateDuplicateUsername(signUpRequest.getUsername());
        validateDuplicateEmail(signUpRequest.getEmail());

        Customer customer = customerDao.save(signUpRequest.toCustomer());
        return SignUpResponse.fromCustomer(customer);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findMe(String username) {
        Customer customer = customerDao.findByUsername(username);
        return CustomerResponse.from(customer);
    }

    @Transactional
    public void updateMe(String username, UpdatePasswordRequest updatePasswordRequest) {
        validateExistUsername(username);
        validatePassword(username, updatePasswordRequest.getPassword());

        Customer customer = customerDao.findByUsername(username);
        customerDao.updatePassword(customer.getId(), updatePasswordRequest.getNewPassword());
    }

    @Transactional
    public void deleteMe(String username, DeleteCustomerRequest deleteCustomerRequest) {
        validateExistUsername(username);
        validatePassword(username, deleteCustomerRequest.getPassword());

        customerDao.deleteByUsername(username);
    }

    private void validateDuplicateUsername(String username) {
        if (customerDao.existByUsername(username)) {
            throw new DuplicateUsernameException();
        }
    }

    private void validateDuplicateEmail(String email) {
        if (customerDao.existByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }

    private void validateExistUsername(String username) {
        if (!customerDao.existByUsername(username)) {
            throw new NoSuchCustomerException();
        }
    }

    private void validatePassword(String username, String password) {
        if (!customerDao.isValidPasswordByUsername(username, password)) {
            throw new InvalidPasswordException();
        }
    }
}