$( document ).ready(function() {
    console.log( "page ready!" );

    var bottleImages = [
        "amber-beer-bottle-foil-neck.png",
        "amber-beer-bottle-square-etiquette.png",
        "amber-beer-steinie-bottle.png",
        "brown-beer-bottle.png",
        //"clear-beer-bottle.png",
        //"dark-beer-bottle.png",
        "green-beer-bottle-etiquette.png",
        "green-beer-bottle-neck-foil.png",
        "green-beer-bottle.png",
        "lt-brown-beer-bottle.png"
    ];

    var rand = Math.floor(Math.random() * bottleImages.length);
    console.log(rand);
    document.getElementById('bottle-image').src = '\img\\' + bottleImages[rand];
});