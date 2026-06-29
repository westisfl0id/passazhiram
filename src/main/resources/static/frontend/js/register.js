document.getElementById("registerBtn").addEventListener("click", async () => {
    const user = {
        login: document.getElementById("login").value.trim(),
        password: document.getElementById("password").value.trim(),
        fullName: document.getElementById("fullName").value.trim(),
        birthDate: document.getElementById("birthDate").value,
        phone: document.getElementById("phone").value.trim(),
        email: document.getElementById("email").value.trim()
    };

    const message = document.getElementById("message");

    if (!user.login || !user.password || !user.fullName || !user.birthDate || !user.phone || !user.email) {
        message.textContent = "Заполните все поля";
        return;
    }

    const response = await fetch(`${API_URL}/api/auth/register`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    });

    const data = await response.json();
    message.textContent = data.message;

    if (data.success) {
        setTimeout(() => {
            window.location.href = "index.html";
        }, 1000);
    }
});