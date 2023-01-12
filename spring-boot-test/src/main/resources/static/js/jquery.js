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

    // from https://getbootstrap.com/docs/4.0/components/forms/?
    (function() {
        'use strict';
        window.addEventListener('load', function() {
            // Fetch all the forms we want to apply custom Bootstrap validation styles to
            var forms = document.getElementsByClassName('needs-validation');
            // Loop over them and prevent submission
            var validation = Array.prototype.filter.call(forms, function(form) {
                form.addEventListener('submit', function(event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();
});
