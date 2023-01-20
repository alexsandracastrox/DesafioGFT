package Entities;

public class Booking {
   private String firstName;
   private String lastName;
   private Float totalPrice;
   private Boolean depositPaid;
   private BookingDates bookingDates;
   private String additionalNeeds;

   public String getFirstname() {
      return firstName;
   }

   public void setFirstname(String firstname) {
      this.firstName = firstname;
   }

   public String getLastname() {
      return lastName;
   }

   public void setLastname(String lastName) {
      this.lastName = lastName;
   }

   public Float getTotalprice() {
      return totalPrice;
   }

   public void setTotalprice(Float totalPrice) {
      this.totalPrice = totalPrice;
   }

   public Boolean getDepositpaid() {
      return depositPaid;
   }

   public void setDepositpaid(Boolean depositPaid) {
      this.depositPaid = depositPaid;
   }

   public BookingDates getBookingdates() {
      return bookingDates;
   }

   public void setBookingdates(BookingDates bookingdates) {
      this.bookingDates = bookingdates;
   }

   public String getAdditionalneeds() {
      return additionalNeeds;
   }

   public void setAdditionalneeds(String additionalNeeds) {
      this.additionalNeeds = additionalNeeds;
   }

   public Booking(String firstname, String lastName, Float totalPrice, Boolean depositpaid, BookingDates bookingDates, String additionalNeeds) {
      this.firstName = firstname;
      this.lastName = lastName;
      this.totalPrice = totalPrice;
      this.depositPaid = depositpaid;
      this.bookingDates = bookingDates;
      this.additionalNeeds = additionalNeeds;
   }
}