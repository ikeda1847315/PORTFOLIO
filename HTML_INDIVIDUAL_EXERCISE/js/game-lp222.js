const card = document.querySelector(".floating-card");
const aboutContent = document.querySelector(".about-content");
const cards = document.querySelectorAll(".image-card");

document.addEventListener("mousemove", (e) => {
    const x = (window.innerWidth / 2 - e.pageX) / 30;
    const y = (window.innerHeight / 2 - e.pageY) / 30;
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

// ハンバーガーメニュー
const hamburger = document.getElementById("hamburger");
const navMenu = document.getElementById("nav-menu");

hamburger.addEventListener("click", () => {
    hamburger.classList.toggle("open");
    navMenu.classList.toggle("open");
});

// ナビのリンクをタップしたら閉じる
navMenu.querySelectorAll("a").forEach(link => {
    link.addEventListener("click", () => {
        hamburger.classList.remove("open");
        navMenu.classList.remove("open");
    });
});

// 監視
cards.forEach(card => observer.observe(card));
observer.observe(aboutContent);

const download = document.querySelector(".download");
observer.observe(download);

const heroTitle = document.querySelector(".hero h1");
observer.observe(heroTitle);