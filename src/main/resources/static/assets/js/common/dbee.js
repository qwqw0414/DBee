// common.dbee.js

// 전역 변수
const _token = $("meta[name='_csrf']").attr("content");
const _header = $("meta[name='_csrf_header']").attr("content");

/**
 * str 문자열에 길이가 len 보다 작을 경우 
 * char 값을 앞부분 부터 let 길이 만큼 채워 넣음
 * @param {String} str 대상 문자열
 * @param {String} char 채워 넣을 값
 * @param {Number} len 적용 범위
 * @returns 
 */

function strFill(str, char, len) {
    if (str && char && str.length < len) {
        let temp = '';
        for (let i = 0; i < (len - str.length); i++) {
            temp += char;
        }
        str = temp + str;
    }
    return str;
}

/**
 * 에러 모달
 * @param {String} message 알림 메세지
 */
function alertError(message) {
    let $modal = $("#error-modal");
    $modal.find(".modal-body").html(message);
    $modal.modal();
}