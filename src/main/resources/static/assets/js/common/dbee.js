// common.dbee.js

// 전역 변수
const _token = $("meta[name='_csrf']").attr("content");
const _header = $("meta[name='_csrf_header']").attr("content");


function getJson(fileName, target) {
    fetch("/assets/json/" + fileName)
    .then(function (response) {
        return response.json();
    })
    .then(function (myJson) {
        target = JSON.stringify(myJson);
    });
}