/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function init(mainFormId){
    var objForm = document.getElementById(mainFormId);
    objForm.onsubmit = validateForm();
}

function validateForm(){
    var finalValidationResult = true;
    var validationResult = true;
    
    validationResult = validateMissingText("eventName", "lblErrEventName");
    if(!validationResult)
        finalValidationResult = false;
    
    validationResult = validateMissingText("startDateTime", "lblErrStartDateTime");
    if(!validationResult) 
        finalValidationResult = false;
    
    validationResult = validateMissingText("endDateTime", "lblErrEndDateTime");
    if(!validationResult) 
        finalValidationResult = false;
    
    if(!finalValidationResult)
    {
        alert("Validation Error");
        return false;
    }
    else
    {
        return true;
    }
}