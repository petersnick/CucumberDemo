Feature: TC-102

  TC -102
  To verify whether the check-out date field accepts a later date than check-in date.
  1. Launch hotel reservation application using URL as in test data.
  2. Login to the application using username and password as in test data.
  3. Select location as in test data.
  4. Select hotel as in test data.
  5. Select room type as in test data.
  6. Select no-of-rooms as in test data.
  7. Enter check-in-date later than the check-out-date field as in test data.
  8. Verify that system gives an error saying �check-in-date should not be later than check-out-date�.
  URL: http://adactin.com/HotelApp/index.php
  User:{test username}
  Password:{test password}
  Location: Sydney
  Hotel: Hotel Creek
  Room type: standard
  No-of-rooms:1
  Check-in-date: today + 7 date
  Checkout date:today +5 date
  System should report an error message.

Scenario Outline: verify whether the check-out date field accepts a later date than check-in date
  Given I am on the adactin site
  And I log in with my credentials
  And I am logged in
  When I set the location to "<Location>"
  And I select Hotel "<Hotels>"
  And I select Room type "<Roomtype>"
  And I select the number of rooms "<NumberRooms>"
  And I select the amount of adults "<AdultsPerRoom>"
  And the day that I check in is "7" days from now
  And the day that I check out is "5" days from now
  Then the search results in an error message
Examples:
  | Location | Hotels       | Roomtype | NumberRooms | AdultsPerRoom |
  |Sydney    |Hotel Creek   |Standard  |1            |1              |
  |London    |Hotel Sunshine|Double    |2            |3              |