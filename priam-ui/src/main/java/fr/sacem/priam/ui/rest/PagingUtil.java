package fr.sacem.priam.ui.rest;

  
  import org.springframework.data.domain.Page;
  import org.springframework.data.domain.PageImpl;
  import org.springframework.data.domain.PageRequest;
  import org.springframework.data.domain.Pageable;
  import org.springframework.data.domain.Sort;
  
  import java.util.ArrayList;
  import java.util.List;
  import java.util.Map;
  
  import static com.google.common.base.MoreObjects.firstNonNull;

/**
 * Created by Guillaume on 26/01/2015.
 */
public class PagingUtil {
  
  /**
   * When using paging and sorting on calculated fields, Spring Data adds 'orderby' clause with main entity alias prefix
   * which is wrong on calculated fields
   * To avoid this, encapsulate parenthesis over required field property
   *
   * This problem is described here :
   * https://stackoverflow.com/questions/28149174/spring-data-paginating-and-sorting-on-calculated-fields
   *
   * @return the same pageable with fields encapsulated between parenthesis
   */
  public static Pageable parenthesisEncapsulation(Pageable pageable) {
    
    List<Sort.Order> orders = new ArrayList<>() ;
    for (Sort.Order order : pageable.getSort()) {
      String encapsulatedProperty = "("+order.getProperty()+")" ;
      orders.add( new Sort.Order(order.getDirection(), encapsulatedProperty));
    }
    
    return new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(orders)) ;
  }
  
  public static Pageable convertSortColumn(Pageable pageable, Map<String,String> map) {
    
    if ( pageable.getSort() == null ) {
      return pageable ;
    }
    
    List<Sort.Order> orders = new ArrayList<>() ;
    for (Sort.Order order : pageable.getSort()) {
      String converted = firstNonNull(map.get(order.getProperty()), order.getProperty()) ;
      orders.add( new Sort.Order(order.getDirection(), converted) );
    }
    
    return new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(orders)) ;
  }
  
  public static Pageable addSort(Pageable pageable, Sort.Direction direction, String property) {
    
    List<Sort.Order> orders = new ArrayList<>() ;
    for (Sort.Order order : pageable.getSort()) {
      orders.add( new Sort.Order(order.getDirection(), order.getProperty()));
    }
    orders.add( new Sort.Order(direction, property));
    
    return new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(orders)) ;
  }
  
  /**
   * @See parenthesisEncapsulation
   * Create a new page with original sorted properties
   */
  public static <T> Page<T> originalSorting(Page<T> result, Pageable original) {
    return new PageImpl<T>(result.getContent(), original, result.getTotalElements() ) ;
  }
}

