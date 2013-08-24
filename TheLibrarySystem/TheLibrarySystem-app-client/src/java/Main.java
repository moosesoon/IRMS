/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignment IS2104 EJB
 */
import java.util.Scanner;
import java.io.IOException;

public class Main {

    public Main(){}

    public static void main(String[] args) throws IOException {
        //Initialization
        String choice;
        int temp;
        Scanner scn = new Scanner(System.in);
        Main client = new Main();

        do {//Start while
            //Print out the interface
            System.out.println("\n\n\t\t---===Welcome to TLS: Administrator===---" +
                           "\n-Menu-" +
                           "\n1.Member Menu" +
                           "\n2.Title / Book Menu" +
                           "\n3.Loan and Borrow Menu" +
                           "\n0.Exits");
            System.out.print("Please enter your choice: ");
            choice = scn.next();
            if((temp = validateInput(choice, 0, 4)) < 0){
                continue;
            }

            switch(temp){
                case 1:
                    client.doMember();
                    break;
                case 2:
                    client.doTitle();
                    break;
                case 3:
                    client.doLoan();
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 0:
                    System.out.println("Program Terminated! Have a nice day!");
                    System.exit(0);
            }
        } while(temp > 0);
    }

    public void doMember(){
        MemberView mv = new MemberView();
        mv.mainWindow();
    }

    public void doTitle() throws IOException{
        TitleView tv = new TitleView();
        tv.mainWindow();
    }

    public void doLoan(){
        LoanView lv = new LoanView();
        lv.mainWindow();
    }

    //Validation: Must be a number, and more than a but less than b
    public static int validateInput(String choice, int a, int b){
        int temp;
        for(int i = 0; i < choice.length(); i++){
            if(!Character.isDigit(choice.charAt(i))){ //There is a character which is not a digit
                System.out.println("\nPlease enter a valid input");
                return -1;
            }
        }
        temp = Integer.parseInt(choice);
        if(temp < a || temp > b){
            System.out.println("\nPlease enter a valid input");
            return -1;
        }
        return temp;
    }
    //End validation
}