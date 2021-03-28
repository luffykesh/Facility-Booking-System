function comparePassword(confirmPassword) {
    if (confirmPassword.value != $("#password").val()) {
        confirmPassword.setCustomValidity("Password not matching")
    } else {
        confirmPassword.setCustomValidity("");
    }
}