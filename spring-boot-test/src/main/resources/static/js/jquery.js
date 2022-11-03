$(document).ready(function (){
    $("#switch-btn").click(function (event) {
        var labelText = $("#switch-btn");
        if (labelText.text() == "Already registered?"){
            $("#switch-btn").text("Not registered?");
            $("#login-btn").text("Login");
            $("#repeat-password").hide();
            $("#repeat-password-label").hide();
        } else{
            $("#switch-btn").text("Already registered?");
            $("#login-btn").text("Register");
            $("#repeat-password").show();
            $("#repeat-password-label").show();
        }
    });
});