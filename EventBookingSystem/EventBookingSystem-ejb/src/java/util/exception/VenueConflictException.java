/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Stanley
 */
public class VenueConflictException extends Exception{
    public VenueConflictException(){
        
    }
    
    public VenueConflictException(String msg){
        super(msg);
    }
}
