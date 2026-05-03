const taskInput = document.getElementById("taskInput");
const addTaskBtn = document.getElementById("addTask");
const taskList = document.getElementById("taskList");
const taskCount = document.getElementById("taskCount");
const filter = document.getElementById("filter");
const themeToggle = document.getElementById("themeToggle");

let tasks = JSON.parse(localStorage.getItem("tasks")) || [];
let currentFilter = "all";

// 保存
function saveTasks() {
    localStorage.setItem("tasks", JSON.stringify(tasks));
}

// 件数更新
function updateCount() {
    const activeTasks = tasks.filter(task => !task.completed).length;
    taskCount.textContent = `残り ${activeTasks} 件`;
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
        const index = tasks.indexOf(task);

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
        editBtn.textContent = "編集";
        editBtn.classList.add("edit-btn");

        editBtn.addEventListener("click", () => {
            const newText = prompt("タスクを編集", task.text);
            if (newText) {
                tasks[index].text = newText;
                saveTasks();
                renderTasks();
            }
        });

        // 削除
        const deleteBtn = document.createElement("button");
        deleteBtn.textContent = "削除";
        deleteBtn.classList.add("delete-btn");

        deleteBtn.addEventListener("click", () => {
            tasks.splice(index, 1);
            saveTasks();
            renderTasks();
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
    if (!text) return;

    tasks.push({
        text,
        completed: false
    });

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
    localStorage.setItem("darkMode",
        document.body.classList.contains("dark"));
});

if (localStorage.getItem("darkMode") === "true") {
    document.body.classList.add("dark");
}

// 初期表示(画面再描画：タスク変更ごとに更新)
renderTasks();