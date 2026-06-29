document.getElementById("loginBtn").addEventListener("click", async () => {
    const login = document.getElementById("login").value.trim();
    const password = document.getElementById("password").value.trim();
    const message = document.getElementById("message");

    const response = await fetch(`${API_URL}/api/auth/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ login, password })
    });

    const data = await response.json();

    if (!data.success) {
        message.textContent = data.message;
        return;
    }

    if (data.role === "admin") {
        localStorage.setItem("role", "admin");
        window.location.href = "admin.html";
    } else {
        localStorage.setItem("role", "user");
        localStorage.setItem("userId", data.userId);
        window.location.href = "cabinet.html";
    }
});