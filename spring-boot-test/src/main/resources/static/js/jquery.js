$(document).ready(function () {
    $("#switch-btn").click(function (event) {
        var labelText = $("#switch-btn");
        if (labelText.text() == "Go to Login") {
            $("#login-title-lbl").text("Login");
            $("#switch-btn").text("Go to Registration");
            $("#switch-text").text("Back to Registration");
            $("#login-btn").text("Login");
            $("#name-div").hide();
            $("#repeat-password-div").hide();
            $("#firstname").prop('required', false);
            $("#lastname").prop('required', false);
            $("#repeat-password").prop('required', false);
        } else {
            $("#login-title-lbl").text("Register");
            $("#switch-btn").text("Go to Login");
            $("#switch-text").text("Back to Login");
            $("#login-btn").text("Register");
            $("#name-div").show();
            $("#repeat-password-div").show();
            $("#firstname").prop('required', true);
            $("#lastname").prop('required', true);
            $("#repeat-password").prop('required', true);
        }
    });

    $("#comment-area").on("keyup input", function(){
        $(this).css('height','auto').css('height',this.scrollHeight+(this.offsetHeight - this.clientHeight));
    });



    var buttons = document.querySelector('.submit');

    $("#comment-area").onfocus = function(){
        buttons.style.display = 'block';
    }

});
