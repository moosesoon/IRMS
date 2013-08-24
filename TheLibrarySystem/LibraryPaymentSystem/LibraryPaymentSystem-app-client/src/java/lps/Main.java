package lps;

/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignmentt IS2104 EJB
 */
import java.util.Scanner;
import javax.ejb.EJB;
import lps.PaymentManagerRemote;

public class Main {
    @EJB
    private static PaymentManagerRemote pmr;
    
    public Main(){}

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        String input;
        Long mid = new Long(0);
        double amtPaid = 0.0;
        //PART 1
        //Request for member ID
        System.out.print("\t\t---===Library Payment System===---" +
                         "\n\nPlease enter your member ID: ");
        input = scn.next();
        try{
            mid = Long.parseLong(input);
        } catch(NumberFormatException ex){
            System.err.println("Number Format Exception occurred");
        }
        
        //Set up connection
        pmr.setMember(mid);
        pmr.doDisplay();
        pmr.receive();
        //Display for part 1
        System.out.print("\n\n\t\t---===Amount Owed===---" +
                         "\n\nName: " + pmr.getName() +
                         "\nAmount Owed: " + pmr.getAmtOwed());

        //Part 2 - Pay
        System.out.print("\nPlease enter amount to pay: ");
        input = scn.next();
        try{
            amtPaid = Double.parseDouble(input);
        } catch(NumberFormatException nfe){
            System.out.println("Number Format Exception in main. Unable to parse double");
            nfe.printStackTrace();
        }
        pmr.setPayment(amtPaid);
        pmr.doPayment();
        pmr.receive();
        //Display for part 2
        System.out.print("\n\n\t\t---===Amount Owed===---" +
                     "\n\nName: " + pmr.getName() +
                     "\nAmount Owed: " + pmr.getAmtOwed());
        System.out.print("\nSystem exiting....");
    }
}
