let regexp = new DbeeRegExp();




function isDuplicateId(userId, $alert) {
    // let $alert = $(alert);

    $.ajax({
        url: "/dbee/account/duple/" + userId,
        type: "GET",
        dataType: "JSON",
        success: (data) => {
            var idCount = data.data.countId;

            if (idCount > 0) {
                $("#userId").focus();
                $alert.show();
                $alert.text("중복된 아이디입니다.");
            }
            else {
                $alert.hide();
            }
        },
        complete: () => {

        },
        error: (e, x, c) => {
            console.log(e, x, c);
        }
    });
}


function submit($form, $alert) {
    let formData = new FormData($form[0]);

    let userId = formData.get("userId");
    let userName = formData.get("userName");
    let password = formData.get("password");
    let passwordChk = formData.get("passwordChk");

    if (!regexp.test("account.userId", userId)) {
        $alert.show();
        $alert.text("아이디를 입력해주세요.");
        return "userId";
    }
    
    if (!regexp.test("account.userName", userName)) {
        $alert.show();
        $alert.text("이름을 입력해주세요.");
        return "userName";
    }
    
    if (!regexp.test("account.password", password)) {
        $alert.show();
        $alert.text("비밀번호를 입력해주세요.");
        return "password";
    }

    if (password !== passwordChk) {
        $alert.show();
        $alert.text("비밀번호가 서로 일치하지 않습니다.");
        return "password";
    }

    $form.submit();
}