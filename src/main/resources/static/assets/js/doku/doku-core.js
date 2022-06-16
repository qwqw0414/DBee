class Doku {
    doku = new Array();
    dokum = new Array();
    dokus;
    limit = 2;

    constructor(doku) {
        this.doku = this.parseDoku(doku);
    }

    // Getter, Setter
    setLimit(limit) {
        this.limit = limit;
    }

    getDoku() {
        return this.doku;
    }

    getString() {
        let result = '';
        if(this.doku.length == 9){
            for(let i = 0; i < 9; i++){
                for(let j = 0; j < 9; j++){
                    result += this.doku[i][j];
                }
            }
        }
        return result;
    }

    getDokum() {
        return this.dokum;
    }

    get(row, col) {
        return this.doku[row][col];
    }

    getRow(row, doku) {
        let line = new Array();
        for (let i = 0; i < 9; i++) {
            line[i] = doku[row][i];
        }
        return line;
    }

    getCol(col, doku) {
        let line = new Array();
        for (let i = 0; i < 9; i++) {
            line[i] = doku[i][col];
        }
        return line;
    }

    getBlock(row, col, doku) {
        let line = new Array();
        if (col !== undefined) {
            for (let i = 0; i < 9; i++) {
                let y = Math.floor(i / 3) + Math.floor(row / 3) * 3;
                let x = Math.floor(i % 3) + Math.floor(col / 3) * 3;
                line[i] = doku[y][x];
            }
        } else {
            for (let i = 0; i < 9; i++) {
                let y = Math.floor(i / 3) + Math.floor(row / 3) * 3;
                let x = Math.floor(i % 3) + Math.floor(row % 3) * 3;
                line[i] = doku[y][x];
            }
        }
        return line;
    }

    setDoku(doku) {
        this.doku = this.parseDoku(doku);
    }

    set(row, col, num) {
        this.doku[row][col] = Number(num);
        // console.log("SET : [" + row + ", " + col + "] = " + num);
    }

    copy(arr) {
        let doku = new Array();
        for (let i = 0; i < 9; i++) {
            let line = new Array();
            for (let j = 0; j < 9; j++) {
                line[j] = arr[i][j];
            }
            doku[i] = line;
        }
        return doku;
    }

    setDokum() {
        let dokum = new Array();
        for (let i = 0; i < 9; i++) {
            let line = new Array();
            for (let j = 0; j < 9; j++) {
                let val = '';
                if (this.get(i, j) === 0) {
                    for (let n = 1; n <= 9; n++) {
                        if (this.isValid(i, j, n, this.getDoku())) {
                            val += String(n);
                        }
                    }
                }
                line[j] = val;
            }
            dokum[i] = line;
        }
        this.dokum = dokum;
    }

    // solution
    low() {
        for (let i = 0; i < 9; i++) {
            for (let j = 0; j < 9; j++) {
                // 해당 위치에 값이 0인 경우 진행
                if (this.get(i, j) === 0) {
                    let cnt = 0;
                    let num = 0;
                    for (let n = 1; n <= 9; n++) {
                        if (this.isValid(i, j, n, this.getDoku())) {
                            num = n;
                            cnt++;
                        }
                    }
                    // 유일한 값인 경우 해당 넘버 Set
                    if (cnt === 1) {
                        this.set(i, j, num);
                    }
                }
            }
        }
    }

    high() {
        this.setDokum();
        for(let i = 0; i < 9; i++){
            for(let j = 0; j < 9; j++){
                if(this.dokum[i][j].length > 0){
                    for(let n = 1; n <= 9; n++){
                        if (this.isValid(i, j, n, this.doku) && this.isPrimaryAll(i, j, n, this.dokum)) {
                            this.set(i, j, n);
                        }
                    }
                }
            }
        }
    }

    low_high() {
        let try_cnt = 0;
        let type = true;
        let zero = -1;

        while(try_cnt++ < this.limit && this.countOfZero() > 0){
            zero = this.countOfZero();
            // console.log("STEP:" + try_cnt, "N:" + zero);

            // 처리
            type ? this.low() : this.high();

            // 처리 모드 변경
            if(this.countOfZero() == zero){
                // 진행 불가능한 경우 모드 변경
                // console.log("mode change");
                type = !type;
            } else {
                // 진행 가능한 경우 시도 수 초기화
                try_cnt = 0;
            }
        }

        // console.log("Complete=" + this.isComplete());
    }

    solution() {
        let date = new Date();
        this.low_high();
        if(!this.isComplete()){
            // console.log("Start : Random");
            this.dokus = this.copy(this.doku);
            outer:
            for(let i = 0; i < 9; i++){
                for(let j = 0; j < 9; j++){
                    // 해당 위치에 값이 0일 경우 진행
                    if(this.doku[i][j] != 0) continue;
                    // 순차 대입
                    for(let n = 1; n <= 9; n++){
                        if (this.isValid(i, j, n, this.dokus)) {
                            this.doku = this.copy(this.dokus);
                            this.set(i, j, n);
                            // console.log("Seq:", i, j, n);
                            this.low_high();
                            if (this.isComplete()) {
                                break outer;
                            }
                        }
                    }
                }
            }

        }
        console.log("Timer : " + (new Date() - date) + "/ms");
    }

    // valid check
    isValid(row, col, num, doku) {
        return !(this.getRow(row, doku).includes(num) || 
                 this.getCol(col, doku).includes(num) || 
                 this.getBlock(row, col, doku).includes(num));
    }

    isPrimaryAll(row, col, num) {
        return this.isPrimary(this.getRow(row,this.dokum), num) || 
               this.isPrimary(this.getCol(col,this.dokum), num) ||
               this.isPrimary(this.getBlock(row, col, this.dokum), num);
    }

    isPrimary(line, num) {
        let cnt = 0;
        for (let i = 0; i < 9; i++) {
            if (line[i].includes(String(num)))
                cnt++;
        }
        return cnt === 1;
    }

    // Util
    print() {
        this.doku.forEach(row => {console.log(row);});
    }

    countOfZero() {
        let cnt = 0;
        for (let i = 0; i < 9; i++) {
            for (let j = 0; j < 9; j++) {
                this.doku[i][j] === 0 ? cnt++ : cnt;
            }
        }
        return cnt;
    }

    hasZero() {
        return this.countOfZero !== 0;
    }

    parseDoku(doku) {
        let result = new Array();

        // String => String[][]
        if (typeof (doku) === 'string' && doku.length === 81) {
            for (let i = 0; i < 9; i++) {
                let row = new Array();
                for (let j = 0; j < 9; j++) {
                    row[j] = Number(doku.charAt(i * 9 + j));
                }
                result.push(row);
            }
        }

        // String[] => String[][]
        else if (typeof (doku) === 'object' && doku.length === 9) {
            let row = new Array();
            for (let i = 0; i < 9; i++) {
                let row = new Array();
                for (let j = 0; j < 9; j++) {
                    row[j] = Number(doku[i][j]);
                }
                result.push(row);
            }
        }
        return result;
    }

    isComplete() {
        let cntArr = [0, 0, 0, 0, 0, 0, 0, 0, 0];
        let isValid = true;

        for (let i = 0; i < 9; i++) {
            for (let j = 0; j < 9; j++) {
                let n = this.doku[i][j] - 1;
                if (n >= 0 && n < 9)
                    cntArr[n]++;
            }
        }

        for(let i = 0; i < 9; i++){
            if(cntArr[i] != 9){
                isValid = false;
                break;
            }
        }

        return isValid;
    }
}