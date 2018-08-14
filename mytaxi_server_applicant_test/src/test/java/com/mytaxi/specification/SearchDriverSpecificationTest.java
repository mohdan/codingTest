package com.mytaxi.specification;

import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mytaxi.MytaxiServerApplicantTestApplication;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.domainvalue.car.CarRating;
import com.mytaxi.filter.SearchDriverFilter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MytaxiServerApplicantTestApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class SearchDriverSpecificationTest
{

    private SearchDriverSpecification searchDriverSpecification;
    private CriteriaBuilder criteriaBuilderMock;
    private CriteriaQuery criteriaQueryMock;
    private Predicate predicateMock;
    private Root<DriverDO> driverDORootMock;
    private Path<DriverDO> driverDOPathMock;


    /*
     * private Root<CarDO> carDORootMock; private Path<CarDO> carDOPathMock;
     */
    @Before
    public void setUp()
    {
        criteriaBuilderMock = Mockito.mock(CriteriaBuilder.class);
        criteriaQueryMock = Mockito.mock(CriteriaQuery.class);
        predicateMock = Mockito.mock(Predicate.class);
        driverDORootMock = Mockito.mock(Root.class);
        driverDOPathMock = Mockito.mock(Path.class);

    }


    @Test
    public void toPredicateNullTest()
    {
        SearchDriverFilter searchDriverFilter = new SearchDriverFilter();
        searchDriverSpecification = new SearchDriverSpecification(searchDriverFilter);
        when(criteriaBuilderMock.conjunction()).then(((invocation) -> {
            return predicateMock;
        }));
        Assert.assertNotNull(
            searchDriverSpecification.toPredicate(driverDORootMock, criteriaQueryMock, criteriaBuilderMock));

    }


    public void toPredicateTest()
    {
        String licensePlate = "MH11-1111";
        String manufacturerName = "Manu";
        OnlineStatus onlineStatus = OnlineStatus.ONLINE;
        CarRating rating = CarRating.FIVE_STAR;
        String username = "driver";

        SearchDriverFilter servPermHistoryFilter = new SearchDriverFilter();
        // DriverDO attributes
        servPermHistoryFilter.setUsername(username);
        servPermHistoryFilter.setOnlineStatus(onlineStatus);
        // CarDO attributes
        servPermHistoryFilter.setLicensePlate(licensePlate);
        servPermHistoryFilter.setRating(rating);
        // ManufacturerDO attribute
        servPermHistoryFilter.setManufacturerName(manufacturerName);

        searchDriverSpecification = new SearchDriverSpecification(servPermHistoryFilter);

        when(criteriaBuilderMock.conjunction()).then(((invocation) -> {
            return predicateMock;
        }));

        when(predicateMock.getExpressions()).then(((invocation) -> {
            return new ArrayList<Expression<Boolean>>();
        }));

        when(driverDORootMock.get(searchDriverSpecification.USERNAME)).then(((invocation) -> {
            return driverDOPathMock;
        }));

        when(driverDORootMock.get(searchDriverSpecification.ONLINE_STATUS)).then(((invocation) -> {
            return driverDOPathMock;
        }));

        when(driverDORootMock.get(searchDriverSpecification.CAR)).then(((invocation) -> {
            return driverDOPathMock;
        }));

        when(driverDORootMock.get(searchDriverSpecification.LICENSE_PLATE)).then(((invocation) -> {
            return driverDOPathMock;
        }));

        when(driverDORootMock.get(searchDriverSpecification.RATING)).then(((invocation) -> {
            return driverDOPathMock;
        }));

        when(driverDORootMock.get(searchDriverSpecification.MANUFACTURER)).then(((invocation) -> {
            return driverDOPathMock;
        }));
        when(driverDORootMock.get(searchDriverSpecification.NAME)).then(((invocation) -> {
            return driverDOPathMock;
        }));

        Assert.assertNotNull(
            searchDriverSpecification.toPredicate(driverDORootMock, criteriaQueryMock, criteriaBuilderMock));

    }

}
