package com.mytaxi.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.filter.SearchDriverFilter;

public class SearchDriverSpecification implements Specification<DriverDO>
{

    /**
     * Below are the attributes name from DO 
     * for driver, car and manufacturer
     * in order to search for driver base on these
     */
    private static final long serialVersionUID = 1L;

    public static final String ONLINE_STATUS = "onlineStatus";
    public static final String USERNAME = "username";
    public static final String CAR = "car";
    public static final String LICENSE_PLATE = "licensePlate";
    public static final String RATING = "rating";
    public static final String MANUFACTURER = "manufacturer";
    public static final String NAME = "name";

    private final SearchDriverFilter searchDriverFilter;


    public SearchDriverSpecification(SearchDriverFilter searchDriverFilter)
    {
        super();
        this.searchDriverFilter = searchDriverFilter;
    }

    /**
     * Allows to generate SearchDriverSpecification 
     * by using SearchFiler details which has
     * attributes of drivers like username, onlineStatus
     * attributes of cars like licensePlate, rating
     * attributes of manufacturer like name
     * 
     * @param uuid
     * @param username
     * @param onlineStatus
     * @param licensePlate
     * @param rating
     * @param manufacturerName        
     */
    @Override
    public Predicate toPredicate(Root<DriverDO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteraBuilder)
    {
        Predicate predicate = criteraBuilder.conjunction();
        if (searchDriverFilter.getOnlineStatus() != null)
        {
            predicate.getExpressions()
                .add(criteraBuilder.equal(root.get(ONLINE_STATUS), searchDriverFilter.getOnlineStatus()));
        }

        if (searchDriverFilter.getUsername() != null)
        {
            predicate.getExpressions().add(criteraBuilder.equal(root.get(USERNAME), searchDriverFilter.getUsername()));
        }

        if (searchDriverFilter.getLicensePlate() != null)
        {
            predicate.getExpressions()
                .add(criteraBuilder.equal(root.get(CAR).get(LICENSE_PLATE), searchDriverFilter.getLicensePlate()));
        }

        if (searchDriverFilter.getRating() != null)
        {
            predicate.getExpressions()
                .add(criteraBuilder.equal(root.get(CAR).get(RATING), searchDriverFilter.getRating()));
        }

        if (searchDriverFilter.getManufacturerName() != null)
        {
            predicate.getExpressions().add(criteraBuilder.equal(root.get(CAR).get(MANUFACTURER).get(NAME),
                searchDriverFilter.getManufacturerName()));
        }

        return predicate;
    }

}
