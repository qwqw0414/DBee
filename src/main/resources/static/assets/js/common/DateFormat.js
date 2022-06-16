class DateFormat {

    date = new Date();
    yaer;
    month;
    day;
    hour;
    min;
    sec;

    /**
     * 
     * @param {*} date 대상 날짜
     */
    constructor(date) {
        if (!date) {
            this.date = new Date();
        } else if (date instanceof Date) {
            this.date = date;
        } else {
            this.date = new Date(date);
        }
        this.yaer = (String)(this.date.getFullYear());
        this.month = (String)(this.date.getMonth() + 1);
        this.day = (String)(this.date.getDate());
        this.hour = (String)(this.date.getHours());
        this.min = (String)(this.date.getMinutes());
        this.sec = (String)(this.date.getSeconds());
    }

    /**
     * 날짜 포맷
     * @param {String} format 포맷 형태
     * @returns 
     */
    format(format) {
        return format
            .replace('YYYY', this.yaer)
            .replace('YY', this.yaer.substring(2))
            .replace('MM', this.zeroParse(this.month))
            .replace('M', this.month)
            .replace('DD', this.zeroParse(this.day))
            .replace('D', this.day)
            .replace('hh', this.zeroParse(this.hour))
            .replace('h', this.hour)
            .replace('mm', this.zeroParse(this.min))
            .replace('m', this.min)
            .replace('ss', this.zeroParse(this.sec))
            .replace('s', this.sec)
    }

    zeroParse(text) {
        return text.length > 1 ? text : '0' + text;
    }
}