

function initMap() {
    var myLatLng = {lat: -25.363, lng: 131.044};
    var mypoint2 = {lat: -25.000, lng: 131.044};
    var jsonBom = $('#jsonBom').val();
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 4,
        center: myLatLng
    });

    var marker = new google.maps.Marker({
        position: myLatLng,
        map: map,
        title: 'Location1'
    });
    var marker2 = new google.maps.Marker({
        position: mypoint2,
        map: map,
        title: 'Location2'


        });
}