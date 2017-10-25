function initMap() {
    var obj = JSON.parse($("#locations").val());
    var obj2 = JSON.parse($("#userLocation").val());
    var userLocation = {lat: obj2.latitude, lng: obj2.longitude};
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 8,
        center: userLocation,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    });

    var markers = [];

    //alert("i'm here");
    setMarkers();

    function setMarkers()  {
        var contentStrings = [];
        var i, marker;
        for (i = 0; i < obj.length; i++){
            var contentString = '<div id="content">'+
                '<div id="siteNotice">'+
                   '</div>'+
                      '<h6 >'+ obj[i].name +'</h6>'+
                      '<div id="bodyContent">'+
                        '<p>description:' + obj[i].description +  '</p>'+
                        '<p>address:' + obj[i].streetNumber +  '</p>'+
                        '<p>' + obj[i].route +  '</p>'+
                        '<p>' + obj[i].postalCode +  '</p>'+
                        '<p>' + obj[i].administrativeAreaLevel1 +  '</p>'+
                        '<p>' + obj[i].administrativeAreaLevel2 +  '</p>'+
                        '<p>phone:' + obj[i].phone +  '</p>'+
                        '<p>web:' + obj[i].webSite +  '</p>'+

                     '</div>'+
                   '</div>'+
                '</div>';
            contentStrings.push(contentString);
            var infowindow = new google.maps.InfoWindow({
                content: contentString});
            var curMarker = {lat: obj[i].latitude, lng: obj[i].longitude};
            marker = new google.maps.Marker({
                //  position: new google.maps.LatLng(obj[i].latitude, obj[i].longitude),
                position: curMarker,
                animation: google.maps.Animation.DROP,
                map: map,
                title: obj[i].name,
                infowindow: infowindow
            });
            marker.addListener('click', function() {
                this.infowindow.open(map, this);
            });
            markers.push(marker);
        }

}




}