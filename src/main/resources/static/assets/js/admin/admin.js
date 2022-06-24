// admin.admin.js

/**
 * 계정 정보 리스트 조회
 * @param {*} $table 
 * @param {*} $modal 
 */
function getAccountList($table, $modal) {
    $.ajax({
        type: "GET",
        url: "/dbee/admin/account/list",
        contentType: 'application/json; charset=utf-8',
        dataType: "json",
        // data: oParam,
        success: (oResult) => {
            console.log(oResult);
            if (oResult.code && oResult.code == 100 && oResult.data) {
                let sHtml = "";
                let oUsers = oResult.data.users;

                oUsers.forEach(oUser => {
                    sHtml += '<tr>';
                    sHtml += '<input type="hidden" value="' + oUser.userNo + '">';
                    sHtml += '<td>' + oUser.userNo + '</td>';
                    sHtml += '<td>' + oUser.userId + '</td>';
                    sHtml += '<td>' + oUser.userName + '</td>';
                    sHtml += '<td>' + new DateFormat(oUser.regDate).format('YYYY-MM-DD') + '</td>';
                    sHtml += '</tr>';
                });

                $table.find("tbody").html(sHtml);
                $table.DataTable();
            }
        },
        complete: () => {
            $table.find("tbody tr").on("click", (e) => {
                let userNo = $(e.target).parent("tr").children("input[type=hidden]").val();
                if (userNo) {
                    getUserDetail(userNo, $modal);
                    $modal.modal();
                }
            });
        },
        error: (e, x, c) => {
            console.log(e, x, c);
        }
    });
}

/**
 * 계정 정보 상세 조회
 * @param {*} userNo 
 * @param {*} $modal 
 */
function getUserDetail(userNo, $modal) {

    let $userNo = $modal.find("#inputUserNo");
    let $userId = $modal.find("#inputUserId");
    let $userName = $modal.find("#inputUserName");
    let $regDate = $modal.find("#inputRegDate");
    let $alert = $modal.find("#modal-alert");

    // 값 초기화
    $userNo.val('');
    $userId.val('');
    $userName.val('');
    $regDate.val('');
    $modal.find(".modal-body input[type=checkbox]").prop("checked", false);

    $alert.html("");

    if (userNo) {
        $.ajax({
            type: "GET",
            url: "/dbee/admin/account/" + userNo,
            dataType: "json",
            // data: oParam,
            success: (oResult) => {
                console.log('oResult', oResult);

                let oUser = oResult.data.user;
                let aRoles = oUser.roles;

                // 기본정보 셋팅
                $userNo.val(oUser.userNo);
                $userId.val(oUser.userId);
                $userName.val(oUser.userName);
                $regDate.val(new DateFormat(oUser.regDate).format('YYYY-MM-DD'));

                // 권한 셋팅
                console.log(aRoles);
                aRoles.forEach(oRole => {
                    $modal.find(".modal-body input[value=" + oRole.roleId + "]:checkbox").prop("checked", true);
                });

                console.log(oResult);
            },
            complete: () => {

            },
            error: (e, x, c) => {
                console.log(e, x, c);
            }
        });
    }
}

/**
 * 계정 정보 수정
 * @param {*} $modal 
 */
function updateUserDetail($modal) {
    let userNo = $modal.find("#inputUserNo").val();
    let userId = $modal.find("#inputUserId").val();
    let userName = $modal.find("#inputUserName").val();
    let regDate = $modal.find("#inputRegDate").val();
    let $alert = $modal.find("#modal-alert");

    // 권한 배열
    let roles = new Array();
    let $roles = $modal.find(".modal-body input:checkbox");

    for (var i = 0; i < $roles.length; i++) {
        let id = $roles.eq(i).attr("id");
        if ($("#" + id).prop('checked')) {
            roles.push((String)($("#" + id).val()));
        }
    }

    let oParam = {
        "userNo": userNo,
        "userId": userId,
        "userName": userName,
        "regDate": regDate,
        "roles": roles
    }

    console.log(oParam);

    $.ajax({
        type: "POST",
        url: "/dbee/admin/account/update",
        contentType: 'application/json; charset=utf-8',
        dataType: "json",
        data: JSON.stringify(oParam),
        beforeSend: (xhr) => {
            xhr.setRequestHeader(_header, _token);
        },
        success: (oResult) => {
            console.log(oResult);

            // 결과 알림 
            let status = oResult.code;
            let sHtml = "";

            if (100 == status) {
                sHtml = '<div class="alert alert-success" role="alert">'
                    + 'Successssssssss !'
                    + '</div>';
            } else {
                sHtml = '<div class="alert alert-danger" role="alert">'
                    + 'Error rro rroroo r !'
                    + '</div>';
            }

            $alert.html(sHtml);
        },
        complete: () => {
        },
        error: (e, x, c) => {
            console.log(e, x, c);
            let sHtml = '<div class="alert alert-danger" role="alert">'
                + 'Error rro rroroo r !'
                + '</div>';
            $alert.html(sHtml);
        }
    });
}