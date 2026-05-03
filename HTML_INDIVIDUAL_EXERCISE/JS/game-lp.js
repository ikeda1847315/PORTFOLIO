const card = document.querySelector(".floating-card");
const exploreBtn = document.querySelector(".secondary-btn");
const aboutContent = document.querySelector(".about-content");
const cards = document.querySelectorAll(".image-card");

document.addEventListener("mousemove", (e) => {
    const x = (window.innerWidth / 2 - e.pageX) / 30;
    const y = (window.innerHeight / 2 - e.pageY) / 30;

    card.style.transform =
        `rotateY(${x}deg) rotateX(${-y}deg)`;
});

const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.classList.add("show");
        } else {
            entry.target.classList.remove("show");
        }
    });
}, {
    threshold: 0.3
});

document.querySelectorAll(".about-content").forEach(el => {
    observer.observe(el);
});

document.querySelectorAll(".image-card").forEach(el => {
    observer.observe(el);
});

exploreBtn.addEventListener("click", () => {
    aboutContent.classList.remove("show");

    cards.forEach(card => {
        card.classList.remove("show");
    });

    setTimeout(() => {
        aboutContent.classList.add("show");

        cards.forEach((card, index) => {
            setTimeout(() => {
                card.classList.add("show");
            }, index * 200);
        });

    }, 400);
});