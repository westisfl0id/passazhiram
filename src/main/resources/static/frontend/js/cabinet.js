const userId = localStorage.getItem("userId");

if (!userId) {
    window.location.href = "index.html";
}

const images = [
    "img/slide1.jpg",
    "img/slide2.jpg",
    "img/slide3.jpg",
    "img/slide4.jpg"
];

let currentSlide = 0;

const slideImage = document.getElementById("slideImage");
const prevBtn = document.getElementById("prevBtn");
const nextBtn = document.getElementById("nextBtn");
const requestsContainer = document.getElementById("requests");
const logoutBtn = document.getElementById("logoutBtn");

function showSlide(index) {
    currentSlide = (index + images.length) % images.length;
    slideImage.src = images[currentSlide];
}

prevBtn.addEventListener("click", () => {
    showSlide(currentSlide - 1);
});

nextBtn.addEventListener("click", () => {
    showSlide(currentSlide + 1);
});

setInterval(() => {
    showSlide(currentSlide + 1);
}, 3000);

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

async function loadRequests() {
    requestsContainer.innerHTML = "";

    try {
        const requestsResponse = await fetch(`${API_URL}/api/requests/user/${userId}`);
        const reviewsResponse = await fetch(`${API_URL}/api/reviews/user/${userId}`);

        if (!requestsResponse.ok) {
            throw new Error("Ошибка загрузки заявок");
        }

        if (!reviewsResponse.ok) {
            throw new Error("Ошибка загрузки отзывов");
        }

        const requests = await requestsResponse.json();
        const reviews = await reviewsResponse.json();

        const reviewsByRequestId = {};

        reviews.forEach(review => {
            reviewsByRequestId[review.requestId] = review;
        });

        if (requests.length === 0) {
            requestsContainer.innerHTML = `<div class="empty">У вас пока нет заявок.</div>`;
            return;
        }

        requests.forEach(request => {
            const card = document.createElement("div");
            card.className = "request-card";

            const review = reviewsByRequestId[request.id];

            card.innerHTML = `
                <p><b>Транспорт:</b> ${request.transportType}</p>
                <p><b>Дата начала:</b> ${request.startDate}</p>
                <p><b>Оплата:</b> ${request.paymentType}</p>
                <p>
                    <b>Статус:</b>
                    <span class="${getStatusClass(request.status)}">${request.status}</span>
                </p>
            `;

            if (review) {
                card.innerHTML += `
                    <div class="review-box">
                        <p><b>Ваш отзыв:</b></p>
                        <p>${review.text}</p>
                    </div>
                `;
            } else if (request.status === "Обучение завершено") {
                card.innerHTML += `
                    <textarea placeholder="Ваш отзыв" id="review-${request.id}"></textarea>
                    <button onclick="sendReview(${request.id})">Оставить отзыв</button>
                `;
            }

            requestsContainer.appendChild(card);
        });
    } catch (error) {
        console.error(error);
        requestsContainer.innerHTML = `<div class="empty">Не удалось загрузить заявки.</div>`;
    }
}

async function sendReview(requestId) {
    const textarea = document.getElementById(`review-${requestId}`);
    const text = textarea.value.trim();

    if (!text) {
        alert("Введите текст отзыва");
        return;
    }

    try {
        const response = await fetch(`${API_URL}/api/reviews`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                userId: Number(userId),
                requestId: requestId,
                text: text
            })
        });

        const data = await response.json();

        alert(data.message);

        if (data.success) {
            textarea.value = "";
            loadRequests();
        }
    } catch (error) {
        console.error(error);
        alert("Ошибка при отправке отзыва");
    }
}

logoutBtn.addEventListener("click", () => {
    localStorage.clear();
    window.location.href = "index.html";
});

showSlide(0);
loadRequests();