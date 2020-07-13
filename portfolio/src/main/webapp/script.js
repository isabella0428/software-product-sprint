/**
Author: Yi Lyu
Date:   2020.07.08
Email:  isabella_aus_china@outlook.com
*/

window.addEventListener("load", init);

function init() {
    var path = window.location.pathname;
    var page = path.split("/").pop();
    console.log(page);
    if (page == "comment.html") {
        console.log("Aloha");
        getAllComment();
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
        console.log("ALoha");
        console.log(comments);
        for (const [key, value] of Object.entries(comments)) {
            var divElement = document.createElement("div");
            divElement.setAttribute("class", "comment alignLeft");
            
            var spanElement = document.createElement("span");
            spanElement.innerText = value;
            divElement.appendChild(spanElement);
            allCommentElement.appendChild(divElement);
        };
    });
}