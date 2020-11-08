import requests
import json
import googlemaps
import os.path
from shapely.geometry import shape, Point
import csv

with open('properties.json', encoding='utf8') as f:
  data = json.load(f)

propOAvgDict = {}
propYAvgDict = {}

if not os.path.isfile('propOAvgDict.json'):
    for prop in data:
        locn = prop['locationName']
        p = {'Record':'LocationName', 'SearchString':locn, 'ReportingGroup':'Heat', 'StartTime':'2015-01-01', 'EndTime':'2019-12-12'}
        overallResponse = requests.get('https://helsinki-openapi.nuuka.cloud/api/v1.0/EnergyData/Monthly/ListByProperty', params=p)
        p2 = {'Record':'LocationName', 'SearchString':locn, 'ReportingGroup':'Heat', 'StartTime':'2018-12-12', 'EndTime':'2019-12-12'}
        lastYearResponse = requests.get('https://helsinki-openapi.nuuka.cloud/api/v1.0/EnergyData/Monthly/ListByProperty', params=p2)
        
        overall = overallResponse.json()
        if 'errorNote' in overall:
            continue
        avg = 0.0
        for d in overall:
            avg += d['value']
        avg = avg / len(overall)
        propOAvgDict[locn] = avg

        yearly = lastYearResponse.json()
        if 'errorNote' in yearly:
            continue

        avg = 0.0
        for d in yearly:
            avg += d['value']
        avg = avg / len(yearly)
        propYAvgDict[locn] = avg
    with open('propOAvgDict.json', 'w', encoding='utf8') as file:
        json.dump(propOAvgDict, file, ensure_ascii=False)

    with open('propYAvgDict.json', 'w', encoding='utf8') as file:
        json.dump(propYAvgDict, file, ensure_ascii=False)

else:
    with open('propOAvgDict.json', encoding='utf8') as file:
        propOAvgDict = json.load(file)

    with open('propYAvgDict.json', encoding='utf8') as file:
        propYAvgDict = json.load(file)
        


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

propOAvgDictGeoKeys = []
propYAvgDictGeoKeys = []

propOAvgDictGeo = []
propYAvgDictGeo = []

gmaps = googlemaps.Client(key='')


if not os.path.isfile('propOAvgDictGeoKeys.json'):
    for key in propOAvgDict.keys():
        geocode_result = gmaps.geocode(key)
        
        if not geocode_result:
            geocode_result = gmaps.geocode('Helsinki ' + key)
        if not geocode_result:
            geocode_result = gmaps.geocode('Finland ' + key)
        if not geocode_result:
            continue

        lat = geocode_result[0]['geometry']['location']['lat']
        lng = geocode_result[0]['geometry']['location']['lng']

        # construct point based on lon/lat returned by geocoder
        point = Point(lng, lat)

        for poly in polys:
            if poly[0].contains(point):
                if not str(poly[0]) in usedPolys.keys():
                    usedPolys[str(poly[0])] = key
                    propOAvgDictGeoKeys.append([key, poly[1]])

    usedPolys = {}
    for key in propYAvgDict.keys():
        geocode_result = gmaps.geocode(key)
        
        if not geocode_result:
            geocode_result = gmaps.geocode('Helsinki ' + key)
        if not geocode_result:
            geocode_result = gmaps.geocode('Finland ' + key)
        if not geocode_result:
            continue

        lat = geocode_result[0]['geometry']['location']['lat']
        lng = geocode_result[0]['geometry']['location']['lng']
        
        # construct point based on lon/lat returned by geocoder
        point = Point(lng, lat)

        for poly in polys:
            if poly[0].contains(point):
                if not str(poly[0]) in usedPolys.keys():
                    usedPolys[str(poly[0])] = key
                    propYAvgDictGeoKeys.append([key, poly[1]])


    with open('propOAvgDictGeoKeys.json', 'w', encoding='utf8') as file:
        json.dump(propOAvgDictGeoKeys, file, ensure_ascii=False)

    with open('propYAvgDictGeoKeys.json', 'w', encoding='utf8') as file:
        json.dump(propYAvgDictGeoKeys, file, ensure_ascii=False)

else:
    with open('propOAvgDictGeoKeys.json', encoding='utf8') as file:
        propOAvgDictGeoKeys = json.load(file)

    with open('propYAvgDictGeoKeys.json', encoding='utf8') as file:
        propYAvgDictGeoKeys = json.load(file)

normValO = [float(i)/sum(propOAvgDict.values()) for i in propOAvgDict.values()]
zippedValO = dict(zip(propOAvgDict.keys(), normValO))

normValY = [float(i)/sum(propYAvgDict.values()) for i in propYAvgDict.values()]
zippedValY = dict(zip(propYAvgDict.keys(), normValY))

print(len(propOAvgDict))
print(len(propOAvgDictGeo))
for elem in propOAvgDictGeoKeys:
    propOAvgDictGeo.append([elem[1], zippedValO[elem[0]]])
for elem in propYAvgDictGeoKeys:
    propYAvgDictGeo.append([elem[1], zippedValY[elem[0]]])

print(len(propOAvgDictGeo))

finalO = []

for row in propOAvgDictGeo:
    finalO.append([row[0]['properties']['name'], row[0], row[1]])

finalY = []

for row in propYAvgDictGeo:
    finalY.append([row[0]['properties']['name'], row[0], row[1]])
                

with open('heat_overall.csv', 'w+', newline='', encoding='utf8') as output:
    for elem in finalO:
        elem[1] = json.dumps(elem[1])
        output.write(";".join(map(str, elem))+"\n")

with open('heat_lastyear.csv', 'w+', newline='', encoding='utf8') as output:
    for elem in finalY:
        elem[1] = json.dumps(elem[1])
        output.write(";".join(map(str, elem))+"\n")

