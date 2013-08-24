/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function validateMissingText(textbox, errorlabel){
    var field = document.getElementById(textbox);
    var label = document.getElementById(errorlabel);
    
    label.innerHTML = "";
    if(field.value==""){
        label.innerHTML = "Missing Entry";
        return false;
    } else{
        return true;
    }
}
