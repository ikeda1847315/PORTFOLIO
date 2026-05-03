const form = document.getElementById("contactForm");

form.addEventListener("submit", async (e) => {
    e.preventDefault();
    // ブラウザ標準のフォーム送信を停止
    // JavaScriptで送信する場合は必要
    // 通常のHTMLでのform送信(action)にする場合は不要（<form action="URL" method="POST">etc）

    // 将来ここでサーバー送信設定
    // await fetch("送信URL", {
    //     method: "POST",
    //     body: new FormData(form)
    // });

    window.location.href = "thanks.html";
});