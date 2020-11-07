var token =  'pk.eyJ1IjoicG9seWFrem9sdGFuIiwiYSI6ImNraDZ0dmF6azAwcmQycnQzY2wwcjZ5ZzQifQ.laHDj0D6EQBaGrCfwIYzLA';
var mapview = L.map(
    'map',
    {
        center: [
            60.192059,
            24.945831
        ],
        minZoom: 5,
        maxZoom: 20,
        zoom: 13
    }
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

var categories = {},
    category = 'Growth rate';

var layersControl = L.control.layers(null, null).addTo(mapview);

// "http://35.205.22.186/api/getAll"
$.getJSON("http://35.205.22.186/api/getAll", function (data) {
    var geoCoords = [];
    var getGrowths = [];
    var getRgbGrowths = [];

    data.forEach(function (item) {
        geoCoords.push(item['geoJson']);
        getGrowths.push(item['growthRate']);
    });

    var max = Math.max(...getGrowths);

    getGrowths.forEach(function (item) {
        var percentage = item/max;
        percentage = percentage.toFixed(3) * 1;
        percentage = Math.round(percentage * 255);
        percentage = percentage.toString(16);

        if (percentage.length == 1 ) {
            percentage = '0' + percentage;
        }

        var hex = '#66bb' + percentage;
        getRgbGrowths.push(hex);
    });
    var i = 0;
    console.log(geoCoords);
    var geoJsonLayer = L.geoJson(
        geoCoords,
        {
            style: function () {
                return {
                    color: '#11aaff55',
                    fillOpacity: .5
                };
            },
            onEachFeature: function (feature, layer) {
                layer.setStyle({fillColor : getRgbGrowths[i]});
                i++;
                // console.log(layer)
                var popupText = "<b>" + feature.properties.name + "</b>";
                layer.bindPopup(popupText);
                layer.on('mouseover', function (e) {
                    this.openPopup();
                });

                if (typeof categories[category] === "undefined") {
                    categories[category] = L.layerGroup().addTo(mapview);
                    layersControl.addOverlay(categories[category], category);
                }
                categories[category].addLayer(layer);
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
            // $(".map-path").click(function () {
            //      $(this).css({ fill: '#00ff00' });
            // });
        },
        1000
    );
});

/**
 * Switch handle
 */
$(".switch input[type='checkbox']").click(function () {
    var switcher = $('.switch-label');

    if (switcher.text().match(/statistics/)) {
        switcher.text('Change to details');
        switcher.addClass('switch-statistics');
        switcher.removeClass('switch-details');
    } else {
        switcher.text('Change to statistics');
        switcher.addClass('switch-details');
        switcher.removeClass('switch-statistics');
    }
});
