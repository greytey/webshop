$(document).ready(function (){
    $("#switch-btn").click(function (event) {
        var labelText = $("#switch-btn");
        if (labelText.text() == "Go to Login"){
            $("#switch-btn").text("Go to Registration");
            $("#switch-text").text("Back to Registration");
            $("#login-btn").text("Login");
            $("#repeat-password").hide();
            $("#repeat-password").removeAttr('required');
            $("#repeat-password-label").hide();
        } else{
            $("#switch-btn").text("Go to Login");
            $("#switch-text").text("Back to Login");
            $("#login-btn").text("Register");
            $("#repeat-password").show();
            $("#repeat-password").addAttr('required');
            $("#repeat-password-label").show();
        }
    });
});