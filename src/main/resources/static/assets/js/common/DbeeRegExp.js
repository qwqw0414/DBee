class DbeeRegExp {
    // regexp.json 경로
    jsonPath = "/assets/json/regexp.json";

    // 정규식 맵
    regexpMap = "";

    constructor() {
        let regexpMap;
        let callback = (data) =>{
            this.regexpMap = data;
        }

        fetch(this.jsonPath)
            .then(function (response) {
                return response.json();
            })
            .then(function (myJson) {
                callback(myJson);
            });
    }

    test(key, value) {
        // console.log(this.regexpMap);
        let regexpStr = this.regexpMap[key];
        if (regexpStr !== undefined && regexpStr.length > 0) {
            let regexp = new RegExp(regexpStr);
            return regexp.test(value);
        }
        console.log("DbeeRegExp.regexp : regexp is Null key=" + key);
        return false;
    }
}