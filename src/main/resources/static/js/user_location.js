
getUserPosition();
function getUserPosition() {

        // Try HTML5 geolocation.
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(position) {
                var pos = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude
                };

                var myPosition = JSON.stringify(pos);
                $("#myPosition").val(myPosition);
            }, function() {
                handleLocationError(true);
            });
        } else {
            // Browser doesn't support Geolocation
            handleLocationError(false);
        }

    function handleLocationError(browserHasGeolocation) {
            alert("Error: The Geolocation service failed. Your browser doesn\'t support geolocation.");

    }


}