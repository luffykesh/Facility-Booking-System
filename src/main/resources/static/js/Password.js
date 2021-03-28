function comparePassword(confirmPassword) {
    originalPassword = document.getElementById("pw").value
    console.log(originalPassword)
    if (confirmPassword.value != originalPassword) {
        confirmPassword.setCustomValidity("Passwords do not match")
        confirmPassword.reportValidity()
    } else {
        confirmPassword.setCustomValidity("");
        confirmPassword.reportValidity()
    }
}