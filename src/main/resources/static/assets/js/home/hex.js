function drawHex(oHexData) {
    let sHtml = '';
    if (oHexData && oHexData.length > 0) {
        for (var i = 0; i < oHexData.length; i++) {
            let sw = false;

            let oHex = oHexData[i];
            sHtml += '<li class="hex">';
            sHtml += '<div class="hexIn">';
            sHtml += '<a class="hexLink" href="' + (oHex.requestUrl ? oHex.requestUrl : '#') + '">';
            sHtml += '<img src="' + oHex.menuImage + '" alt="" />';
            // sHtml += '<img src="/assets/img/home/default.jpg" alt="" />';
            // sHtml += '<p>' + (oHex.title ? oHex.title : '') + '</p>';
            sHtml += '</a>';
            sHtml += '</div>';
            sHtml += '</li>';
        }
    }
    return sHtml;
}