package coms309.people;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonTest {
    Person test;

    @BeforeEach
    public void beforeEach() {
        test = new Person(null, null, null, null);
    }

    @Test
    @DisplayName("Setting the first name")
    public void setFirstNameTest() {
        assertEquals(null, test.getFirstName(), "First name was set to null by the constructor and getFirstName() != null.");
        test.setFirstName("Jacob");
        assertEquals("Jacob", test.getFirstName(), "First name was set to 'Jacob' by setFirstName() and getFirstName() != 'Jacob'");
    }

    @Test
    @DisplayName("Setting the last name")
    public void setLastNameTest() {
        assertEquals(null, test.getLastName(), "Last name was set to null by the constructor and getLastName() != null.");
        test.setLastName("Duba");
        assertEquals("Duba", test.getLastName(), "Last name was set to 'Duba' by setLastName() and getLastName() != 'Duba'");
    }

    @Test
    @DisplayName("Setting the address")
    public void setAddressTest() {
        assertEquals(null, test.getAddress(), "Address was set to null by the constructor and getAddress() != null.");
        test.setAddress("660");
        assertEquals("660", test.getAddress(), "Address was set to '660' by setLastName() and getLastName() != '660'");
    }

    @Test
    @DisplayName("Setting the telephone")
    public void setTelephoneTest() {
        assertEquals(null, test.getTelephone(), "Telephone was set to null by the constructor and getTelephone() != null.");
        test.setTelephone("708");
        assertEquals("708", test.getTelephone(), "Telephone was set to '708' by setTelephone() and getTelephone() != '660'");
    }

    @Test
    @DisplayName("Testing the toString")
    public void toStringTest() {
        assertEquals("null null null null", test.toString());
    }
}
