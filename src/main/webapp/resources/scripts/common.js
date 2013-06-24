var cookie_name_font_size = "siteFontSize";

function adjustSize(indicator){

    var rule = getCSSRule(".ui-widget");

    //try to get fontsize from cookie if exist
    var currentSize = get_cookie(cookie_name_font_size);

    if (currentSize == null) {
        currentSize = rule.style.fontSize;
    }
    currentSize = parseFloat(currentSize);

    if (indicator == '+'){
        if (currentSize <= 120) {
            currentSize = currentSize + 5.0;
        }
    }
    else {
        if (currentSize > 62.5) {
            currentSize = currentSize - 5.0;
        }
    }

    //store fontSize into cookie
    rule.style.fontSize = currentSize + "%";
    set_cookie(cookie_name_font_size, rule.style.fontSize, 360); //cannot store % in cookie.
}

function getCSSRule(selectorText) {

    var i, j, noRules, rules, rule,
    // the regex is used for case insensitive string comparison
        regex = new RegExp("^" + selectorText+"$", "i"),
        sheets = document.styleSheets,
        noSheets = sheets.length;

    for (i = 0; i < noSheets; i++) {
        rules = (sheets[i].rules || sheets[i].cssRules);
        noRules = rules.length;
        for (j = 0; j < noRules; j++) {
            if (regex.test((rule = rules[j]).selectorText)) {

                if (sheets[i].href.indexOf("common-style.css") != -1) {
                    return rule;
                }
            }
        }
    }
}

function set_cookie ( cookie_name, cookie_value,
                      lifespan_in_days, valid_domain )
{
    // http://www.thesitewizard.com/javascripts/cookies.shtml
    var domain_string = valid_domain ?
        ("; domain=" + valid_domain) : '' ;
    document.cookie = cookie_name +
        "=" + encodeURIComponent( cookie_value ) +
        "; max-age=" + 60 * 60 *
        24 * lifespan_in_days +
        "; path=/" + domain_string ;
}
function get_cookie ( c_name )
{
    var c_value = document.cookie;
    var c_start = c_value.indexOf(" " + c_name + "=");
    if (c_start == -1)
    {
        c_start = c_value.indexOf(c_name + "=");
    }
    if (c_start == -1)
    {
        c_value = null;
    }
    else
    {
        c_start = c_value.indexOf("=", c_start) + 1;
        var c_end = c_value.indexOf(";", c_start);
        if (c_end == -1)
        {
            c_end = c_value.length;
        }
        c_value = unescape(c_value.substring(c_start,c_end));
    }
    return c_value;
}

function loadSiteFontSizeFromCookie() {
    var currentSize = get_cookie(cookie_name_font_size);

    if (currentSize != null) {
        var rule = getCSSRule("body");
        rule.style.fontSize = currentSize;
    }
}