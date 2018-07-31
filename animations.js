/*
* File: animations.js
* Author: T3
*/

/* */
var Reveal = function(element, width, height, direction, speed) {
    this.setOptions(element, width, height, direction, speed);
    this.initialize();
};

Reveal.prototype = {

    getStyle: function(element, styleProp) {
        var elem = (typeof element == 'string') ? document.getElementById(element) : element;
        var result = false;

        if (elem.currentStyle)
            result = elem.currentStyle[styleProp];
        else if (window.getComputedStyle)
            result = document.defaultView.getComputedStyle(elem, null).getPropertyValue(styleProp);

        return result;
    },

    setOptions: function(element, width, height, direction, speed) {
        this.element = (typeof element == 'string') ? document.getElementById(element) : element;
        this.current = 0;
        this.width = width;
        this.height = height;
        this.parent = this.element.parentNode;
        this.direction = direction ? direction : "down";
        if (direction == "left" || direction == "right") {
            this.desired = width;
        } else {
            this.desired = height;
        }
        this.target = element;
        this.speed = speed;
        this.id = this.element.id;
    },

    initialize: function() {

        var dv1 = document.createElement('div');
        dv1.id = this.element.id;
        dv1.style.height = this.height + "px";
        dv1.style.width = this.width + "px";
        dv1.style.display = "block";

        var dv2 = document.createElement('div');
        dv2.style.overflow = "hidden";
        dv2.style.position = "absolute";

        var myImg = document.createElement('img');
        myImg.src = this.element.src;
        myImg.style.position = "absolute";

        switch (this.direction) {
            case "up":
                dv2.style.top = "0";
                dv2.style.height = "0";
                dv2.style.width = this.width + "px";
                myImg.style.top = "0";
                break;
            case "left":
                dv2.style.top = "0";
                dv2.style.height = this.height + "px";
                dv2.style.width = "0";
                myImg.style.top = "0";
                break;
            case "right":
                dv2.style.top = "0";
                dv2.style.right = "0";
                dv2.style.height = this.height + "px";
                dv2.style.width = "0";
                myImg.style.top = "0";
                myImg.style.right = "0";
                break;
            default:
                dv2.style.bottom = "0";
                dv2.style.height = "0";
                dv2.style.width = this.width + "px";
                myImg.style.bottom = "0";
        }


        dv2.appendChild(myImg);
        dv1.appendChild(dv2);
        this.parent.removeChild(this.element);
        this.parent.appendChild(dv1);
        this.target = dv2;

        // var self = this;
        // self.si = setInterval(function() { self.animate() }, 20);
        window.requestAnimationFrame(this.animate.bind(this));
    },

    animate: function() {
        if (this.current >= this.desired) {
            // clearInterval(this.si);
            return;
        }
        var newValue = this.current + this.speed;
        if (this.direction == "left" || this.direction == "right") {
            this.target.style.width = newValue + "px";
            this.current = newValue;
        } else {
            this.target.style.height = newValue + "px";
            this.current = newValue;
        }

        window.requestAnimationFrame(this.animate.bind(this));
    }

};

/*

Speed: 1-10 (higher is faster)

*/
var Fade = function(element, duration, state) {
    this.setOptions(element, duration, state);
    this.initiate();
};

Fade.prototype = {

    setOptions: function(element, speed, state) {
        this.element = (typeof element == 'string') ? document.getElementById(element) : element;
        this.id = this.element.id;
        this.fadeState = (state && state == 1) ? 1 : 0;
        this.state = state;
        this.speed = speed ? speed : 5;
        this.tick = this.speed * .02;
    },

    initiate: function() {
        // var self = this;
        // this.si = setInterval(function() { self.animate() }, 20);
        window.requestAnimationFrame(this.animate.bind(this));
    },

    animate: function() {
        var reAnimate = true;
        if (this.state == 0) {
            this.fadeState = this.fadeState + this.tick;
            if (this.fadeState >= 1) {
                this.fadeState = 1;
                // clearInterval(this.si);
                reAnimate = false;
            }
        }

        if (this.state == 1) {
            this.fadeState = this.fadeState - this.tick;
            if (this.fadeState <= 0) {
                this.fadeState = 0;
                // clearInterval(this.si);
                reAnimate = false;
            }
        }

        this.element.style.opacity = this.fadeState;
        this.element.style.filter = 'alpha(opacity = ' + (this.fadeState * 100) + ')';

        if (reAnimate) {
          window.requestAnimationFrame(this.animate.bind(this));
        }
    }

};

/* */
var animate = {

    reveal: function(element, width, height, direction, speed) {
        for (var i = 0, l = element.length; i < l; i++) {
            new Reveal(element[i], width, height, direction, speed);
        }
    },

    fadeIn: function(element, speed, state) {
        for (var i = 0, l = element.length; i < l; i++) {
            new Fade(element[i], speed, 0);
        }
    },

    fadeOut: function(element, speed, state) {
        for (var i = 0, l = element.length; i < l; i++) {
            new Fade(element[i], speed, 1);
        }
    }

}
