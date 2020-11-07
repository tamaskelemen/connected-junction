var token =  'pk.eyJ1IjoicG9seWFrem9sdGFuIiwiYSI6ImNraDZ0dmF6azAwcmQycnQzY2wwcjZ5ZzQifQ.laHDj0D6EQBaGrCfwIYzLA';
var mapview = L.map('map')
    .setView(
        [
            60.192059,
            24.945831
        ],
        10
    );

L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=' + token, {
    maxZoom: 18,
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
        '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
        'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    id: 'mapbox/light-v9',
    tileSize: 512,
    zoomOffset: -1
}).addTo(mapview);

$.getJSON("assets/finnland.geojson", function (data) {
    var geoJsonLayer = L.geoJson(
        data,
        {
            style: function (feature) {
                return { color: '#11aaff' };
            },
            onEachFeature: function (feature, layer) {

            },
            clickable: true
        }
    );
    geoJsonLayer.setStyle({'className': 'map-path'});
    geoJsonLayer.addTo(mapview);
});

/**
 * MAIN
 */
$(window).on('load', function() {
    console.log('Page is ready...');
    setTimeout(
        function () {
            $('.map-path').click(function () {
                $(this).css({ fill: '#f0f0f0' });
                $(this).powerTip({});
            });
        },
        1000
    );
});
