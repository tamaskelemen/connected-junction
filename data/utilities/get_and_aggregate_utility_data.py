import requests
import json
import googlemaps

with open('properties.json', encoding='utf8') as f:
  data = json.load(f)

propOAvgDict = {}
propYAvgDict = {}

for prop in data:
    locn = prop['locationName']
    p = {'Record':'LocationName', 'SearchString':locn, 'ReportingGroup':'Electricity', 'StartTime':'2015-01-01', 'EndTime':'2019-12-12'}
    overallResponse = requests.get('https://helsinki-openapi.nuuka.cloud/api/v1.0/EnergyData/Monthly/ListByProperty', params=p)
    p2 = {'Record':'LocationName', 'SearchString':locn, 'ReportingGroup':'Electricity', 'StartTime':'2018-12-12', 'EndTime':'2019-12-12'}
    lastYearResponse = requests.get('https://helsinki-openapi.nuuka.cloud/api/v1.0/EnergyData/Monthly/ListByProperty', params=p2)

    overall = reponse.json()
    avg = 0.0
    for d in overall:
        avg += overall['value']
    avg = avg / len(overall)
    propAvgDict[locn] = avg

    avg = 0.0
    for d in overall:
        avg += overall['value']
    avg = avg / len(overall)
    propAvgDict[locn] = avg
