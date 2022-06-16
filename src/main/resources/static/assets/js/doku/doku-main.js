// doku.doku-main.js

// #############################################################
//                          Varible
// #############################################################
let $table = $("#dokuTable");
let cell_index = -1;



// #############################################################
//                           Event
// #############################################################

$(() => {
    drawDoku();
});

// 마우스 이벤트
$().on("click", (e) => {
    let $e = $(e.target);
    if (!$e.hasClass('cell-on')) {
        $(".cell-on").removeClass("cell-on");
    }
});

// 키 이벤트
$().on("keydown", (e) => {
    let key = Number(e.key);
    let keyCode = e.keyCode;
    let $target = $(".cell-on");

    if ($target.length > 0) {
        // cell-on 숫자값 입력
        if (key && (key > 0 && key <= 9)) {
            $target.text(key);
        }
        else if (keyCode == 8) {
            $target.text("");
        }
        // cell-on 방향 이동
        else if (keyCode >= 37 && keyCode <= 40) {
            $cells = $(".cell");

            // left
            if (keyCode == 37) {
                cell_index--;
            }
            // right
            else if (keyCode == 39) {
                cell_index++;
            }
            // up
            else if (keyCode == 38) {
                cell_index -= 9;
            }
            //down
            else {
                cell_index += 9;
            }

            cell_index %= 81;

            $(".cell-on").removeClass("cell-on");
            $cells.eq(cell_index).addClass("cell-on");
            e.preventDefault();
        }

    }
});

// 버튼 이벤트
$("#resetBtn").on("click", () => {
    $("#card").addClass("haha");
    drawDoku();
    setTimeout(() => {
        $("#card").removeClass("haha");
    }, 800);
});

$("#runBtn").on("click", runDoku);

// #############################################################
//                           Funtion
// #############################################################

// 함수
function runDoku() {
    let $cells = $(".cell");
    let dokuData = "";

    for (let i = 0; i < $cells.length; i++) {
        let num = $cells.eq(i).text();
        dokuData += num !== undefined && num.length == 1 ? num : '0';
    }

    if (dokuData !== undefined && dokuData.length == 81) {
        let doku = new Doku(dokuData);
        $("#sideBtn").hide();
        $("#sideSpinner").show();

        setTimeout(() => {
            doku.solution();
            if (doku.isComplete()) {
                writeDoku(doku.getString());
            }
            else {
                let $h2 = $("#card h2");
                $("#card").addClass("shake");
                $h2.removeClass("text-primary");
                $h2.addClass("text-danger");

                setTimeout(() => {
                    $("#card").removeClass("shake");
                    $h2.removeClass("text-danger");
                    $h2.addClass("text-primary");
                }, 800);
            }
            $("#sideSpinner").hide();
            $("#sideBtn").show();
        }, 100);
    }
}

function writeDoku(data) {
    if (data !== undefined && data.length == 81) {
        let $cells = $(".cell");

        for (let i = 0; i < $cells.length; i++) {
            $cells.eq(i).text(data.charAt(i));
        }
    }
}

function drawDoku() {
    let html = '';
    for (let i = 0; i < 9; i++) {
        html += '<tr>'
        for (let j = 0; j < 9; j++) {
            html += '<td><div class="cell"></div></td>';
        }
        html += '</tr>'
    }
    $table.html(html);

    $(".cell").on("click", (e) => {
        let $e = $(e.target);
        $(".cell-on").removeClass("cell-on");
        $e.addClass("cell-on");

        // 활성화된 cell 인덱스 Get
        $cells = $(".cell");
        for (let i = 0; i < $cells.length; i++) {
            if ($cells.eq(i).hasClass("cell-on")) {
                cell_index = i;
                break;
            }
        }
    });
    // writeDoku(gugus.d);
}