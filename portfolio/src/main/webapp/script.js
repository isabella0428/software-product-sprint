/**
Author: Yi Lyu
Date:   2020.07.08
Email:  isabella_aus_china@outlook.com
*/

window.addEventListener("load", init);
var loginUrl, logoutUrl;

function init() {
    var path = window.location.pathname;
    var page = path.split("/").pop();
    if (page == "comment.html") {
        getAllComment();
    }

    if (page == "intro.html") {
        accountStatus();
    }
}

// The page will jump to intro page
function jumpToIntro() {
    window.location.href="/intro.html";
}

// The page will jump to comment page
function jumpToComment() {
    window.location.replace("/comment.html");
}

function getAllComment() {
    var allCommentElement = document.getElementById("commentCol");

    fetch("/comment").then(response => response.json()).then(comments=> {
        console.log(comments);
        for (const [key, value] of Object.entries(comments)) {
            const arr = value.split(" ");
            const length = arr.length;
            const email = arr[length-1];

            console.log(email);

            var content = arr.slice(0, arr.length-1).join(" ");
            content = email + ":\n      " + content;

            console.log(content);

            var divElement = document.createElement("div");
            divElement.setAttribute("class", "comment alignLeft");
            
            var spanElement = document.createElement("span");
            spanElement.innerText = content;
            divElement.appendChild(spanElement);
            allCommentElement.appendChild(divElement);
        };
    });
}

function testLoginStatus() {
    fetch("/login").then(status => {
        console.log(status);
        if (status == "true") {

        } else {

        }
    });
}

async function accountStatus() {
    fetch("/login").then(response => response.json()).then(res => {
        var div = document.getElementById("formOrLogin");

        for (const [key, value] of Object.entries(res)) {
            if (key == "loginUrl") {
                loginUrl = value;
                div.innerHTML = "<button onclick=\"jumpToComment();\"> See all Comment </button>\n"
                    + "<button onclick=\"login();\"> Login Account</button>";
            } else {
                logoutUrl = value;
                div.innerHTML = "<!--Comment my website-->\n"
                    + "<form method=\"post\" action=\"comment\">\n"
                        + "<label for=\"comment\">Leave a comment:</label><br>"
                        + "<textarea name=\"newComment\" rows=\"5\" cols=\"20\"></textarea> <br>"
                        + "<input type=\"submit\" value=\"Submit Comment\"/>"
                    + "</form>\n"
                    + "<button onclick=\"jumpToComment();\"> See all Comment </button>\n"
                    + "<button onclick=\"logout();\"> Logout Account</button>";
            }
        };
    });
}

function login() {
    window.location.href = loginUrl;
}

function logout() {
    console.log(logoutUrl);
    window.location.href = logoutUrl;
}