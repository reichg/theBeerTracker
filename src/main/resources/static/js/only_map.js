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

        }



}