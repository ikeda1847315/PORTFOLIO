const taskInput = document.getElementById("taskInput");
const addTaskBtn = document.getElementById("addTask");
const taskList = document.getElementById("taskList");
const taskCount = document.getElementById("taskCount");
const filter = document.getElementById("filter");
const themeToggle = document.getElementById("themeToggle");
const deleteModal = document.getElementById("deleteModal");
const modalConfirm = document.getElementById("modalConfirm");
const modalCancel = document.getElementById("modalCancel");
const modalMessage = document.getElementById("modalMessage");

let pendingDeleteIndex = null;
let tasks = JSON.parse(localStorage.getItem("tasks")) || [];
let currentFilter = "active";
// タスクフィルターの初期表示設定

function showModal(index, taskText) {
    pendingDeleteIndex = index;
    modalMessage.textContent = "";

    const line1 = document.createElement("p");
    line1.textContent = `「${taskText}」を削除します。`;

    const line2 = document.createElement("p");
    line2.textContent = "この操作は元に戻せません。";

    modalMessage.appendChild(line1);
    modalMessage.appendChild(line2);

    deleteModal.classList.add("show");
}

function hideModal() {
    deleteModal.classList.remove("show");
    pendingDeleteIndex = null;
}

modalCancel.addEventListener("click", hideModal);

modalConfirm.addEventListener("click", () => {
    const skipConfirm = document.getElementById("skipConfirm").checked;
    if (pendingDeleteIndex !== null) {
        tasks.splice(pendingDeleteIndex, 1);
        saveTasks();
        renderTasks();
    }
    hideModal();
});

// 保存
function saveTasks() {
    localStorage.setItem("tasks", JSON.stringify(tasks));
}

// 件数更新
function updateCount() {
    const activeTasks = tasks.filter(task => !task.completed).length;
    document.getElementById("taskCountNum").textContent = activeTasks;
}

// 表示
function renderTasks() {
    taskList.innerHTML = "";

    let filteredTasks = tasks.filter(task => {
        if (currentFilter === "active") return !task.completed;
        if (currentFilter === "completed") return task.completed;
        return true;
    });

    filteredTasks.forEach((task) => {
        const index = tasks.findIndex(t => t.id === task.id);

        const li = document.createElement("li");

        const span = document.createElement("span");
        span.textContent = task.text;
        span.classList.add("task-text");

        if (task.completed) {
            span.classList.add("completed");
        }

        span.addEventListener("click", () => {
            tasks[index].completed = !tasks[index].completed;
            saveTasks();
            renderTasks();
        });

        const buttons = document.createElement("div");
        buttons.classList.add("action-buttons");

        // 編集
        const editBtn = document.createElement("button");
        editBtn.textContent = "✏️ 編集";
        editBtn.classList.add("edit-btn");

        editBtn.addEventListener("click", () => {
            // spanをinputに置き換える
            const input = document.createElement("input");
            input.type = "text";
            input.value = task.text;
            input.placeholder = "タスクを入力してください";
            li.replaceChild(input, span);
            input.focus();

            // Enterで確定
            // Enterで確定
            input.addEventListener("keypress", (e) => {
                if (e.key === "Enter") {
                    const newText = input.value.trim();

                    if (!newText) {
                        input.style.borderColor = "#b50000";
                        input.style.outline = "none"; // 追加
                        input.classList.add("shake");
                        setTimeout(() => {
                            input.style.borderColor = "";
                            input.classList.remove("shake");
                        }, 1000);
                        return;
                    }

                    tasks[index].text = newText;
                    saveTasks();
                    renderTasks();
                }
            });
        });

        // 上記に変更。
        // 　⇒・デザインが崩れる（ブラウザのデフォルト見た目なので、アプリのスタイルが全く反映されない）
        // 　　・モバイルで使いにくい（スマホだと操作しづらく、一部ブラウザでは無効化されている）
        // 　　・ページがフリーズする（同期処理で表示中、他の処理がすべて止まる）
        // 　　本番サービスでは使わない
        // editBtn.addEventListener("click", () => {
        //     const newText = prompt("タスクを編集", task.text);
        //     if (newText) {
        //         tasks[index].text = newText;
        //         saveTasks();
        //         renderTasks();
        //     }
        // });

        // 削除
        const deleteBtn = document.createElement("button");
        deleteBtn.textContent = "🗑️ 削除";
        deleteBtn.classList.add("delete-btn");

        // 削除（renderTasks内）
        deleteBtn.addEventListener("click", () => {
            const skipConfirm = document.getElementById("skipConfirm").checked;

            if (skipConfirm) {
                // チェックあり → 即削除
                tasks.splice(index, 1);
                saveTasks();
                renderTasks();
            } else {
                // チェックなし → モーダル表示
                showModal(index, task.text);
            }
        });

        buttons.appendChild(editBtn);
        buttons.appendChild(deleteBtn);

        li.appendChild(span);
        li.appendChild(buttons);

        taskList.appendChild(li);
    });

    updateCount();
}

// 追加
addTaskBtn.addEventListener("click", () => {
    const text = taskInput.value.trim();
    if (!text) {
        taskInput.style.borderColor = "#b50000";
        taskInput.classList.add("shake");
        document.getElementById("errorMsg").classList.add("show");

        // 1秒後にリセット
        setTimeout(() => {
            taskInput.style.borderColor = "#ddd";
            taskInput.classList.remove("shake");
            document.getElementById("errorMsg").classList.remove("show");
        }, 1000);
        return;
    }

    tasks.push({ id: Date.now(), text, completed: false });

    taskInput.value = "";
    saveTasks();
    renderTasks();
});

taskInput.addEventListener("keypress", (e) => {
    if (e.key === "Enter") addTaskBtn.click();
});

// フィルター
filter.addEventListener("change", (e) => {
    currentFilter = e.target.value;
    renderTasks();
});

// ダークモード
themeToggle.addEventListener("click", () => {
    document.body.classList.toggle("dark");
    const isDark = document.body.classList.contains("dark");
    themeToggle.textContent = isDark ? "ライト ☀️" : "ダーク 🌙";
    localStorage.setItem("darkMode", isDark);
});

// 初期表示（← ここも元のコードと重複していないか確認）
if (localStorage.getItem("darkMode") === "true") {
    document.body.classList.add("dark");
    themeToggle.textContent = "ライト ☀️";
}

// 初期表示(画面再描画：タスク変更ごとに更新)
renderTasks();