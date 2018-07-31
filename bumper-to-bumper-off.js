var width = getWidth();

var desiredLeft = 81;
var desiredRight = 81;

var carLeft = document.getElementById("car_left");
var carRight = document.getElementById("car_right");

var stageOne = function() {
    console.log('Starting "animation"');

    // stage 1
    // animate.fadeOut(['badge_large'], 50);
    // animate.fadeIn(['badge'], 50);

    carLeft.style.left = desiredLeft + "px";
    carLeft.style.display = "block";

    carRight.style.right = desiredRight + "px";
    carRight.style.display = "block";

    // stage 2
    document.getElementById("trunk").style.display = "block";
    document.getElementById("sound").style.display = "block";
    carLeft.className = 'car_left  static';
    carRight.className = 'car_right static';

    // stage 3
    document.getElementById("bubble_ohno").style.display = "block";
    document.getElementById("bubble_yikes").style.display = "block";

    // stage 4
    var els = ['content_home_copy', 'btn_getstarted', 'content_home_h1'];
    for (var i = 0; i < els.length; i++) {
      var el = document.getElementById(els[i]);
      el.style.opacity = 1;
      el.style.filter = 'alpha(opacity = 100)';
    }
    console.log('Finished "animating"');
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
