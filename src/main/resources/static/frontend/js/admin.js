const role = localStorage.getItem("role");

if (role !== "admin") {
    window.location.href = "index.html";
}

let allRequests = [];

function getStatusClass(status) {
    if (status === "Новая") {
        return "status status-new";
    }

    if (status === "Идет обучение") {
        return "status status-progress";
    }

    if (status === "Обучение завершено") {
        return "status status-done";
    }

    return "status";
}

async function loadAdminRequests() {
    const container = document.getElementById("adminRequests");
    container.innerHTML = "";

    try {
        const response = await fetch(`${API_URL}/api/admin/requests`);

        if (!response.ok) {
            throw new Error("Ошибка загрузки заявок");
        }

        allRequests = await response.json();
        renderRequests();
    } catch (error) {
        console.error(error);
        container.innerHTML = `<div class="empty">Не удалось загрузить заявки.</div>`;
    }
}

function renderRequests() {
    const filter = document.getElementById("statusFilter").value;
    const container = document.getElementById("adminRequests");

    container.innerHTML = "";

    const filtered = filter
        ? allRequests.filter(request => request.status === filter)
        : allRequests;

    if (filtered.length === 0) {
        container.innerHTML = `<div class="empty">Заявок нет.</div>`;
        return;
    }

    filtered.forEach(request => {
        const card = document.createElement("div");
        card.className = "request-card";

        card.innerHTML = `
            <p><b>ID заявки:</b> ${request.id}</p>
            <p><b>ID пользователя:</b> ${request.userId}</p>
            <p><b>Транспорт:</b> ${request.transportType}</p>
            <p><b>Дата начала:</b> ${request.startDate}</p>
            <p><b>Оплата:</b> ${request.paymentType}</p>
            <p>
                <b>Статус:</b>
                <span class="${getStatusClass(request.status)}">${request.status}</span>
            </p>

            <select id="status-${request.id}">
                <option value="Новая">Новая</option>
                <option value="Идет обучение">Идет обучение</option>
                <option value="Обучение завершено">Обучение завершено</option>
            </select>

            <button onclick="changeStatus(${request.id})">Изменить статус</button>
        `;

        container.appendChild(card);

        document.getElementById(`status-${request.id}`).value = request.status;
    });
}

async function changeStatus(id) {
    const status = document.getElementById(`status-${id}`).value;

    try {
        const response = await fetch(`${API_URL}/api/admin/requests/${id}/status`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ status })
        });

        const data = await response.json();

        alert(data.message);

        if (data.success) {
            loadAdminRequests();
        }
    } catch (error) {
        console.error(error);
        alert("Ошибка при изменении статуса");
    }
}

document.getElementById("statusFilter").addEventListener("change", renderRequests);

document.getElementById("logoutBtn").addEventListener("click", () => {
    localStorage.clear();
    window.location.href = "index.html";
});

loadAdminRequests();