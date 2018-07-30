var width = getWidth();
var largeBadgeHidden = false;
var smallBadgeVisible = false;

var stageOne = function() {

    var desiredLeft = 81;
    var desiredRight = 81;

    var startingLeft = ((((width - 850) / 2) + 334) * -1);
    var startingRight = ((((width - 850) / 2) + 346) * -1);

    var carLeft = document.getElementById("car_left");
    var leftPos = startingLeft;
    var carRight = document.getElementById("car_right");
    var rightPos = startingRight;

    var timer = setInterval(function() {

        if ((leftPos >= desiredLeft) && (rightPos >= desiredRight)) {
            carLeft.style.left = desiredLeft + "px";
            carLeft.style.display = "block";

            carRight.style.right = desiredRight + "px";
            carRight.style.display = "block";

            clearInterval(timer);
            stageTwo();
            return;
        }

        if ((leftPos >= (desiredLeft - 40)) && (!largeBadgeHidden)) {
            largeBadgeHidden = true;
            animate.fadeOut(['badge_large'], 10);
            animate.fadeIn(['badge'], 10);
        }

        leftPos = leftPos + 8;
        if (leftPos < desiredLeft) {
            carLeft.style.left = leftPos + "px";
            carLeft.style.display = "block";
        }

        rightPos = rightPos + 8;
        if (rightPos < desiredRight) {
            carRight.style.right = rightPos + "px";
            carRight.style.display = "block";
        }

    }, 10);

}

var stageTwo = function() {
    document.getElementById("trunk").style.display = "block";
    document.getElementById("sound").style.display = "block";
    document.getElementById("car_left").className = 'car_left  static';
    document.getElementById("car_right").className = 'car_right static';
    setTimeout(stageThree, 500);
}

var stageThree = function() {
    document.getElementById("bubble_ohno").style.display = "block";
    document.getElementById("bubble_yikes").style.display = "block";
    setTimeout(stageFour, 800);
}
var stageFour = function() {
    animate.fadeIn(['content_home_copy', 'btn_getstarted', 'content_home_h1'], 5);
}

// DOM Ready
contentLoaded(window, function(e) {

    stageOne();

    if (!isIpad) {
        var iFrameLoaded = false;
        var dialog_email = new Modal({
            contentID: "modal_c_email",
            closeBtnClass: "modal-close",
            trigger: "modal-email",
            triggerType: "class",
            before: function(e) {
                if (!iFrameLoaded) {
                  var el = document.createElement("div");
                  var att = document.createAttribute("class");       // Create a "class" attribute
                  att.value = "ModalShell";                           // Set the value of the class attribute
                  el.setAttributeNode(att);
                    el.innerHTML = '<iframe src="' + e.href + '" class="ModalFrame" scrolling="no" frameborder="0" onload="window.scrollTo(0, 150); this.style.display=\'block\';"></iframe>';
                    document.getElementById("modal_c_email").appendChild(el);
                    iFrameLoaded = true;
                }
            }
        });
    }

});
