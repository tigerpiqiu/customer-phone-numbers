package au.com.belong.service.phonenumbers.persistence;

import au.com.belong.service.phonenumbers.domain.CustomerPhoneNumber;
import au.com.belong.service.phonenumbers.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerPhoneNumberDAO {

    private static final String SELECT_BASE =
            "SELECT id, customer_id, phone_number, is_active FROM customer_phone_number";

    private static final String SELECT_PHONE_NUMBERS_BY_CUSTOMER_ID = SELECT_BASE
            + " WHERE customer_id = :customerId";

    private static final String SELECT_BY_PHONE_NUMBER = SELECT_BASE
            + " WHERE phone_number = :phoneNumber";

    private static final String UPDATE_PHONE_NUMBER =
            "UPDATE customer_phone_number SET customer_id = :customerId, phone_number = :phoneNumber," +
                    " is_active = :isActive WHERE id=:id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final CustomerPhoneNumberRowMapper customerPhoneNumberRowMapper = new CustomerPhoneNumberRowMapper();

    @Autowired
    public CustomerPhoneNumberDAO(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CustomerPhoneNumber> getAllPhoneNumbers() {
        return jdbcTemplate.query(SELECT_BASE, customerPhoneNumberRowMapper);
    }

    public List<CustomerPhoneNumber> getPhoneNumbersByCustomerId(long customerId) {
        return jdbcTemplate.query(SELECT_PHONE_NUMBERS_BY_CUSTOMER_ID,
                new MapSqlParameterSource("customerId", customerId), customerPhoneNumberRowMapper);
    }

    public CustomerPhoneNumber getByPhoneNumber(String phoneNumber) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_PHONE_NUMBER,
                    new MapSqlParameterSource("phoneNumber", phoneNumber), customerPhoneNumberRowMapper);
        } catch (EmptyResultDataAccessException ignored) {
            throw new NotFoundException(String.format("Phone number %s not found", phoneNumber));
        }
    }

    @Transactional
    public void update(CustomerPhoneNumber customerPhoneNumber) {
        jdbcTemplate.update(UPDATE_PHONE_NUMBER, updateSqlParameterSource(customerPhoneNumber));
    }

    private SqlParameterSource updateSqlParameterSource(CustomerPhoneNumber customerPhoneNumber) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("id", customerPhoneNumber.getId());
        sqlParameterSource.addValue("customerId", customerPhoneNumber.getCustomerId());
        sqlParameterSource.addValue("phoneNumber", customerPhoneNumber.getPhoneNumber());
        sqlParameterSource.addValue("isActive", customerPhoneNumber.isActive());
        return sqlParameterSource;
    }

    private static class CustomerPhoneNumberRowMapper implements RowMapper<CustomerPhoneNumber> {
        @Override
        public CustomerPhoneNumber mapRow(ResultSet rs, int rowNum) throws SQLException {
            return CustomerPhoneNumber.builder()
                    .id(rs.getLong("id"))
                    .customerId(rs.getLong("customer_id"))
                    .phoneNumber(rs.getString("phone_number"))
                    .isActive(rs.getBoolean("is_active"))
                    .build();
        }
    }
}
