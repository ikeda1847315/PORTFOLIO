const cityInput = document.getElementById("cityInput");
const searchBtn = document.getElementById("searchBtn");
const weatherResult = document.getElementById("weatherResult");

// 都市検索 → 緯度経度取得
async function getCoordinates(city) {
    const response = await fetch(
        `https://geocoding-api.open-meteo.com/v1/search?name=${city}&count=1&language=ja&format=json`
    );

    const data = await response.json();

    if (!data.results) {
        throw new Error("都市が見つかりません");
    }

    return data.results[0];
}

// 天気取得
function getWeatherDescription(code) {
    switch (code) {
        case 0:
            return "🌞 快晴";

        case 1:
        case 2:
            return "⛅ 晴れ 時々 曇り";

        case 3:
            return "☁️ 曇り";

        case 51:
        case 53:
        case 55:
        case 61:
        case 63:
        case 65:
        case 80:
        case 81:
        case 82:
            return "☔ 雨";

        case 71:
        case 73:
        case 75:
        case 77:
        case 85:
        case 86:
            return "☃️ 雪";

        case 95:
        case 96:
        case 99:
            return "⚡ 雷雨";

        case 45:
        case 48:
            return "🌫️ 霧";

        default:
            return "🌍 予想外な天気コード！";
    }
}

// 風速表示
function getWindAlert(speed) {
    if (speed >= 40) return "🌪️ 暴風";
    if (speed >= 25) return "🌀 強風";
    return "🍃 微風";
}

async function getWeather() {
    const city = cityInput.value.trim();

    if (!city) return;

    weatherResult.innerHTML = "読み込み中...";

    try {
        const location = await getCoordinates(city);

        const response = await fetch(
            `https://api.open-meteo.com/v1/forecast?latitude=${location.latitude}&longitude=${location.longitude}&current_weather=true&timezone=Asia/Tokyo`
        );

        const data = await response.json();

        const weather = data.current_weather;

        const formattedTime = weather.time
            .replace("T", " ")
            .replace(/-/g, "/");


        weatherResult.innerHTML = `
        <h2>${location.name}</h2>
        <p>${getWeatherDescription(weather.weathercode)}</p>
        <p>🌡️ 気温: ${weather.temperature}℃</p>
        <p>💨 風速: ${weather.windspeed} km/h［${getWindAlert(weather.windspeed)}］</p>
        <p>🕒 取得データ情報：${formattedTime}</p>
`;
    } catch (error) {
        weatherResult.innerHTML = "都市が見つかりません";
    }
}

searchBtn.addEventListener("click", getWeather);

cityInput.addEventListener("keypress", (e) => {
    if (e.key === "Enter") {
        getWeather();
    }
});