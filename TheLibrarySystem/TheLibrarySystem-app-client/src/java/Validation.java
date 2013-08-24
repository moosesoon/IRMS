/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignment IS2104 EJB
 */
public class Validation {

    public Validation(){}

        //Validation: Must be a number, and more than a but less than b
    public int validateInput(String choice, int a, int b){
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

    public Long validateLong(String choice){
        Long temp;
        for(int i = 0; i < choice.length(); i++){
            if(!Character.isDigit(choice.charAt(i))){ //There is a character which is not a digit
                System.out.println("\nPlease enter a valid input");
                temp = new Long(-1);
                return temp;
            }
        }
        temp = Long.parseLong(choice);
        return temp;
    }
}
