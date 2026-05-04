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

    entries.forEach((entry) => {

        if (entry.isIntersecting) {

            entry.target.classList.remove("show");

            setTimeout(() => {
                entry.target.classList.add("show");
            }, 50);

        } else {

            entry.target.classList.remove("show");

        }

    });

}, {
    threshold: 0.2
});

// 監視
cards.forEach(card => observer.observe(card));
observer.observe(aboutContent);

const download = document.querySelector(".download");
observer.observe(download);

const heroTitle = document.querySelector(".hero h1");
observer.observe(heroTitle);