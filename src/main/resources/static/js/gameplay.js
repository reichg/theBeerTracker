$( document ).ready(function() {
    console.log( "page ready!" );

    var bottleImages = [
        "amber-beer-bottle-foil-neck.png",
        "amber-beer-bottle-square-etiquette.png",
        "amber-beer-steinie-bottle.png",
        "brown-beer-bottle.png",
        "\img\\clear-beer-bottle.png",
        "dark-beer-bottle.png",
        "green-beer-bottle-etiquette.png",
        "green-beer-bottle-neck-foil.png",
        "green-beer-bottle.png",
        "lt-brown-beer-bottle.png"
    ];

    document.getElementById('bottle-image').src = bottleImages[4];
});