import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    public Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void setup() {
    	LocalTime openingTime = LocalTime.parse("10:00:00");
        LocalTime closingTime = LocalTime.parse("12:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
    	Restaurant mockedrestaurant = Mockito.mock(Restaurant.class);
    	mockedrestaurant.closingTime = restaurant.closingTime;
    	mockedrestaurant.openingTime =restaurant.openingTime;
    	LocalTime OneHourBeforeClosingTime = LocalTime.of(11, 0);
    	Mockito.when(mockedrestaurant.getCurrentTime()).thenReturn(OneHourBeforeClosingTime);
    	Mockito.when(mockedrestaurant.isRestaurantOpen()).thenCallRealMethod();

        assertTrue(mockedrestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
    	Restaurant mockedrestaurant = Mockito.mock(Restaurant.class);
    	mockedrestaurant.closingTime = restaurant.closingTime;
    	mockedrestaurant.openingTime =restaurant.openingTime;
    	LocalTime OneHourBeforeOpeningTime = LocalTime.of(9, 0);
    	Mockito.when(mockedrestaurant.getCurrentTime()).thenReturn(OneHourBeforeOpeningTime);
    	Mockito.when(mockedrestaurant.isRestaurantOpen()).thenCallRealMethod();

        assertFalse(mockedrestaurant.isRestaurantOpen());
    	
    	LocalTime OneHourAfterClosingTime = LocalTime.of(12, 0);
    	Mockito.when(mockedrestaurant.getCurrentTime()).thenReturn(OneHourAfterClosingTime);

        assertFalse(mockedrestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

    	int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    
    //>>>>>>>>>>>>>>>>>>>>>>ORDER<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void calculating_total_order_value_for_existing_item_should_return_expected_value() throws  itemNotFoundException {
        //WRITE UNIT TEST CASE HERE
    	List<String> items = new ArrayList<>();
    	items.add("Sweet corn soup");
    	items.add("Vegetable lasagne");
        assertEquals(388, restaurant.getTotalOrderValue(items));
    }

    //You may watch the video by Muthukumaran on how to write exceptions in Course 3: Testing and Version control: Optional content
    @Test
    public void calculating_total_order_value_for_non_existing_item_should_throw_exception() throws itemNotFoundException {
        //WRITE UNIT TEST CASE HERE
    	List<String> items = new ArrayList<>();
    	items.add("French fries");
    	assertThrows(itemNotFoundException.class,
                ()->restaurant.getTotalOrderValue(items));
    }
    //<<<<<<<<<<<<<<<<<<<<ORDER>>>>>>>>>>>>>>>>>>>>>>>>>>
}