var width = getWidth();
var largeBadgeHidden = false;
var smallBadgeVisible = false;

var desiredLeft = 81;
var desiredRight = 81;

var stepSize = 1;

var startingLeft = ((((width - 850) / 2) + 334) * -1);
var startingRight = ((((width - 850) / 2) + 346) * -1);

var carLeft = document.getElementById("car_left");
var carRight = document.getElementById("car_right");


var leftPos = startingLeft;
var rightPos = startingRight;


// some simple polyfills
window.requestAnimationFrame = window.requestAnimationFrame
    || window.mozRequestAnimationFrame
    || window.webkitRequestAnimationFrame
    || window.msRequestAnimationFrame
    || function (f) { return setTimeout(f, 1000/60) } // simulate calling code 60

window.cancelAnimationFrame = window.cancelAnimationFrame
    || window.mozCancelAnimationFrame
    || function (requestID) { clearTimeout(requestID) } //fall back



function step1 (timestamp) {
  if ((leftPos >= desiredLeft) && (rightPos >= desiredRight)) {
    carLeft.style.left = desiredLeft + "px";
    carLeft.style.display = "block";

    carRight.style.right = desiredRight + "px";
    carRight.style.display = "block";

    console.log('Going on to stage 2');
    window.requestAnimationFrame(step2);
    return;
  }

  if ((leftPos >= (desiredLeft - 40)) && (!largeBadgeHidden)) {
    largeBadgeHidden = true;
    animate.fadeOut(['badge_large'], 10);
    animate.fadeIn(['badge'], 10);
  }

  leftPos = leftPos + stepSize;
  if (leftPos < desiredLeft) {
    carLeft.style.left = leftPos + "px";
    carLeft.style.display = "block";
  }

  rightPos = rightPos + stepSize;
  if (rightPos < desiredRight) {
    carRight.style.right = rightPos + "px";
    carRight.style.display = "block";
  }
  console.log('r: ' + rightPos + ' (' + desiredRight + '), l: ' + leftPos + ' (' + desiredLeft + ')');
  window.requestAnimationFrame(step1);
}

function step2 (timestamp) {
  console.log('Stage 2');
  document.getElementById("trunk").style.display = "block";
  document.getElementById("sound").style.display = "block";
  carLeft.className = 'car_left  static';
  carRight.className = 'car_right static';
  setTimeout(function () {
    console.log('Going on to stage 3');
    window.requestAnimationFrame(step3);
  }, 400);
}

function step3 (timestamp) {
    console.log('Stage 3');
    document.getElementById("bubble_ohno").style.display = "block";
    document.getElementById("bubble_yikes").style.display = "block";
    setTimeout(function () {
      console.log('Going on to stage 4');
      window.requestAnimationFrame(step4);
    }, 800);
}

function step4 (timestamp) {
    animate.fadeIn(['content_home_copy', 'btn_getstarted', 'content_home_h1'], 5);
    console.log('Finished stage 4');
}

// DOM Ready
contentLoaded(window, function(e) {
    window.requestAnimationFrame(step1);
});
