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
        zoom: 13,
        preferCanvas: true
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
    growth_rate = 'Growth rate',
    real_estate = 'Real estates';

var layersControl = L.control.layers(null, null).addTo(mapview);

/**
 * GET Growth rate with geo coords
 */
$.getJSON("http://35.205.22.186/api/getAll", function (data) {
    var geoCoords = [];
    var getGrowths = [];
    var getRgbGrowths = [];

    data.forEach(function (item) {
        item['geoJson'].properties.name = item['name'];
        geoCoords.push(item['geoJson']);
        getGrowths.push(item['growthRate']);
    });

    var max = Math.max(...getGrowths);

    getGrowths.forEach(function (item) {
        var percentage = item/max;
        percentage = percentage.toFixed(3) * 1;
        percentage = Math.sqrt(percentage);
        percentage = percentage * 255;
        percentage = Math.round(percentage);
        getRgbGrowths.push(percentage);
    });

    var i = 0;
    var geoJsonLayer = L.geoJson(
        geoCoords,
        {
            style: function () {
                return {
                    color: '#11aaff55',
                    fillOpacity: 0.8
                };
            },
            onEachFeature: function (feature, layer) {
                layer.setStyle(
                    {
                        fillColor : "rgba(0, 80, " + getRgbGrowths[i] + ", 1)",
                    }
                );
                i++;

                var popupText = "<b>" + feature.properties.name + "</b>";
                layer.bindPopup(popupText);
                layer.on('mouseover', function () {
                    this.openPopup();
                });

                if (typeof categories[growth_rate] === "undefined") {
                    categories[growth_rate] = L.layerGroup().addTo(mapview);
                    layersControl.addOverlay(categories[growth_rate], growth_rate);
                }
                categories[growth_rate].addLayer(layer);
            },
            clickable: true
        }
    );
    geoJsonLayer.setStyle({className: 'map-path'});
    geoJsonLayer.addTo(mapview);
});

/**
 * MAIN
 */
$(window).on('load', function() {
    console.log('Page is ready...');
    setTimeout(
        function () {
            // console.log(1);
            // $("path").click(function () {
            //     console.log(231);
            //     $(this).css({ fill: '#000fff' });
            // });
        },
        2000
    );
});

/**
 * count :7871
 */
$.getJSON("http://35.205.22.186/api/estatesSimplified", function (data) {
    var render = L.canvas();
    console.log(data);
    data.forEach(function (item) {
        if (
            (item.coordinates != null)
            && (item.coordinates.Latitude != null)
            && (item.coordinates.Longitude != null)
        ) {
            L.circle(
                [
                    item.coordinates.Latitude,
                    item.coordinates.Longitude
                ],
                {
                    radius: 50,
                    fillColor: "#ff4500",
                    color: "#ff4500"
                }
            ).addTo(mapview).bindPopup('kcsa');
        }
    });
});

/**
 * Switch handle
 */
$(".switch input[type='checkbox']").click(function () {
    var switcher = $('.switch-label');
    var properties = $('.properties, .prediction');
    var charts = $('.charts-container');

    if (switcher.text().match(/statistics/)) {
        properties.fadeOut(200);
        setTimeout(
            function () {
                charts.fadeIn(200);
                switcher.text('Change to details');
                switcher.addClass('switch-statistics');
                switcher.removeClass('switch-details');
            },
            200
        );
    } else {
        charts.fadeOut(200);
        setTimeout(
            function () {
                properties.fadeIn(200);
                switcher.text('Change to statistics');
                switcher.addClass('switch-details');
                switcher.removeClass('switch-statistics');
            },
            200
        );
    }
});
