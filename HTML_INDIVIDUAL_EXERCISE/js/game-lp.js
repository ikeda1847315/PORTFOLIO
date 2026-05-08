const aboutContent = document.querySelector(".about-content");
const cards = document.querySelectorAll(".image-card");

const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
        if (entry.isIntersecting) {
            entry.target.classList.add("show");
            observer.unobserve(entry.target);
        }
    });
}, { threshold: 0.2 });

cards.forEach(card => observer.observe(card));
observer.observe(aboutContent);

const download = document.querySelector(".download");
observer.observe(download);

// 修正後：ページ読み込み直後に即座にshowを付ける
const heroTitle = document.querySelector(".hero h1");
setTimeout(() => {
    heroTitle.classList.add("show");
}, 100); // 少し待ってからアニメーション開始

// ハンバーガーメニュー
const hamburger = document.getElementById("hamburger");
const navMenu = document.getElementById("nav-menu");

hamburger.addEventListener("click", () => {
    hamburger.classList.toggle("open");
    navMenu.classList.toggle("open");
});

navMenu.querySelectorAll("a").forEach(link => {
    link.addEventListener("click", () => {
        hamburger.classList.remove("open");
        navMenu.classList.remove("open");
    });
});