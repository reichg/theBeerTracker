function initMap() {
    var obj = JSON.parse($("#locations").val());
    var obj2 = JSON.parse($("#userLocation").val());
    var userLocation = {lat: obj2.latitude, lng: obj2.longitude};
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 4,
        center: userLocation,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    });
    var marker, i;
    var markers = [];

    // var infowindow = new google.maps.InfoWindow();

    for (i = 0; i < obj.length; i++) {
        var curMarker = {lat: obj[i].latitude, lng: obj[i].longitude};
        marker = new google.maps.Marker({
            //  position: new google.maps.LatLng(obj[i].latitude, obj[i].longitude),
            position: curMarker,
            map: map
        });
        // alert( marker.position);
        markers.push(marker);

        var infoWindow = new google.maps.InfoWindow({map: map});

        // Try HTML5 geolocation.
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(position) {
                var pos = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude
                };

                infoWindow.setPosition(pos);
                infoWindow.setContent('Location found.');
                map.setCenter(pos);
                var myPosition = JSON.stringify(pos);
                $("#myPosition").val(myPosition);
            }, function() {
                handleLocationError(true, infoWindow, map.getCenter());
            });
        } else {
            // Browser doesn't support Geolocation
            handleLocationError(false, infoWindow, map.getCenter());
        }
    }

    function handleLocationError(browserHasGeolocation, infoWindow, pos) {
        infoWindow.setPosition(pos);
        infoWindow.setContent(browserHasGeolocation ?
            'Error: The Geolocation service failed.' :
            'Error: Your browser doesn\'t support geolocation.');


        /*        google.maps.event.addListener(marker, 'click', (function(marker, i) {
                    return function() {
                        infowindow.setContent(locations[i][0]);
                        infowindow.open(map, marker);
                    }
                })(marker, i));*/
    }




    /*
        var marker3 = new google.maps.Marker({
            position: myLatLng3,
            map: map,
            title: 'Location1'
        });*/

}