package com.ironyard;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by illladell on 6/22/16.
 */
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
