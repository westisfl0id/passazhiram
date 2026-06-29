const startDateInput = document.getElementById("startDate");
const sendRequestBtn = document.getElementById("sendRequestBtn");
const message = document.getElementById("message");

startDateInput.addEventListener("input", () => {
    let value = startDateInput.value.replace(/\D/g, "").slice(0, 8);

    if (value.length >= 5) {
        value = value.replace(/(\d{2})(\d{2})(\d{1,4})/, "$1.$2.$3");
    } else if (value.length >= 3) {
        value = value.replace(/(\d{2})(\d{1,2})/, "$1.$2");
    }

    startDateInput.value = value;
});

function isValidRuDate(dateString) {
    const match = dateString.match(/^(\d{2})\.(\d{2})\.(\d{4})$/);

    if (!match) {
        return false;
    }

    const day = Number(match[1]);
    const month = Number(match[2]);
    const year = Number(match[3]);

    const date = new Date(year, month - 1, day);

    return (
        date.getFullYear() === year &&
        date.getMonth() === month - 1 &&
        date.getDate() === day
    );
}

function convertRuDateToIso(dateString) {
    const [day, month, year] = dateString.split(".");
    return `${year}-${month}-${day}`;
}

sendRequestBtn.addEventListener("click", async () => {
    const userId = localStorage.getItem("userId");

    if (!userId) {
        window.location.href = "index.html";
        return;
    }

    const transportType = document.getElementById("transportType").value;
    const startDate = document.getElementById("startDate").value.trim();
    const paymentType = document.getElementById("paymentType").value;

    if (!transportType || !startDate || !paymentType) {
        message.textContent = "Заполните все поля";
        return;
    }

    if (!isValidRuDate(startDate)) {
        message.textContent = "Введите дату в формате ДД.ММ.ГГГГ";
        return;
    }

    const request = {
        userId: Number(userId),
        transportType: transportType,
        startDate: convertRuDateToIso(startDate),
        paymentType: paymentType
    };

    try {
        const response = await fetch(`${API_URL}/api/requests`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(request)
        });

        const data = await response.json();
        message.textContent = data.message;

        if (data.success) {
            setTimeout(() => {
                window.location.href = "cabinet.html";
            }, 1000);
        }
    } catch (error) {
        console.error(error);
        message.textContent = "Ошибка отправки заявки";
    }
});




// document.getElementById("sendRequestBtn").addEventListener("click", async () => {
//     const userId = localStorage.getItem("userId");
//
//     if (!userId) {
//         window.location.href = "index.html";
//         return;
//     }
//
//     const request = {
//         userId: Number(userId),
//         transportType: document.getElementById("transportType").value,
//         startDate: document.getElementById("startDate").value,
//         paymentType: document.getElementById("paymentType").value
//     };
//
//     const message = document.getElementById("message");
//
//     if (!request.transportType || !request.startDate || !request.paymentType) {
//         message.textContent = "Заполните все поля";
//         return;
//     }
//
//     const response = await fetch(`${API_URL}/api/requests`, {
//         method: "POST",
//         headers: {
//             "Content-Type": "application/json"
//         },
//         body: JSON.stringify(request)
//     });
//
//     const data = await response.json();
//     message.textContent = data.message;
//
//     if (data.success) {
//         setTimeout(() => {
//             window.location.href = "cabinet.html";
//         }, 1000);
//     }
// });