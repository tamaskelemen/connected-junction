import requests
import json
import googlemaps
import os.path
from shapely.geometry import shape, Point
import csv
import heapq
num = 0
retVal = []

with open('Kohteet.json', encoding='utf8') as jsonFile:
    data = json.load(jsonFile)
    with open('water_overall.csv', encoding='utf8') as file:
        rows = list(csv.reader(file, delimiter=';'))
        scaleFactor = len(rows)/10
        for entry in data['ns0:Apartments']['Apartment']:
            if not entry['Location']['Coordinates'] or not 'Longitude' in entry['Location']['Coordinates'] or not 'Latitude' in entry['Location']['Coordinates']:
                num = num +1
                print(num)
                continue
            point = Point(float(entry['Location']['Coordinates']['Longitude']), float(entry['Location']['Coordinates']['Latitude']))
            distances = {}
            for row in rows:
                polygon = shape(json.loads(row[1])['geometry'])
                distances[polygon.centroid.distance(point)] = row[2]

            smallest = sorted(distances, key = lambda x: float(x))[:int(scaleFactor)]
            avg = 0.0
            for s in smallest:
                avg += float(distances[s])
            avg = avg / scaleFactor
            retVal.append([entry['Keys']['@objectId'], avg])

with open('predicted.csv', 'w+', newline='', encoding='utf8') as output:
    for elem in retVal:
        output.write(";".join(map(str, elem))+"\n")

        
