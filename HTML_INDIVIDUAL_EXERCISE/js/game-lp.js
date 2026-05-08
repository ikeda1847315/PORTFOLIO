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

// ページ読み込み直後に、タイトルへ即座にshowを付ける
const heroTitle = document.querySelector(".hero h1");
if (heroTitle) {
    // heroTitleがnullだとエラーなので、念のための分岐
    setTimeout(() => {
        heroTitle.classList.add("show");
    }, 100);
    // 少し待って（100ms）から、アニメーション開始
}

// ハンバーガーメニュー
const hamburger = document.getElementById("hamburger");
const navMenu = document.getElementById("nav-menu");

hamburger.addEventListener("click", () => {
    hamburger.classList.toggle("open");
    navMenu.classList.toggle("open");

    const isOpen = hamburger.classList.contains("open");
    hamburger.setAttribute("aria-label", isOpen ? "メニューを閉じる" : "メニューを開く");
});

navMenu.querySelectorAll("a").forEach(link => {
    link.addEventListener("click", () => {
        hamburger.classList.remove("open");
        navMenu.classList.remove("open");
    });
});