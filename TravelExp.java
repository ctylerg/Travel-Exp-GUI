//JAVA travel expense GUI
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TravelExp extends JFrame {

    private JPanel panel;  //ref a panel

    private JTextField numDays; //ref a text field
    private JTextField airfare;
    private JTextField rental;
    private JTextField miles;
    private JTextField parking;
    private JTextField taxi;
    private JTextField registration;
    private JTextField hotel;

    private JButton calcButton; //ref a button

    private final int WINDOW_W = 430;
    private final int WINDOW_H = 350;

    private final double MEALS = 37.00;
    private final double PARK = 10.00;
    private final double CAB = 20.00;
    private final double LODGING = 95.00;
    private final double AUTO = 0.27;

    //constructor
    public TravelExp() {
      //set the window title
      setTitle("Business Expenses");
      //set size of the window
      setSize(WINDOW_W, WINDOW_H);
      // what happens on close
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //build panel to add it to frame
      buildPanel();
      //Add panel to frame
      add(panel);
      //display window
      setVisible(true);
    }

    //buildPanel method adds labels,text fields, and buttons to panel
    private void buildPanel() {

      //create labels for instructions
      JLabel instrMessage = new JLabel("You must enter a number for each box, if there was no expense enter 0.");
      JLabel numDaysMessage = new JLabel("Enter number of days of trip:");
      JLabel airfareMessage = new JLabel("Enter the amount of the airfare:");
      JLabel rentalMessage = new JLabel("Enter the amount of the rental car:");
      JLabel milesMessage = new JLabel("Enter the number of miles driven with a private car:");
      JLabel parkingMessage = new JLabel("Enter the total amount spent on parking:");
      JLabel taxiMessage = new JLabel("Enter the total amount spent on taxis:");
      JLabel registrationMessage = new JLabel("Enter the registration fees:");
      JLabel hotelMessage = new JLabel("Enter the total amount spent on lodging:");

      //create text fields
      numDays = new JTextField(3);
      airfare = new JTextField(6);
      rental = new JTextField(6);
      miles = new JTextField(6);
      parking = new JTextField(6);
      taxi = new JTextField(6);
      registration = new JTextField(6);
      hotel = new JTextField(6);

      //create a button
      calcButton = new JButton("Calculate");
      //add action listener
      calcButton.addActionListener(new CalcButtonListener());

      //creat JPanel obj to be referenced
      panel = new JPanel();

      //add label, text field, and button
      panel.add(numDaysMessage);
      panel.add(numDays);
      panel.add(airfareMessage);
      panel.add(airfare);
      panel.add(rentalMessage);
      panel.add(rental);
      panel.add(milesMessage);
      panel.add(miles);
      panel.add(parkingMessage);
      panel.add(parking);
      panel.add(taxiMessage);
      panel.add(taxi);
      panel.add(registrationMessage);
      panel.add(registration);
      panel.add(hotelMessage);
      panel.add(hotel);

      panel.add(calcButton);
    }

    //CalcButtonListener class for calcButton
    private class CalcButtonListener implements ActionListener {

      double days;
      double flight;
      double actualRental;
      double actualMiles;
      double actualParking;
      double actualTaxi;
      double registr;
      double actualHotel;
      double mealsReimb;
      double parkingReimb;
      double taxiReimb;
      double hotelReimb;
  //    double mileReimb;

      public void actionPerformed(ActionEvent e){

      double expenses;
      double reimbursement;
      double owe;
      double savings;
      double maxReimb;
      String msg; //output


      //get data
      getExp();

      //calulate the acutal expenses
      expenses = actualExpenses();

      //calulate the reimbursement
      reimbursement = allowableExpenses();

      //calculate the max reimbursement
      maxReimb = maximumReimbursement();


      msg = String.format("Total Expenses were $%,.2f\n" + "Allowable Expenses for this trip were $%,.2f\n", expenses, reimbursement);

      if(expenses > reimbursement){
        owe = expenses - reimbursement;
        msg = msg + String.format("You were over they daily allowance and owe $%,.2f\n", owe);
      }else if(expenses <= reimbursement){
        savings = maxReimb - reimbursement;
        msg = msg + String.format("You were under the maximum budget by $%,.2f\n", savings);
      }else{
        msg = msg + "You were right on budget.\n";
      }

      JOptionPane.showMessageDialog(null, msg);
      }

      //method to grab input
      private void getExp(){

        days = Double.parseDouble(numDays.getText());
        flight = Double.parseDouble(airfare.getText());
        actualRental = Double.parseDouble(rental.getText());
        actualMiles = Double.parseDouble(miles.getText());
        actualParking = Double.parseDouble(parking.getText());
        actualTaxi = Double.parseDouble(taxi.getText());
        registr = Double.parseDouble(registration.getText());
        actualHotel = Double.parseDouble(hotel.getText());
      }

      //method to total expenses
      private double actualExpenses(){
        double actualExp = (days * MEALS) + flight + actualRental + (actualMiles * AUTO) +
        actualParking + actualTaxi + registr + actualHotel;

        return actualExp;
      }

      private double allowableExpenses(){
        double reimbursement;

        mealsReimb = (days * MEALS);

        if(actualParking > (days * PARK)){
          parkingReimb = (days * PARK);
        }else
        parkingReimb = actualParking;


        if(actualTaxi > (days * CAB)){
          taxiReimb = (days * CAB);
        }else
        taxiReimb = actualTaxi;

        if(actualHotel > (days * LODGING)){
          hotelReimb = (days * LODGING);
        }else
        hotelReimb = actualHotel;

        reimbursement = mealsReimb + flight + actualRental + (actualMiles * AUTO) + parkingReimb +
                  taxiReimb + registr + hotelReimb;

        return reimbursement;
      }

      private double maximumReimbursement(){

          double maxReimb = (days * MEALS) + flight + actualRental + (actualMiles * AUTO) + (days * PARK) + (days * CAB) + registr + (days * LODGING);

          return maxReimb;
      }
    }
    public static void main (String[] args){
      TravelExp reimburse = new TravelExp();
    }
}
