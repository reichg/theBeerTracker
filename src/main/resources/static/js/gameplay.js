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

    var savedBeer = localStorage.getItem("savedBeer");
    var savedImage = localStorage.getItem("savedImage");
    var currentBeer = document.getElementsByClassName('beer-name')[0].outerText;

    console.log(savedImage == null || savedBeer != currentBeer )
    if(savedImage == null || savedBeer != currentBeer ) {
      var rand = Math.floor(Math.random() * bottleImages.length);
      currentImage = '\img\\' + bottleImages[rand];
      localStorage.setItem("savedImage", (currentImage).toString());
      console.log('random going')
    } else {
      currentImage = savedImage;
    }

    localStorage.setItem("savedBeer", (currentBeer).toString());

    document.getElementById('bottle-image').src = currentImage;

});