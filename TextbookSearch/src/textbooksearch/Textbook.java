/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package textbooksearch;

/**
 *
 * @author Stanley
 */
public class Textbook {
    private String name;
    private int code;
    private int copies;

    public Textbook(){
        name = "NIL";
        code = 0;
        copies = 0;
    }
    public Textbook(int code, int copies){
        this.name = "NIL";
        this.code = code;
        this.copies = copies;
    }
    public Textbook(String name, int code, int copies){
        this.name = name;
        this.code = code;
        this.copies = copies;
    }

    public String getName(){
        return name;
    }

    public int getCode(){
        return code;
    }

    public int getCopies(){
        return copies;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCode(int code){
        this.code = code;
    }

    public void setCopies(int copies){
        this.copies = copies;
    }
}
