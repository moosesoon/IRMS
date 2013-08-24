/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignment IS2104 EJB
 */
import appHelper.MemberState;
import java.util.Scanner;
import tlb.MemberManagerRemote;
import javax.naming.InitialContext;

public class MemberView {
    private Scanner scn = new Scanner(System.in);
    private MemberManagerRemote mcm;
    private Validation v = new Validation();

    public MemberView(){
        getSessionBean();
    }

    //M. Display main menu for Members
    public void mainWindow(){
        String choice;
        int temp;

        do{
            System.out.println("\n\n\t\t---===Member Menu===---" +
                               "\n\n-Menu-" +
                               "\n1.Add Members" +
                               "\n2.Delete Members" +
                               "\n3.List Members" +
                               "\n0.Go back Main Menu");
            System.out.print("Please enter your choice: ");
            choice = scn.next();
            temp = v.validateInput(choice, 0, 4);

            switch(temp){
                case 1:
                    doRegister();
                    break;
                case 2:
                    doTerminate();
                    break;
                case 3:
                    doDisplayMember("");
                    break;
                case 0:
                    temp = -1;
                    break;
            }
        }while(temp > 0);
        mcm.remove();
    }

    //1. Add Members
    private void doRegister(){
        //Initialise required variables
        String name, password, type, dept, faculty, email, phone;
        int temp;

        //Print interface step 1 name
        System.out.print("\n\n---===Member Registration===---" +
                           "\n\nEnter name: ");
        name = scn.next();

        //Step 2 type
        do{//Start while
            System.out.print("Enter type(1.Staff 2.Student): ");
            type = scn.next();
            temp = v.validateInput(type, 0, 3);
            switch(temp){
                case 1:
                    type = "Staff";
                    break;
                case 2:
                    type = "Student";
                    break;
            }
        } while(temp < 0);

        //Step 3 password
        System.out.print("Enter password(Not encrypted): ");
        password = scn.next();

        //Contact
        System.out.print("Enter Department: ");
        dept = scn.next();
        System.out.print("Enter Faculty: ");
        faculty = scn.next();
        System.out.print("Enter Email: ");
        email = scn.next();
        do{//Start while
            System.out.print("Enter Phone Number: ");
            phone = scn.next();
            if((temp = v.validateInput(phone, 1, 99999999)) < 0){
                continue;
            }
            if(phone.length() < 1) {
                System.out.println("\nPlease enter a valid input");
                continue;
            } else{
                break;
            }
        } while(true);

        //Create Member
        mcm.createMember(name, type, password);
        //Create Contact
        mcm.createContact(dept, faculty, email, temp);
        mcm.persist();
    } //1. End of Add Member

    //2. Delete Member
    private void doTerminate(){
        Long mid;
        String name, choice;
        int temp;

        do {//Start while
            //Print out the interface
            System.out.println("\n\n---===Member Termination===---" +
                           "\n-Menu-" +
                           "\n1.Delete by ID" +
                           "\n2.Search and Delete by Name" +
                           "\n0.Main Menu");
            System.out.print("Please enter your choice: ");
            choice = scn.next();
            temp = v.validateInput(choice, 0, 3);

            switch(temp){
                case 1:
                    do{
                        System.out.print("\nPlease the ID: "); //Presuming the correct ID
                        choice = scn.next();
                        mid = v.validateLong(choice);
                    } while(mid < 0);
                    terminateID(mid);
                    break;
                case 2:
                    System.out.print("\nPlease enter a name: ");
                    name = scn.next();
                    terminateName(name, "Member");
                    break;
                case 0:
                    return;
            }
        } while(temp > 0);
    } // End of Delete Member
    //-----Start of terminate helper method-----
    private void terminateID(Long mid){
        System.out.print(mcm.terminate(mid));
    }
    //Terminate by Name Search
    private void terminateName(String name, String mode){
        String choice;
        Long mid;
        try{
            if(mode.equals("Member")){
                doDisplayMember(name);
                do{
                    System.out.print("\nPlease enter the ID: "); //Presuming the correct ID
                    choice = scn.next();
                    mid = v.validateLong(choice);
                } while(mid < 0);
                
                terminateID(mid);
            } else if(mode.equals("Title")){

            }
        } catch(Exception ex){
            System.err.println("An unexpected error occured");
            ex.printStackTrace();
        }
    }
    //-----End of Terminate Helper method-----

    //3. Display member
    private void doDisplayMember(String name){
        try{
            System.out.println("\n\n\t\t---===Member Display===---");
            for(Object o: mcm.getMember(name)){
                MemberState ms = (MemberState) o;
                System.out.println("\nMemberID: " + ms.getMid());
                System.out.print("Name: " + ms.getName() +
                                 "\nType: " + ms.getType() +
                                 "\nDepartment: " + ms.getContactState().getDept() +
                                 "\nFaculty: " + ms.getContactState().getFaculty() +
                                 "\nEmail: " + ms.getContactState().getEmail() +
                                 "\nPhone: " + ms.getContactState().getPhoneNum());
            }
            System.out.println("\n\n\t\t---===End of Display===---");
        } catch(Exception ex){
            System.err.println("An unexpected error occured");
            ex.printStackTrace();
        }
    }

    private void getSessionBean(){
        try{
            InitialContext ic = new InitialContext();
            mcm = (MemberManagerRemote) ic.lookup(MemberManagerRemote.class.getName());
        } catch(Exception ex){
            System.err.println("An unexpected error occured");
            ex.printStackTrace();
        }
    }
}