import numpy as np
import csv
import googlemaps
import json
from shapely.geometry import shape, Point


with open('regions.csv', encoding='latin-1') as file:
    reader = csv.reader(file)
    data = list(reader)
    ret = []
    growth_rates = []
    for elem in data:
        region = elem[0]
        if not any(list(map(int, elem[1:]))):
            growth_rate = 0
        else:
            firstIndex = next((i for i, x in enumerate(elem[1:]) if int(x) != 0), None)
            lastIndex = [index for index, item in enumerate(elem[1:]) if int(item) != 0][-1]
            if int(elem[1:][firstIndex]) == int(elem[1:][lastIndex]):
                if firstIndex != lastIndex:
                    growth_rate = pow((int(elem[1:][lastIndex])+0.001)*1.0/int(elem[1:][firstIndex]), 1.0/(lastIndex-firstIndex))
                else:
                    growth_rate = pow((int(elem[1:][lastIndex])+0.001)*1.0/int(elem[1:][firstIndex]), 1.0)
                print(growth_rate)
            else:
                growth_rate = pow(int(elem[1:][lastIndex])*1.0/int(elem[1:][firstIndex]), 1.0)
        growth_rates.append(growth_rate)

i = 0
for norm in [float(i)/sum(growth_rates) for i in growth_rates]:
    ret.append([data[i][0], norm])
    i = i+1

gmaps = googlemaps.Client(key='')

realRet = []

polys = []

usedPolys = {}

# load GeoJSON file containing sectors
with open('finnland.geojson') as f:
    js = json.load(f)

# check each polygon to see if it contains the point
for feature in js['features']:
    polygon = shape(feature['geometry'])
    leaf = True
    for feature2 in js['features']:
        if feature2['properties']['parents']:
            if str(feature['properties']['id']) in feature2['properties']['parents'].split(","):
                leaf = False
    if leaf:
        polys.append([polygon,feature])

for elem in ret:
    geocode_result = gmaps.geocode('Helsinki ' + elem[0])
    if not geocode_result:
        geocode_result = gmaps.geocode('Espoo ' + elem[0])
    if not geocode_result:
        geocode_result = gmaps.geocode('Vantaa ' + elem[0])
    lat = geocode_result[0]['geometry']['location']['lat']
    lng = geocode_result[0]['geometry']['location']['lng']

    # construct point based on lon/lat returned by geocoder
    point = Point(lng, lat)

    for poly in polys:
        if poly[0].contains(point):
            if not str(poly[0]) in usedPolys.keys():
                usedPolys[str(poly[0])] = elem[0]
                realRet.append([elem[0], poly[1], elem[1]])

with open('regions_with_growth.csv', 'w+', newline='', encoding='utf8') as output:
    for elem in realRet:
        elem[1] = json.dumps(elem[1])
        output.write(";".join(map(str, elem))+"\n")


