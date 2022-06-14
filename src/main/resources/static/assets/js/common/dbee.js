// common.dbee.js



function getJson(fileName, target) {
    fetch("/assets/json/" + fileName)
    .then(function (response) {
        return response.json();
    })
    .then(function (myJson) {
        target = JSON.stringify(myJson);
    });
}