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
        zoom: 10,
        preferCanvas: true
    }
);

L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=' + token, {
    maxZoom: 18,
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
        '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
        'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    id: 'mapbox/streets-v9',
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
 * Estates
 */
$.getJSON("http://35.205.22.186/api/estatesSimplified", function (data) {
    data.forEach(function (item) {
        if (
            (item.coordinates != null)
            && (item.coordinates.Latitude != null)
            && (item.coordinates.Longitude != null)
        ) {
            var year = '',
                energy_class = '',
                plan = '',
                coating = '',
                rooms = '',
                area = '',
                renovation = '',
                rent = '',
                price = '',
                unencrumbed_price = '';

            if (item.buildingYearOfFirstUse != null && item.buildingYearOfFirstUse != 0) {
                year = item.buildingYearOfFirstUse;
            }

            if (item.buildingEnergyClass != null && item.buildingEnergyClass['@class'] != null) {
                energy_class = item.buildingEnergyClass['@class'];
            }

            if (item.buildingPlanSituation != null && item.buildingPlanSituation.length < 30) {
                plan = item.buildingPlanSituation;
            }

            if (item.housingCoating != null) {
                // coating = item.housingCoating;
            }

            if (item.housingNumberOfRooms != null) {
                rooms = item.housingNumberOfRooms;
            }

            if (item.housingTotalArea != null && item.housingTotalArea['@size'] != null && item.housingTotalArea['@unit'] != null) {
                area = item.housingTotalArea['@size'] + item.housingTotalArea['@unit'];
            }

            if (item.renovations != null) {
                if (item.renovations.Accepted != null) {
                    // renovation += item.renovations.Accepted + ',';
                }

                if (item.renovations.Done != null) {
                    // renovation += item.renovations.Done + ',';
                }

                if (item.renovations.OtherKnown != null) {
                    // renovation += item.renovations.OtherKnown + ',';
                }
            }

            if (item.rentPerMonth != null && item.rentPerMonth['@currency'] != null && item.rentPerMonth['@unit'] != null && item.rentPerMonth['@value'] != null) {
                rent = item.rentPerMonth['@value'] + item.rentPerMonth['@currency'] + ' per ' + item.rentPerMonth['@unit'];
            }

            if (item.salesPrice != null && item.salesPrice['@currency'] != null && item.salesPrice['@value'] != null) {
                price = item.salesPrice['@value'] + item.salesPrice['@currency'];
            }
            
            if (item.unencumberedSalesPrice != null && item.unencumberedSalesPrice['@currency'] != null && item.unencumberedSalesPrice['@value'] != null) {
                unencrumbed_price = item.unencumberedSalesPrice['@value'] + item.unencumberedSalesPrice['@currency'];
            }

            var markup = "<div>";

            if (energy_class != '' && energy_class != ' ') {
                markup += "<p class='buildingEnergyClass'>Energy class: " + energy_class + "</p>";
            }

            if (year != '' && year != ' ') {
                markup += "<p class='buildingEnergyClass'>Year of first use: " + year+ "</p>";
            }

            if (plan != '' && plan != ' ') {
                markup += "<p class='buildingPlanSituation'>Plan: " + plan + "</p>";
            }

            if (coating != '' && coating != ' ') {
                markup += "<p class='housingCoating'>Coating: " + coating + "</p>";
            }

            if (rooms != '' && rooms != ' ') {
                markup += "<p class='housingNumberOfRooms'>Rooms: " + rooms + "</p>";
            }

            if (area != '' && area != ' ') {
                markup += "<p class='housingTotalArea'>Area: " + area + "</p>";
            }

            if (renovation != '' && renovation != ' ') {
                markup += "<p class='renovations'>Renovation: " + renovation + "</p>";
            }

            if (rent != '' && rent != ' ') {
                markup += "<p class='rentPerMonth'>Rent: " + rent + "</p>";
            }

            if (price != '' && price != ' ') {
                markup += "<p class='salesPrice'>Price: " + price + "</p>";
            }

            if (unencrumbed_price != '' && unencrumbed_price != ' ') {
                markup += "<p class='unencumberedSalesPrice'>Unencumbered price: " + unencrumbed_price + "</p>";
            }

            markup += "</div>";
            markup = markup.replace(/NaN/g, '');
            L.circle(
                [
                    item.coordinates.Latitude,
                    item.coordinates.Longitude
                ],
                {
                    opacity: 1,
                    radius: 50,
                    fillColor: "#ff4500",
                    color: "#ff4500",
                    data: {
                        objectId: item.objectId,
                        buildingEnergyClass: energy_class,
                        buildingYearOfFirstUse: year,
                        buildingPlanSituation: plan,
                        housingCoating: coating,
                        housingNumberOfRooms: rooms,
                        housingTotalArea: area,
                        renovations: renovation,
                        rentPerMonth: rent,
                        salesPrice: price,
                        unencumberedSalesPrice: unencrumbed_price
                    }
                },
            )
                .addTo(mapview)
                .bringToFront()
                .bindPopup(markup)
                .on("click", function () {
                    var data = this.options.data;

                    for (var [key, value] of Object.entries(data)) {
                        if( value == '') {
                            value = 'N/A';
                        }

                        $('.' + key + ' span:nth-child(2)').text(value);
                    }

                    $.getJSON("http://35.205.22.186/api/estatePredictions?objectId=" + data.objectId, function (result) {
                        for (const [key, object] of Object.entries(result)) {
                            if (object.value != null) {
                                object.value *= 100;
                                object.value = object.value.toFixed(6);
                            } else {
                                object.value = 'N/A';
                            }

                            $('.' + key + ' span:nth-child(2)').text(object.value);
                        }
                    });
                });
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
